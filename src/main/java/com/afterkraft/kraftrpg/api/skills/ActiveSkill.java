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

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.events.skills.SkillCastEvent;
import com.afterkraft.kraftrpg.api.util.SkillRequirement;

/**
 * See {@link Active}.
 */
public abstract class ActiveSkill<T extends SkillArgument> extends Skill implements Active<T> {

    private String usage = "";

    public ActiveSkill(RPGPlugin plugin, String name) {
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
    public void onWarmUp(SkillCaster caster) {

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
            // TODO Change this up to use the specific Role level
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

            manaCost = skillEvent.getManaCost();
            if (manaCost > caster.getMana()) {
                if (cancelDelayedSkillOnFailure) {
                    caster.cancelStalledSkill(true);
                }
                return SkillCastResult.LOW_MANA;
            }

            healthCost = skillEvent.getHealthCost();
            if (caster instanceof LivingEntity) {
                if ((healthCost > 0) && (caster.getHealth() <= healthCost)) {
                    if (cancelDelayedSkillOnFailure) {
                        caster.cancelStalledSkill(true);
                    }
                    return SkillCastResult.LOW_HEALTH;
                }
            }

            skillRequirement = skillEvent.getRequirement();
            if ((skillRequirement != null) && !hasSkillRequirement(skillRequirement, caster)) {
                if (cancelDelayedSkillOnFailure) {
                    caster.cancelStalledSkill(true);
                }
                return SkillCastResult.MISSING_REAGENT;
            }


            int globalCD = plugin.getProperties().getDefaultGlobalCooldown();
            dSkill = new StalledSkill<T>(this, argument, caster, System.currentTimeMillis(), delay);
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
                dSkill = caster.getStalledSkill();

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
            skillResult = useSkill(caster, argument);

            if (skillResult == SkillCastResult.NORMAL) {
                time = System.currentTimeMillis();
                if (cooldown > 0) {
                    caster.setCooldown(name, time + cooldown);
                }

                if (globalCD > 0) {
                    if (!(dSkill instanceof StalledSkill)) {
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
}
