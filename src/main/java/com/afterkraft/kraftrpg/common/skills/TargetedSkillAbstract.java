/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.common.skills;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.message.Message;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.Being;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.entity.component.EffectsComponent;
import com.afterkraft.kraftrpg.api.entity.component.MessagingComponent;
import com.afterkraft.kraftrpg.api.entity.component.PartyComponent;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;
import com.afterkraft.kraftrpg.api.skills.SkillCastResult;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;
import com.afterkraft.kraftrpg.api.skills.SkillType;
import com.afterkraft.kraftrpg.api.skills.Targeted;
import com.afterkraft.kraftrpg.common.skills.arguments.EntitySkillArgument;

/**
 * The default implementation of a {@link Targeted} skill. This implementation handles automatic
 * creation of the required first argument being an {@link EntitySkillArgument} with a default
 * targeting distance of 10. It should be noted that {@link #useSkill(SkillCaster)} is final because
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
    protected TargetedSkillAbstract(RPGPlugin plugin, String name, Message description,
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
    protected TargetedSkillAbstract(RPGPlugin plugin, String name, Message description,
                                    Class<E> entityClass, int maxDistance) {
        super(plugin, name, description);
        checkNotNull(entityClass);
        this.skillArguments = new SkillArgument<?>[]
                {new EntitySkillArgument<>(maxDistance, entityClass)};
        setDefault(SkillSetting.MAX_DISTANCE, maxDistance);
    }

    @Override
    public final SkillCastResult useSkill(final SkillCaster caster) {
        checkNotNull(caster);
        Optional<EffectsComponent> effect = caster.getComponent(EffectsComponent.class);
        if (effect.get().hasEffectType(EffectType.BLIND)) {
            Optional<MessagingComponent> messaging = caster.getComponent(MessagingComponent.class);
            if (messaging.isPresent()) {
                messaging.get().sendMessage("You cannot target anything while blinded!");
            }
            return SkillCastResult.INVALID_TARGET;
        }
        Optional<E> targetOption =
                this.<EntitySkillArgument<E>>getArgument(0).get().getValue();
        if (!targetOption.isPresent()) {
            return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
        }
        E target = targetOption.get();

        double distance = this.plugin.getSkillConfigManager()
                .getUsedIntSetting(caster, this,
                                   SkillSetting.MAX_DISTANCE);
        if (target.getLocation()
                .getPosition()
                .distance(caster.getLocation().getPosition()) > distance) {
            return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
        }
        Being being = this.plugin.getEntityManager().getEntity(target).get();
        if (being instanceof Insentient) {
            Insentient insentient = (Insentient) being;
            if (caster.equals(insentient)) {
                if (this.isType(SkillType.AGGRESSIVE)
                        || this.isType(SkillType.NO_SELF_TARGETTING)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

            Optional<PartyComponent> party = caster.getComponent(PartyComponent.class);
            if (party.isPresent() && party.get().getParty().isPresent()) {
                if (this.plugin.getPartyManager()
                        .isFriendly(caster, insentient)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

            if (this.isType(SkillType.DAMAGING)) {
                if (!damageCheck(caster, insentient.getEntity().get())) {
                    Optional<MessagingComponent> messaging =
                            caster.getComponent(MessagingComponent.class);
                    if (messaging.isPresent()) {
                        messaging.get().sendMessage("You cannot damage that target!");
                    }
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

        }
        return useSkill(caster, being, target);
    }

}
