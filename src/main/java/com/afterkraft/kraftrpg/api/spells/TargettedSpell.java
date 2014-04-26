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
import com.afterkraft.kraftrpg.api.entity.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
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
    public SpellCastResult canCast(final Champion champion, final T argument, boolean forced) {
        final String name = this.getName();
        if (champion == null) {
            return null;
        }

        if (champion.getPlayer().getHealth() <= 0 || champion.getPlayer().isDead()) {
            return SpellCastResult.DEAD;
        }

        if (!forced) {
            if (!champion.canPrimaryUseSpell(this) || !champion.canSecondaryUseSpell(this) || !champion.canAdditionalsUseSpell(this)) {
                return SpellCastResult.NOT_DEFINED_IN_ACTIVE_ROLES;
            } else if (champion.doesPrimaryRestrictSpell(this) || champion.doesSecondaryRestrictSpell(this)) {
                return SpellCastResult.RESTRICTED_IN_ROLES;
            }
            final int level = plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.LEVEL, 1, true);
            int champLevel = champion.getHighestSpellLevel(this);
            if (champLevel != 0 && champLevel < level) {
                return SpellCastResult.LOW_LEVEL;
            }
            if (champLevel == 0) {
                champLevel = 1;
            }

            long time = System.currentTimeMillis();
            final long global = champion.getGlobalCooldown();
            if (!plugin.getSpellManager().isChampionDelayed(champion)) {
                if (time < global) {
                    return SpellCastResult.ON_GLOBAL_COOLDOWN;
                }
            }

            int cooldown = plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.COOLDOWN, 0, true);
            if (cooldown > 0) {
                final Long expiry = champion.getCooldown(name);
                if ((expiry != null) && (time < expiry)) {
                    return SpellCastResult.ON_COOLDOWN;
                }
            }

            final int delay = plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.DELAY, 0, true);

            if (!isType(SpellType.UNINTERRUPTIBLE)) {
                if (champion.isInCombat() && plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.NO_COMBAT_USE, false)) {
                    if (delay > 0) {
                        final Delayed dSpell = plugin.getSpellManager().getDelayedSpell(champion);
                        if ((dSpell != null) && dSpell.getActiveSpell().equals(this)) {
                            champion.cancelDelayedSpell(true);
                        }
                    }
                    return SpellCastResult.NO_COMBAT;
                }
            }

            int manaCost = plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.MANA, 0, true);
            final double manaReduce = plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.MANA_REDUCE_PER_LEVEL, 0.0, false) * champLevel;
            manaCost -= (int) manaReduce;

            // Reagent stuff
            SpellRequirement spellRequirement = getSpellRequirement(champion);

            double healthCost = plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.HEALTH_COST, 0, true);

            final SpellCastEvent skillEvent = new SpellCastEvent(champion, this, manaCost, healthCost, spellRequirement);
            plugin.getServer().getPluginManager().callEvent(skillEvent);
            if (skillEvent.isCancelled()) {
                return SpellCastResult.CANCELLED;
            }

            boolean cancelDelayedSpellOnFailure = false;
            Delayed<T> dSpell =  plugin.getSpellManager().getDelayedSpell(champion);
            if ((dSpell != null) && dSpell.getActiveSpell().equals(this)) {
                cancelDelayedSpellOnFailure = true;
            }

            if (!(dSpell instanceof DelayedTarget)) {
                return SpellCastResult.FAIL;
            }

            manaCost = skillEvent.getManaCost();
            if (manaCost > champion.getMana()) {
                if (cancelDelayedSpellOnFailure)
                    champion.cancelDelayedSpell(true);
                return SpellCastResult.LOW_MANA;
            }

            healthCost = skillEvent.getHealthCost();
            if ((healthCost > 0) && (champion.getPlayer().getHealth() <= healthCost)) {
                if (cancelDelayedSpellOnFailure)
                    champion.cancelDelayedSpell(true);
                return SpellCastResult.LOW_HEALTH;
            }

            spellRequirement = skillEvent.getRequirement();
            if ((spellRequirement != null) && !hasSpellRequirement(spellRequirement, champion)) {
                if (cancelDelayedSpellOnFailure)
                    champion.cancelDelayedSpell(true);
                return SpellCastResult.MISSING_REAGENT;
            }

            LivingEntity target = getTarget(champion, 50, argument);
            if (target == null) {
                return SpellCastResult.INVALID_TARGET;
            }
            DelayedTargetedSpell<T> dTSpell;

            int globalCD = plugin.getProperties().getDefaultGlobalCooldown();
            dTSpell = new DelayedTargetedSpell<T>(this, argument, champion, target, System.currentTimeMillis(), delay);
            if ((delay > 0) && !plugin.getSpellManager().isChampionDelayed(champion)) {
                if (plugin.getSpellManager().setDelayedSpell(dSpell)) {
                    onWarmUp(champion);

                    if (cooldown < globalCD) {
                        if (cooldown < globalCD) {
                            if (cooldown < 500)
                                cooldown = 500;
                        }
                        champion.setCooldown("global", cooldown + time);
                    } else {
                        champion.setCooldown("global", globalCD + time);
                    }

                    return SpellCastResult.NORMAL;
                } else {
                    return SpellCastResult.FAIL;
                }
            } else if (plugin.getSpellManager().isChampionDelayed(champion)) {
                dTSpell = (DelayedTargetedSpell<T>) plugin.getSpellManager().getDelayedSpell(champion);

                if (dSpell.getActiveSpell().equals(this)) {
                    if (!dSpell.isReady()) {
                        return SpellCastResult.ON_WARMUP;
                    } else {
                        if (!isType(SpellType.ABILITY_PROPERTY_SONG)) {
                            champion.removeEffect(champion.getEffect("Casting"));
                        }

                        plugin.getSpellManager().setCompletedSpell(champion);
                    }
                } else {
                    return SpellCastResult.FAIL;
                }
            }

            SpellCastResult skillResult;
            skillResult = this.useDelayed(champion, dTSpell, argument);

            if (skillResult == SpellCastResult.NORMAL) {
                time = System.currentTimeMillis();
                if (cooldown > 0) {
                    champion.setCooldown(name, time + cooldown);
                }

                if (globalCD > 0) {
                    if (!(dTSpell instanceof DelayedSpell)) {
                        if (cooldown < globalCD) {
                            if (cooldown < 100)
                                cooldown = 100;

                            champion.setGlobalCooldown(cooldown + time);
                        } else
                            champion.setGlobalCooldown(globalCD + time);
                    }
                }

                if (grantsExperienceOnCast()) {
                    this.awardExperience(champion);
                }

                if (manaCost > 0) {
                    champion.setMana(champion.getMana() - manaCost);
                }

                if (healthCost > 0) {
                    champion.getPlayer().setHealth(champion.getPlayer().getHealth() - healthCost);
                }

                if (spellRequirement != null) {
                    champion.removeSpellRequirement(spellRequirement);
                    champion.getPlayer().updateInventory();
                }

            }
            return skillResult;
        } else {
            return SpellCastResult.NORMAL;
        }

    }

    private LivingEntity getTarget(Champion champion, int maxDistance, T args) {
        final Player player = champion.getPlayer();
        LivingEntity target = null;
        if (args.getRawArguments().size() > 0) {
            target = plugin.getServer().getPlayer(args.getTargetUniqueID());

            if (target == null) {
                return null;
            }

            if (!player.equals(target)) {
                if (!target.getLocation().getWorld().equals(player.getLocation().getWorld())) {
                    return null;
                }

                int distanceSquared = maxDistance * maxDistance;
                if (player.getLocation().distanceSquared(target.getLocation()) > distanceSquared) {
                    return null;
                }

                if (!inLineOfSight(player, (Player) target)) {
                    return null;
                }
            }

            if (target.isDead() || target.getHealth() == 0) {
                return null;
            }
        }

        if (target == null) {
            target = getPlayerTarget(player, maxDistance);
            if (isType(SpellType.HEALING) || isType(SpellType.BUFFING)) {
                if ((target instanceof Player) && champion.hasParty() && champion.getParty().hasMember((Player) target)) {
                    return target;
                }
                else if (target instanceof Player) {
                    return null;
                }
                else {
                    target = null;
                }
            }
        }

        if (target == null || player.equals(target)) {
            if (isType(SpellType.AGGRESSIVE) || isType(SpellType.NO_SELF_TARGETTING)) {
                return null;
            }
            target = player;
        }

        if (!player.equals(target)) {
            if (champion.hasEffectType(EffectType.BLIND)) {
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
            if (player.equals(target) || champion.hasEffectType(EffectType.INVULNERABILITY) || !damageCheck(champion, target)) {
                return null;
            }
        }

        if (isType(SpellType.MULTI_GRESSIVE)) {
            boolean bypassDamageCheck = false;
            if (champion.hasParty()) {
                if (target instanceof Player) {
                    Champion targettedChampion = plugin.getEntityManager().getChampion((Player) target);
                    if (champion.getParty().hasMember(targettedChampion)) {
                        bypassDamageCheck = true;
                    }
                }
            }
            else if (player.equals(target)) {
                bypassDamageCheck = true;
            }

            if (!bypassDamageCheck) {
                if (champion.hasEffectType(EffectType.INVULNERABILITY)) {
                    return null;
                }
                else if (!damageCheck(champion, target)) {
                    return null;
                }
            }
        }
        return target;
    }

    public static boolean inLineOfSight(Player a, Player b) {
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
    public static LivingEntity getPlayerTarget(Player player, int maxDistance) {
        if (player.getLocation().getBlockY() > player.getLocation().getWorld().getMaxHeight()) {
            return null;
        }
        List<Block> lineOfSight;
        try {
            lineOfSight = player.getLineOfSight(Utilities.getTransparentBlockIDs(), maxDistance);
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
        final List<Entity> nearbyEntities = player.getNearbyEntities(maxDistance, maxDistance, maxDistance);
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
