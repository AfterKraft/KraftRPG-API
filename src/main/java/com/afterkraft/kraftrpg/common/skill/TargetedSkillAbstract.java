/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.common.skill;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.effect.EffectType;
import com.afterkraft.kraftrpg.api.entity.Being;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skill.Active;
import com.afterkraft.kraftrpg.api.skill.SkillCastContext;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.PartyData;
import com.afterkraft.kraftrpg.api.skill.SkillCastResult;
import com.afterkraft.kraftrpg.api.skill.SkillSetting;
import com.afterkraft.kraftrpg.api.skill.SkillType;
import com.afterkraft.kraftrpg.api.skill.Targeted;
import com.afterkraft.kraftrpg.common.data.manipulator.mutable.EffectData;
import com.afterkraft.kraftrpg.common.skill.argument.EntitySkillArgument;

/**
 * The default implementation of a {@link Targeted} skill. This implementation handles automatic
 * creation of the required first argument being an {@link EntitySkillArgument} with a default
 * targeting distance of 10. It should be noted that {@link Active#useSkill(SkillCaster, SkillCastContext)} is final because
 * of initial target handling checks, if the target is an {@link Insentient} being and if that being
 * has {@link EffectType}s that prevent it from being damaged, the {@link #useSkill(SkillCaster,
 * Being, org.spongepowered.api.entity.Entity)} is not used.  Only experienced developers wishing to
 * add further customizations
 *
 * @param <E> The entity type to target
 */
public abstract class TargetedSkillAbstract<E extends Entity>
        extends AbstractActiveSkill
        implements Targeted<E> {

    /**
     * Creates a new {@link Targeted} skill.
     *
     * @param plugin      The plugin
     * @param name        The name of the skill
     * @param description The description of this skill
     * @param entityClass The target entity class
     */
    protected TargetedSkillAbstract(RpgPlugin plugin, String name, Text description,
                                    Class<E> entityClass) {
        this(plugin, name, description, entityClass, 10);
    }

    /**
     * Creates a new {@link Targeted} skill with a maximum raytracing targeting distance.
     *
     * @param plugin      Theplugin
     * @param name        The name of this skill
     * @param description The description of this skill
     * @param entityClass The targeting entity class
     * @param maxDistance The maximum distance
     */
    protected TargetedSkillAbstract(RpgPlugin plugin, String name, Text description,
                                    Class<E> entityClass, int maxDistance) {
        super(plugin, name, description);
        checkNotNull(entityClass);
        setDefault(SkillSetting.MAX_DISTANCE, maxDistance);
    }

    @Override
    public final SkillCastResult useSkill(final SkillCaster caster, SkillCastContext context) {
        checkNotNull(caster);
        Optional<EffectData> effect = caster.get(EffectData.class);
        if (effect.get().hasEffectType(EffectType.BLIND)) {
            if (caster instanceof Champion) {
                ((Champion) caster).sendMessage(Texts.of(TextColors.GRAY, "You cannot target "
                        + "anything while blinded!"));
            }
            return SkillCastResult.INVALID_TARGET;
        }
        Optional<E> targetOption = context.get("TargetedEntity");
        if (!targetOption.isPresent()) {
            return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
        }
        E target = targetOption.get();

        double distance = RpgCommon.getSkillConfigManager()
                .getUsedIntSetting(caster, this,
                                   SkillSetting.MAX_DISTANCE);
        if (target.getLocation()
                .getPosition()
                .distance(caster.getLocation().getPosition()) > distance) {
            return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
        }
        Being being = RpgCommon.getEntityManager().getEntity(target).get();
        if (being instanceof Insentient) {
            Insentient insentient = (Insentient) being;
            if (caster.equals(insentient)) {
                if (this.isType(SkillType.AGGRESSIVE)
                        || this.isType(SkillType.NO_SELF_TARGETTING)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

            Optional<PartyData> party = caster.get(PartyData.class);
            if (party.isPresent()) {
                if (RpgCommon.getPartyManager().isFriendly(caster, insentient)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

            if (this.isType(SkillType.DAMAGING)) {
                if (!SkillUtils.damageCheck(caster, insentient.getEntity().get())) {
                    if (caster instanceof Champion) {
                        ((Champion) caster).sendMessage(Texts.of(TextColors.RED, "You cannot"
                                + " damage that target!"));
                    }
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

        }
        return useSkill(caster, being, target);
    }

}
