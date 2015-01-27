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

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.PartyMember;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.entity.Summon;
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
 * IEntity, Entity)} is not used.  Only experienced developers wishing to add further
 * customizations
 *
 * @param <E> The entity type to target
 */
public abstract class TargetedSkill<E extends Entity> extends ActiveSkill implements Targeted<E> {

    /**
     * Creates a new {@link Targeted} skill.
     *
     * @param plugin The plugin
     * @param name The name of the skill
     * @param entityClass The target entity class
     */
    protected TargetedSkill(RPGPlugin plugin, String name, Class<E> entityClass) {
        this(plugin, name, entityClass, 10);
    }

    /**
     * Creates a new {@link Targeted} skill with a maximum raytracing targeting distance.
     *
     * @param plugin Theplugin
     * @param name The name of this skill
     * @param entityClass The targeting entity class
     * @param maxDistance The maximum distance
     */
    protected TargetedSkill(RPGPlugin plugin, String name, Class<E> entityClass, int maxDistance) {
        super(plugin, name);
        checkNotNull(entityClass);
        this.skillArguments = new SkillArgument<?>[]
                { new EntitySkillArgument<>(maxDistance, entityClass)};
        setDefault(SkillSetting.MAX_DISTANCE, maxDistance);
    }

    @Override
    public final SkillCastResult useSkill(SkillCaster caster) {
        checkNotNull(caster);
        if (caster.hasEffectType(EffectType.BLIND)) {
            caster.sendMessage("You cannot target anything while blinded!");
            return SkillCastResult.INVALID_TARGET;
        }
        Optional<E> targetOption = this.<EntitySkillArgument<E>>getArgument(0).get().getValue();
        if (!targetOption.isPresent()) {
            return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
        }
        E target = targetOption.get();

        double distance = this.plugin.getSkillConfigManager().getUsedIntSetting(caster, this,
                SkillSetting.MAX_DISTANCE);
        if (target.getLocation()
                .getPosition()
                .distance(caster.getLocation().getPosition()) > distance) {
            return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
        }
        IEntity entity = this.plugin.getEntityManager().getEntity(target).get();
        if (entity instanceof Insentient) {
            Insentient insentient = (Insentient) entity;
            if (caster.equals(insentient)) {
                if (this.isType(SkillType.AGGRESSIVE)
                        || this.isType(SkillType.NO_SELF_TARGETTING)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }
            if (caster.hasEffectType(EffectType.BLIND)) {
                caster.sendMessage("You cannot target anything while blinded!");
                return SkillCastResult.UNTARGETABLE_TARGET;
            }

            if (caster.getParty().isPresent() && insentient instanceof PartyMember) {
                if (this.plugin.getPartyManager().isFriendly(caster, (PartyMember) insentient)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

            if (this.isType(SkillType.DAMAGING)) {
                if (!damageCheck(caster, insentient.getEntity().get())
                        || (insentient instanceof Summon
                        && (caster.equals(((Summon) insentient).getSummoner())))) {
                    caster.sendMessage("You cannot damage that target!");
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

        }
        return useSkill(caster, entity, target);
    }

}