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

import java.util.List;

import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.util.SkillRequirement;

/**
 * See {@link Active}.
 */
public abstract class ActiveSkill extends Skill implements Active {
    private String usage = "";
    private SkillArgument[] skillArguments;
    private SkillCaster parsedCaster;

    public ActiveSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    /**
     * Set the SkillArguments to be used in parsing.
     * 
     * @param arguments to set
     */
    public void setSkillArguments(SkillArgument... arguments) {
        skillArguments = arguments;
    }

    /**
     * Get a casted SkillArgument. You should know the position of all your
     * arguments and be able to use this correctly.
     * 
     * @param index The index into the objects you passed into
     *            setSkillArguments
     * @return a SkillArgument
     */
    @SuppressWarnings("unchecked")
    public <T extends SkillArgument> T getArgument(int index) {
        return (T) skillArguments[index];
    }

    @Override
    public final String getUsage() {
        return this.usage;
    }

    /**
     * {@inheritDoc}
     * 
     * As not all skills will want this method, subclasses should override it
     * if desired.
     */
    @Override
    public void onWarmUp(SkillCaster caster) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean parse(SkillCaster caster, String[] strings) {
        parsedCaster = caster;
        int stringIndex = 0, argIndex = 0;

        while (stringIndex < strings.length && argIndex < skillArguments.length) {
            SkillArgument current = skillArguments[argIndex];

            int width = current.matches(caster, strings, stringIndex);
            if (width >= 0) {
                current.parse(caster, strings, stringIndex);
                current.present = true;
                stringIndex += width;
            } else {
                if (current.isOptional()) {
                    current.present = false;
                } else {
                    return false;
                }
            }

            argIndex++;
        }

        while (argIndex < skillArguments.length) {
            SkillArgument current = skillArguments[argIndex];

            if (current.isOptional()) {
                current.present = false;
            } else {
                return false;
            }
        }

        return true;
    }

    public List<String> tabComplete(SkillCaster caster, String[] strings) {
        parsedCaster = caster;

        int stringIndex = 0, argIndex = 0;
        while (stringIndex < strings.length - 1 && argIndex < skillArguments.length) {
            SkillArgument current = skillArguments[argIndex];

            int width = current.matches(caster, strings, stringIndex);
            if (width >= 0) {
                stringIndex += width;
                argIndex++;
            } else {
                break;
            }
        }

        if (argIndex == skillArguments.length) {
            return ImmutableList.of();
        } else {
            return skillArguments[argIndex].tabComplete(caster, strings, stringIndex);
        }
    }

    public abstract SkillCastResult useSkill(SkillCaster caster);

    @Override
    public void cleanState(SkillCaster caster) {
        assert caster == parsedCaster;

        for (SkillArgument arg : skillArguments) {
            arg.clean();
        }
    }

    @Override
    public SkillRequirement getSkillRequirement(SkillCaster caster) {
        return SkillRequirement.TRUE;
    }

    public abstract SkillCastResult canUse(SkillCaster caster);

