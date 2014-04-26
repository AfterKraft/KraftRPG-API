package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.events.spells.SpellCastEvent;
import com.afterkraft.kraftrpg.api.util.SpellRequirement;

/**
 * {@see Active}
 */
public abstract class ActiveSpell<T extends SpellArgument> extends Spell implements Active<T> {

    private String usage = "";

    public ActiveSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public final String getUsage() {
        return this.usage;
    }

    @Override
    public final void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public void onWarmUp(Champion champion) {

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

            SpellRequirement spellRequirement = getSpellRequirement(champion);

            double healthCost = plugin.getSpellConfigManager().getUseSetting(champion, this, SpellSetting.HEALTH_COST, 0, true);

            final SpellCastEvent skillEvent = new SpellCastEvent(champion, this, manaCost, healthCost, spellRequirement);
            plugin.getServer().getPluginManager().callEvent(skillEvent);
            if (skillEvent.isCancelled()) {
                return SpellCastResult.CANCELLED;
            }

            boolean cancelDelayedSpellOnFailure = false;
            Delayed<T> dSpell = plugin.getSpellManager().getDelayedSpell(champion);
            if ((dSpell != null) && dSpell.getActiveSpell().equals(this)) {
                cancelDelayedSpellOnFailure = true;
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



            int globalCD = plugin.getProperties().getDefaultGlobalCooldown();
            dSpell = new DelayedSpell<T>(this, argument, champion, System.currentTimeMillis(), delay);
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
                dSpell = plugin.getSpellManager().getDelayedSpell(champion);

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
            skillResult = useSpell(champion, argument);

            if (skillResult == SpellCastResult.NORMAL) {
                time = System.currentTimeMillis();
                if (cooldown > 0) {
                    champion.setCooldown(name, time + cooldown);
                }

                if (globalCD > 0) {
                    if (!(dSpell instanceof DelayedSpell)) {
                        if (cooldown < globalCD) {
                            if (cooldown < 100)
                                cooldown = 100;

                            champion.setGlobalCooldown(cooldown + time);
                        } else
                            champion.setGlobalCooldown(globalCD + time);
                    }
                }

                // Award XP for skill usage
                if (grantsExperienceOnCast()) {
                    this.awardExperience(champion);
                }

                // Deduct mana
                if (manaCost > 0) {
                    champion.setMana(champion.getMana() - manaCost);
                }

                // Deduct health
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
}
