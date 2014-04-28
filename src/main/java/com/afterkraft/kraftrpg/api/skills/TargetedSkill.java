/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.skills;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.entity.effects.EffectType;
import com.afterkraft.kraftrpg.api.events.skills.SkillCastEvent;
import com.afterkraft.kraftrpg.api.util.SkillRequirement;
import com.afterkraft.kraftrpg.api.util.Utilities;

/**
 * A skill that requires a {@link org.bukkit.entity.LivingEntity} as a target.
 * Targeted skills do
 */
public abstract class TargetedSkill<T extends TargetedSkillArgument> extends ActiveSkill<T> implements Targeted<T> {

    public TargetedSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public SkillCastResult canCast(final SkillCaster caster, final T argument, boolean forced) {
        final String name = this.getName();
        if (caster == null) {
            return null;
        }

        if (caster.isDead()) {
            return SkillCastResult.DEAD;
        }

        if (!forced) {
            if (!caster.canPrimaryUseSkill(this) || !caster.canSecondaryUseSkill(this) || !caster.canAdditionalUseSkill(this)) {
                return SkillCastResult.NOT_DEFINED_IN_ACTIVE_ROLES;
            } else if (caster.doesPrimaryRestrictSkill(this) || caster.doesSecondaryRestrictSkill(this)) {
                return SkillCastResult.RESTRICTED_IN_ROLES;
            }
            final int level = plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.LEVEL, 1, true);
            int champLevel = caster.getHighestSkillLevel(this);
            if (champLevel != 0 && champLevel < level) {
                return SkillCastResult.LOW_LEVEL;
            }
            if (champLevel == 0) {
                champLevel = 1;
            }

            long time = System.currentTimeMillis();
            final long global = caster.getGlobalCooldown();
            if (!plugin.getSkillManager().isCasterDelayed(caster)) {
                if (time < global) {
                    return SkillCastResult.ON_GLOBAL_COOLDOWN;
                }
            }

            int cooldown = plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.COOLDOWN, 0, true);
            if (cooldown > 0) {
                final Long expiry = caster.getCooldown(name);
                if ((expiry != null) && (time < expiry)) {
                    return SkillCastResult.ON_COOLDOWN;
                }
            }