    /*
     * @Override public SkillCastResult canCast(SkillCaster caster, String[]
     * args, boolean forced) { final String name = this.getName(); if (caster
     * == null) { return null; }
     * 
     * if (caster.isDead()) { return SkillCastResult.DEAD; }
     * 
     * if (!forced) { if (!caster.canPrimaryUseSkill(this) ||
     * !caster.canSecondaryUseSkill(this) ||
     * !caster.canAdditionalUseSkill(this)) { return
     * SkillCastResult.NOT_DEFINED_IN_ACTIVE_ROLES; } else if
     * (caster.doesPrimaryRestrictSkill(this) ||
     * caster.doesSecondaryRestrictSkill(this)) { return
     * SkillCastResult.RESTRICTED_IN_ROLES; } // TODO Change this up to use
     * the specific Role level final int level =
     * plugin.getSkillConfigManager().getUseSetting(caster, this,
     * SkillSetting.LEVEL, 1, true); int champLevel =
     * caster.getHighestSkillLevel(this); if (champLevel != 0 && champLevel <
     * level) { return SkillCastResult.LOW_LEVEL; } if (champLevel == 0) {
     * champLevel = 1; }
     * 
     * long time = System.currentTimeMillis(); final long global =
     * caster.getGlobalCooldown(); if
     * (!plugin.getSkillManager().isCasterDelayed(caster)) { if (time <
     * global) { return SkillCastResult.ON_GLOBAL_COOLDOWN; } }
     * 
     * int cooldown = plugin.getSkillConfigManager().getUseSetting(caster,
     * this, SkillSetting.COOLDOWN, 0, true); if (cooldown > 0) { final Long
     * expiry = caster.getCooldown(name); if ((expiry != null) && (time <
     * expiry)) { return SkillCastResult.ON_COOLDOWN; } }
     * 
     * final int delay = plugin.getSkillConfigManager().getUseSetting(caster,
     * this, SkillSetting.DELAY, 0, true);
     * 
     * if (!isType(SkillType.UNINTERRUPTIBLE)) { if (caster.isInCombat() &&
     * plugin.getSkillConfigManager().getUseSetting(caster, this,
     * SkillSetting.NO_COMBAT_USE, false)) { if (delay > 0) { final Stalled
     * dSkill = caster.getStalledSkill(); if ((dSkill != null) &&
     * dSkill.getActiveSkill().equals(this)) {
     * caster.cancelStalledSkill(true); } } return SkillCastResult.NO_COMBAT;
     * } }
     * 
     * int manaCost = plugin.getSkillConfigManager().getUseSetting(caster,
     * this, SkillSetting.MANA, 0, true); final double manaReduce =
     * plugin.getSkillConfigManager().getUseSetting(caster, this,
     * SkillSetting.MANA_REDUCE_PER_LEVEL, 0.0, false) * champLevel; manaCost
     * -= (int) manaReduce;
     * 
     * SkillRequirement skillRequirement = getSkillRequirement(caster);
     * 
     * double healthCost =
     * plugin.getSkillConfigManager().getUseSetting(caster, this,
     * SkillSetting.HEALTH_COST, 0, true);
     * 
     * final SkillCastEvent skillEvent = new SkillCastEvent(caster, this,
     * manaCost, healthCost, skillRequirement);
     * plugin.getServer().getPluginManager().callEvent(skillEvent); if
     * (skillEvent.isCancelled()) { return SkillCastResult.CANCELLED; }
     * 
     * boolean cancelDelayedSkillOnFailure = false; Stalled dSkill =
     * caster.getStalledSkill(); if ((dSkill != null) &&
     * dSkill.getActiveSkill().equals(this)) { cancelDelayedSkillOnFailure =
     * true; }
     * 
     * manaCost = skillEvent.getManaCost(); if (manaCost > caster.getMana()) {
     * if (cancelDelayedSkillOnFailure) { caster.cancelStalledSkill(true); }
     * return SkillCastResult.LOW_MANA; }
     * 
     * healthCost = skillEvent.getHealthCost(); if (caster instanceof
     * LivingEntity) { if ((healthCost > 0) && (caster.getHealth() <=
     * healthCost)) { if (cancelDelayedSkillOnFailure) {
     * caster.cancelStalledSkill(true); } return SkillCastResult.LOW_HEALTH; }
     * }
     * 
     * skillRequirement = skillEvent.getRequirement(); if ((skillRequirement
     * != null) && !hasSkillRequirement(skillRequirement, caster)) { if
     * (cancelDelayedSkillOnFailure) { caster.cancelStalledSkill(true); }
     * return SkillCastResult.MISSING_REAGENT; }
     * 
     * 
     * int globalCD = plugin.getProperties().getDefaultGlobalCooldown();
     * dSkill = (Stalled) new StalledSkill(this, argument, caster,
     * System.currentTimeMillis(), delay); if ((delay > 0) &&
     * !plugin.getSkillManager().isCasterDelayed(caster)) { return
     * SkillCastResult.START_DELAY; } else if
     * (plugin.getSkillManager().isCasterDelayed(caster)) { dSkill =
     * caster.getStalledSkill();
     * 
     * if (dSkill.getActiveSkill().equals(this)) { if (!dSkill.isReady()) {
     * return SkillCastResult.ON_WARMUP; } else { return SkillCastResult.FAIL;
     * } } else { return SkillCastResult.FAIL; } }
     * 
     * SkillCastResult skillResult; skillResult = useSkill(caster, argument);
     * 
     * if (skillResult == SkillCastResult.NORMAL) { time =
     * System.currentTimeMillis(); if (cooldown > 0) {
     * caster.setCooldown(name, time + cooldown); }
     * 
     * if (globalCD > 0) { if (!(dSkill instanceof StalledSkill)) { if
     * (cooldown < globalCD) { if (cooldown < 100) { cooldown = 100; }
     * 
     * caster.setGlobalCooldown(cooldown + time); } else {
     * caster.setGlobalCooldown(globalCD + time); } } }
     * 
     * // Award XP for skill usage if (grantsExperienceOnCast()) {
     * this.awardExperience(caster); }
     * 
     * // Deduct mana if (manaCost > 0) { caster.setMana(caster.getMana() -
     * manaCost); }
     * 
     * // Deduct health if (healthCost > 0) {
     * caster.setHealth(caster.getHealth() - healthCost); }
     * 
     * if (skillRequirement != null) {
     * caster.removeSkillRequirement(skillRequirement);
     * caster.updateInventory(); }
     * 
     * } return skillResult; } else { return SkillCastResult.NORMAL; }
     * 
     * }
     */
}
