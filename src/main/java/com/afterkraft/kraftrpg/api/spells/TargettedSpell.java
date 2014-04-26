package com.afterkraft.kraftrpg.api.spells;

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
import com.afterkraft.kraftrpg.api.entity.SpellCaster;
import com.afterkraft.kraftrpg.api.entity.effects.EffectType;
import com.afterkraft.kraftrpg.api.events.spells.SpellCastEvent;
import com.afterkraft.kraftrpg.api.util.SpellRequirement;
import com.afterkraft.kraftrpg.api.util.Utilities;

/**
 * A spell that requires a {@link org.bukkit.entity.LivingEntity} as a target. Targetted spells
 * do
 *
 */
public abstract class TargettedSpell<T extends TargettedSpellArgument> extends ActiveSpell<T> implements Targetted<T> {

    public TargettedSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public SpellCastResult canCast(final SpellCaster caster, final T argument, boolean forced) {
        final String name = this.getName();
        if (caster == null) {
            return null;
        }

        if (caster.isDead()) {
            return SpellCastResult.DEAD;
        }

        if (!forced) {
            if (!caster.canPrimaryUseSpell(this) || !caster.canSecondaryUseSpell(this) || !caster.canAdditionalUseSpell(this)) {
                return SpellCastResult.NOT_DEFINED_IN_ACTIVE_ROLES;
            } else if (caster.doesPrimaryRestrictSpell(this) || caster.doesSecondaryRestrictSpell(this)) {
                return SpellCastResult.RESTRICTED_IN_ROLES;
            }
            final int level = plugin.getSpellConfigManager().getUseSetting(caster, this, SpellSetting.LEVEL, 1, true);
            int champLevel = caster.getHighestSpellLevel(this);
            if (champLevel != 0 && champLevel < level) {
                return SpellCastResult.LOW_LEVEL;
            }
            if (champLevel == 0) {
                champLevel = 1;
            }

            long time = System.currentTimeMillis();
            final long global = caster.getGlobalCooldown();
            if (!plugin.getSpellManager().isCasterDelayed(caster)) {
                if (time < global) {
                    return SpellCastResult.ON_GLOBAL_COOLDOWN;
                }
            }

            int cooldown = plugin.getSpellConfigManager().getUseSetting(caster, this, SpellSetting.COOLDOWN, 0, true);
            if (cooldown > 0) {
                final Long expiry = caster.getCooldown(name);
                if ((expiry != null) && (time < expiry)) {
                    return SpellCastResult.ON_COOLDOWN;
                }
            }

            final int delay = plugin.getSpellConfigManager().getUseSetting(caster, this, SpellSetting.DELAY, 0, true);

            if (!isType(SpellType.UNINTERRUPTIBLE)) {
                if (caster.isInCombat() && plugin.getSpellConfigManager().getUseSetting(caster, this, SpellSetting.NO_COMBAT_USE, false)) {
                    if (delay > 0) {
                        final Delayed dSpell = caster.getDelayedSpell();
                        if ((dSpell != null) && dSpell.getActiveSpell().equals(this)) {
                            caster.cancelDelayedSpell(true);
                        }
                    }
                    return SpellCastResult.NO_COMBAT;
                }
            }

            int manaCost = plugin.getSpellConfigManager().getUseSetting(caster, this, SpellSetting.MANA, 0, true);
            final double manaReduce = plugin.getSpellConfigManager().getUseSetting(caster, this, SpellSetting.MANA_REDUCE_PER_LEVEL, 0.0, false) * champLevel;
            manaCost -= (int) manaReduce;

            // Reagent stuff
            SpellRequirement spellRequirement = getSpellRequirement(caster);

            double healthCost = plugin.getSpellConfigManager().getUseSetting(caster, this, SpellSetting.HEALTH_COST, 0, true);

            final SpellCastEvent skillEvent = new SpellCastEvent(caster, this, manaCost, healthCost, spellRequirement);
            plugin.getServer().getPluginManager().callEvent(skillEvent);
            if (skillEvent.isCancelled()) {
                return SpellCastResult.CANCELLED;
            }

            boolean cancelDelayedSpellOnFailure = false;
            Delayed dSpell = caster.getDelayedSpell();
            if ((dSpell != null) && dSpell.getActiveSpell().equals(this)) {
                cancelDelayedSpellOnFailure = true;
            }

            if (!(dSpell instanceof DelayedTarget)) {
                return SpellCastResult.FAIL;
            }

            manaCost = skillEvent.getManaCost();
            if (manaCost > caster.getMana()) {
                if (cancelDelayedSpellOnFailure)
                    caster.cancelDelayedSpell(true);
                return SpellCastResult.LOW_MANA;
            }

            healthCost = skillEvent.getHealthCost();
            if ((healthCost > 0) && (caster.getHealth() <= healthCost)) {
                if (cancelDelayedSpellOnFailure)
                    caster.cancelDelayedSpell(true);
                return SpellCastResult.LOW_HEALTH;
            }

            spellRequirement = skillEvent.getRequirement();
            if ((spellRequirement != null) && !hasSpellRequirement(spellRequirement, caster)) {
                if (cancelDelayedSpellOnFailure)
                    caster.cancelDelayedSpell(true);
                return SpellCastResult.MISSING_REAGENT;
            }

            LivingEntity target = getTarget(caster, 50, argument);
            if (target == null) {
                return SpellCastResult.INVALID_TARGET;
            }
            DelayedTarget dTSpell;

            int globalCD = plugin.getProperties().getDefaultGlobalCooldown();
            dTSpell = new DelayedTargetedSpell<T>(this, argument, caster, target, System.currentTimeMillis(), delay);
            if ((delay > 0) && !plugin.getSpellManager().isCasterDelayed(caster)) {
                if (caster.setDelayedSpell(dSpell)) {
                    onWarmUp(caster);

                    if (cooldown < globalCD) {
                        if (cooldown < globalCD) {
                            if (cooldown < 500)
                                cooldown = 500;
                        }
                        caster.setCooldown("global", cooldown + time);
                    } else {
                        caster.setCooldown("global", globalCD + time);
                    }

                    return SpellCastResult.NORMAL;
                } else {
                    return SpellCastResult.FAIL;
                }
            } else if (plugin.getSpellManager().isCasterDelayed(caster)) {
                dTSpell = (DelayedTarget) caster.getDelayedSpell();

                if (dSpell.getActiveSpell().equals(this)) {
                    if (!dSpell.isReady()) {
                        return SpellCastResult.ON_WARMUP;
                    } else {
                        if (!isType(SpellType.ABILITY_PROPERTY_SONG)) {
                            caster.removeEffect(caster.getEffect("Casting"));
                        }

                        plugin.getSpellManager().setCompletedSpell(caster);
                    }
                } else {
                    return SpellCastResult.FAIL;
                }
            }

            SpellCastResult skillResult;
            skillResult = this.useDelayed(caster, dTSpell, argument);

            if (skillResult == SpellCastResult.NORMAL) {
                time = System.currentTimeMillis();
                if (cooldown > 0) {
                    caster.setCooldown(name, time + cooldown);
                }

                if (globalCD > 0) {
                    if (!(dTSpell instanceof DelayedSpell)) {
                        if (cooldown < globalCD) {
                            if (cooldown < 100)
                                cooldown = 100;

                            caster.setGlobalCooldown(cooldown + time);
                        } else
                            caster.setGlobalCooldown(globalCD + time);
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

                if (spellRequirement != null) {
                    caster.removeSpellRequirement(spellRequirement);
                    caster.updateInventory();
                }

            }
            return skillResult;
        } else {
            return SpellCastResult.NORMAL;
        }

    }

    private LivingEntity getTarget(SpellCaster caster, int maxDistance, T args) {
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
                if (isType(SpellType.HEALING) || isType(SpellType.BUFFING)) {
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
                if (isType(SpellType.AGGRESSIVE) || isType(SpellType.NO_SELF_TARGETTING)) {
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

            if (isType(SpellType.AGGRESSIVE)) {
                if (lentity.equals(target) || caster.hasEffectType(EffectType.INVULNERABILITY) || !damageCheck(ientity, target)) {
                    return null;
                }
            }

            if (isType(SpellType.MULTI_GRESSIVE)) {
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
        }
        catch (final Exception e) {
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
        }
        catch (final IllegalStateException e) {
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