            final int delay = plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.DELAY, 0, true);

            if (!isType(SkillType.UNINTERRUPTIBLE)) {
                if (caster.isInCombat() && plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.NO_COMBAT_USE, false)) {
                    if (delay > 0) {
                        final Stalled dSkill = caster.getStalledSkill();
                        if ((dSkill != null) && dSkill.getActiveSkill().equals(this)) {
                            caster.cancelStalledSkill(true);
                        }
                    }
                    return SkillCastResult.NO_COMBAT;
                }
            }

            int manaCost = plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.MANA, 0, true);
            final double manaReduce = plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.MANA_REDUCE_PER_LEVEL, 0.0, false) * champLevel;
            manaCost -= (int) manaReduce;

            // Reagent stuff
            SkillRequirement skillRequirement = getSkillRequirement(caster);

            double healthCost = plugin.getSkillConfigManager().getUseSetting(caster, this, SkillSetting.HEALTH_COST, 0, true);

            final SkillCastEvent skillEvent = new SkillCastEvent(caster, this, manaCost, healthCost, skillRequirement);
            plugin.getServer().getPluginManager().callEvent(skillEvent);
            if (skillEvent.isCancelled()) {
                return SkillCastResult.CANCELLED;
            }

            boolean cancelDelayedSkillOnFailure = false;
            Stalled dSkill = caster.getStalledSkill();
            if ((dSkill != null) && dSkill.getActiveSkill().equals(this)) {
                cancelDelayedSkillOnFailure = true;
            }

            if (!(dSkill instanceof StalledTarget)) {
                return SkillCastResult.FAIL;
            }

            manaCost = skillEvent.getManaCost();
            if (manaCost > caster.getMana()) {
                if (cancelDelayedSkillOnFailure) {
                    caster.cancelStalledSkill(true);
                }
                return SkillCastResult.LOW_MANA;
            }

            healthCost = skillEvent.getHealthCost();
            if ((healthCost > 0) && (caster.getHealth() <= healthCost)) {
                if (cancelDelayedSkillOnFailure) {
                    caster.cancelStalledSkill(true);
                }
                return SkillCastResult.LOW_HEALTH;
            }

            skillRequirement = skillEvent.getRequirement();
            if ((skillRequirement != null) && !hasSkillRequirement(skillRequirement, caster)) {
                if (cancelDelayedSkillOnFailure) {
                    caster.cancelStalledSkill(true);
                }
                return SkillCastResult.MISSING_REAGENT;
            }

            LivingEntity target = getTarget(caster, 50, argument);
            if (target == null) {
                return SkillCastResult.INVALID_TARGET;
            }
            StalledTarget<T> dTSkill;

            int globalCD = plugin.getProperties().getDefaultGlobalCooldown();
            dTSkill = new StalledTargetedSkill<T>(this, argument, caster, target, System.currentTimeMillis(), delay);
            if ((delay > 0) && !plugin.getSkillManager().isCasterDelayed(caster)) {
                if (caster.setStalledSkill(dSkill)) {
                    onWarmUp(caster);

                    if (cooldown < globalCD) {
                        if (cooldown < globalCD) {
                            if (cooldown < 500) {
                                cooldown = 500;
                            }
                        }
                        caster.setCooldown("global", cooldown + time);
                    } else {
                        caster.setCooldown("global", globalCD + time);
                    }

                    return SkillCastResult.NORMAL;
                } else {
                    return SkillCastResult.FAIL;
                }
            } else if (plugin.getSkillManager().isCasterDelayed(caster)) {
                dTSkill = (StalledTarget<T>) caster.getStalledSkill();

                if (dSkill.getActiveSkill().equals(this)) {
                    if (!dSkill.isReady()) {
                        return SkillCastResult.ON_WARMUP;
                    } else {
                        if (!isType(SkillType.ABILITY_PROPERTY_SONG)) {
                            caster.removeEffect(caster.getEffect("Casting"));
                        }

                        plugin.getSkillManager().setCompletedSkill(caster);
                    }
                } else {
                    return SkillCastResult.FAIL;
                }
            }

            SkillCastResult skillResult;
            skillResult = this.useDelayed(caster, dTSkill, argument);

            if (skillResult == SkillCastResult.NORMAL) {
                time = System.currentTimeMillis();
                if (cooldown > 0) {
                    caster.setCooldown(name, time + cooldown);
                }

                if (globalCD > 0) {
                    if (!(dTSkill instanceof StalledSkill)) {
                        if (cooldown < globalCD) {
                            if (cooldown < 100) {
                                cooldown = 100;
                            }

                            caster.setGlobalCooldown(cooldown + time);
                        } else {
                            caster.setGlobalCooldown(globalCD + time);
                        }
                    }
                }

                if (grantsExperienceOnCast()) {
                    this.awardExperience(caster);
                }

                if (manaCost > 0) {
                    caster.setMana(caster.getMana() - manaCost);
                }

                if (healthCost > 0) {
                    caster.setHealth(caster.getHealth() - healthCost);
                }

                if (skillRequirement != null) {
                    caster.removeSkillRequirement(skillRequirement);
                    caster.updateInventory();
                }

            }
            return skillResult;
        } else {
            return SkillCastResult.NORMAL;
        }

    }

    private LivingEntity getTarget(SkillCaster caster, int maxDistance, T args) {
        if (caster instanceof IEntity) {
            final IEntity ientity = (IEntity) caster;
            final LivingEntity lentity = ientity.getEntity();
            LivingEntity target = null;
            if (args.getRawArguments().size() > 0) {
                target = plugin.getServer().getPlayer(args.getTargetUniqueID());

                if (target == null) {
                    return null;
                }

                if (!lentity.equals(target)) {
                    if (!target.getLocation().getWorld().equals(lentity.getLocation().getWorld())) {
                        return null;
                    }

                    int distanceSquared = maxDistance * maxDistance;
                    if (lentity.getLocation().distanceSquared(target.getLocation()) > distanceSquared) {
                        return null;
                    }

                    if (!inLineOfSight(lentity, target)) {
                        return null;
                    }
                }

                if (target.isDead() || target.getHealth() == 0) {
                    return null;
                }
            }

            if (target == null) {
                target = getPlayerTarget(lentity, maxDistance);
                if (isType(SkillType.HEALING) || isType(SkillType.BUFFING)) {
                    if ((target instanceof Player) && caster.hasParty() && caster.getParty().hasMember((Player) target)) {
                        return target;
                    } else if (target instanceof Player) {
                        return null;
                    } else {
                        target = null;
                    }
                }
            }

            if (target == null || lentity.equals(target)) {
                if (isType(SkillType.AGGRESSIVE) || isType(SkillType.NO_SELF_TARGETTING)) {
                    return null;
                }
                target = lentity;
            }

            if (!lentity.equals(target)) {
                if (caster.hasEffectType(EffectType.BLIND)) {
                    return null;
                }

                if (target != null && (target instanceof Player)) {
                    final Champion targettedChamp = plugin.getEntityManager().getChampion((Player) target);
                    if (targettedChamp.hasEffectType(EffectType.UNTARGETABLE)) {
                        return null;
                    }
                }
            }

            if (isType(SkillType.AGGRESSIVE)) {
                if (lentity.equals(target) || caster.hasEffectType(EffectType.INVULNERABILITY) || !damageCheck(ientity, target)) {
                    return null;
                }
            }

            if (isType(SkillType.MULTI_GRESSIVE)) {
                boolean bypassDamageCheck = false;
                if (caster.hasParty()) {
                    if (target instanceof Player) {
                        Champion targettedChampion = plugin.getEntityManager().getChampion((Player) target);
                        if (caster.getParty().hasMember(targettedChampion)) {
                            bypassDamageCheck = true;
                        }
                    }
                } else if (lentity.equals(target)) {
                    bypassDamageCheck = true;
                }

                if (!bypassDamageCheck) {
                    if (caster.hasEffectType(EffectType.INVULNERABILITY)) {
                        return null;
                    } else if (!damageCheck(ientity, target)) {
                        return null;
                    }
                }
            }
            return target;
        } else {
            return null;
        }
    }

    public static boolean inLineOfSight(LivingEntity a, LivingEntity b) {
        if (a.equals(b)) {
            return true;
        }

        final Location aLoc = a.getEyeLocation();
        final Location bLoc = b.getEyeLocation();
        final int distance = (int) aLoc.distance(bLoc);
        if (distance > 120) {
            return false;
        }
        final Vector vector = new Vector(bLoc.getX() - aLoc.getX(), bLoc.getY() - aLoc.getY(), bLoc.getZ() - aLoc.getZ());
        try {
            final Iterator<Block> iterator = new BlockIterator(a.getWorld(), aLoc.toVector(), vector, 0, distance + 1);
            while (iterator.hasNext()) {
                final Block block = iterator.next();
                final Material type = block.getType();
                if (!Utilities.getTransparentBlocks().contains(type)) {
                    return false;
                }
            }
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public static LivingEntity getPlayerTarget(LivingEntity lentity, int maxDistance) {
        if (lentity.getLocation().getBlockY() > lentity.getLocation().getWorld().getMaxHeight()) {
            return null;
        }
        List<Block> lineOfSight;
        try {
            lineOfSight = lentity.getLineOfSight(Utilities.getTransparentBlockIDs(), maxDistance);
        } catch (final IllegalStateException e) {
            return null;
        }

        final Set<Location> locations = new HashSet<Location>();
        for (final Block block : lineOfSight) {
            locations.add(block.getRelative(BlockFace.UP).getLocation());
            locations.add(block.getLocation());
            locations.add(block.getRelative(BlockFace.DOWN).getLocation());
        }
        final List<Entity> nearbyEntities = lentity.getNearbyEntities(maxDistance, maxDistance, maxDistance);
        for (final Entity entity : nearbyEntities) {
            if ((entity instanceof LivingEntity) && !entity.isDead() && !(((LivingEntity) entity).getHealth() == 0)) {
                if (locations.contains(entity.getLocation().getBlock().getLocation())) {
                    return (LivingEntity) entity;
                }
            }
        }
        return null;
    }

}
