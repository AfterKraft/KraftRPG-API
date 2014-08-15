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


import org.apache.commons.lang.Validate;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.PartyMember;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.entity.Summon;
import com.afterkraft.kraftrpg.api.skills.arguments.EntitySkillArgument;

/**
 * The default implementation of a {@link Targeted} skill. This implementation
 * handles automatic creation of the required first argument being an
 * {@link EntitySkillArgument} with a default targeting distance of 10.
 * It should be noted that {@link #useSkill(SkillCaster)} is final because of
 * initial target handling checks, if the target is an {@link Insentient} being
 * and if that being has {@link EffectType}s that prevent it from being damaged,
 * the {@link #useSkill(SkillCaster, IEntity, Entity)} is not used.
 *
 * Only experienced developers wishing to add further customizations
 * @param <E> The entity type to target
 */
public abstract class TargetedSkill<E extends Entity> extends ActiveSkill implements Targeted<E> {

    protected TargetedSkill(RPGPlugin plugin, String name, Class<E> entityClass) {
        this(plugin, name, entityClass, 10);
    }

    protected TargetedSkill(RPGPlugin plugin, String name, Class<E> entityClass, int maxDistance) {
        super(plugin, name);
        addSkillArgument(new EntitySkillArgument<E>(maxDistance, entityClass));
        setDefault(SkillSetting.MAX_DISTANCE, maxDistance);
    }

    protected void setTargetArgument(EntitySkillArgument<E> argument) {
        if (this.plugin.isEnabled()) {
            throw new IllegalStateException("KraftRPG is already enabled! Cannot modify Skill Arguments after being enabled.");
        }
        Validate.notNull(argument, "Cannot set the targeting argument as null!");
        if (this.skillArguments == null) {
            addSkillArgument(argument);
        } else {
            this.skillArguments[0] = argument;
        }
    }

    @Override
    public final SkillCastResult useSkill(SkillCaster caster) {
        if (caster.hasEffectType(EffectType.BLIND)) {
            caster.sendMessage("You cannot target anything while blinded!");
            return SkillCastResult.INVALID_TARGET;
        }
        E target = this.<EntitySkillArgument<E>>getArgument(0).getMatchedEntity();

        double distance = this.plugin.getSkillConfigManager().getUsedIntSetting(caster, this, SkillSetting.MAX_DISTANCE);
        if (target == null || target.getLocation().distance(caster.getLocation()) > distance) {
            return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
        }
        IEntity entity = this.plugin.getEntityManager().getEntity(target);
        if (entity instanceof Insentient) {
            Insentient insentient = (Insentient) entity;
            if (caster.equals(insentient)) {
                if (this.isType(SkillType.AGGRESSIVE) || this.isType(SkillType.NO_SELF_TARGETTING)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }
            if (caster.hasEffectType(EffectType.BLIND)) {
                caster.sendMessage("You cannot target anything while blinded!");
                return SkillCastResult.UNTARGETABLE_TARGET;
            }

            if (caster.hasParty() && insentient instanceof PartyMember) {
                if (this.plugin.getPartyManager().isFriendly(caster, (PartyMember) insentient)) {
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

            if (this.isType(SkillType.DAMAGING)) {
                if (!damageCheck(caster, insentient.getEntity()) || (insentient instanceof Summon && (caster.equals(((Summon) insentient).getSummoner())))) {
                    caster.sendMessage("You cannot damage that target!");
                    return SkillCastResult.INVALID_TARGET_NO_MESSAGE;
                }
            }

        }
        return useSkill(caster, entity, target);
    }

}
