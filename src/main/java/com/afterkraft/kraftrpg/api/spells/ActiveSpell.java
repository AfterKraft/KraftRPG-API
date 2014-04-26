package com.afterkraft.kraftrpg.api.spells;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SpellCaster;
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
    public void onWarmUp(SpellCaster caster) {

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

            manaCost = skillEvent.getManaCost();
            if (manaCost > caster.getMana()) {
                if (cancelDelayedSpellOnFailure)
                    caster.cancelDelayedSpell(true);
                return SpellCastResult.LOW_MANA;
            }

            healthCost = skillEvent.getHealthCost();
            if (caster instanceof LivingEntity)
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



            int globalCD = plugin.getProperties().getDefaultGlobalCooldown();
            dSpell = new DelayedSpell<T>(this, argument, caster, System.currentTimeMillis(), delay);
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
                dSpell = caster.getDelayedSpell();

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
            skillResult = useSpell(caster, argument);

            if (skillResult == SpellCastResult.NORMAL) {
                time = System.currentTimeMillis();
                if (cooldown > 0) {
                    caster.setCooldown(name, time + cooldown);
                }

                if (globalCD > 0) {
                    if (!(dSpell instanceof DelayedSpell)) {
                        if (cooldown < globalCD) {
                            if (cooldown < 100)
                                cooldown = 100;

                            caster.setGlobalCooldown(cooldown + time);
                        } else
                            caster.setGlobalCooldown(globalCD + time);
                    }
                }

                // Award XP for skill usage
                if (grantsExperienceOnCast()) {
                    this.awardExperience(caster);
                }

                // Deduct mana
                if (manaCost > 0) {
                    caster.setMana(caster.getMana() - manaCost);
                }

                // Deduct health
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
}
