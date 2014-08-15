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

import org.bukkit.entity.Entity;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * A Targeted skill is an Active skill that requires an {@link IEntity} target.
 * The skill will usually perform some sort of special effect at the targeted
 * entity, however, special note should be taken care of:
 * Targeted uses the type E to allow refinement as to the types of Entities are
 * valid for the skill. If the skill requires a Creature, {@link #useSkill(SkillCaster, IEntity, Entity)}
 * will only be called when the SkillCaster has targeted a Creature.
 *
 * The default implementation is a {@link TargetedSkill}.
 *
 * @param <E> The entity type to target
 */
public interface Targeted<E extends Entity> extends Active {

    /**
     * Apply this skill using the previously parsed state and includes the
     * defined Entity target that is found with ray-tracing. The IEntity is
     * provided as a convenience to avoid having to fetch it from the
     * EntityManager. Likewise, the targeted Bukkit entity is provided for
     * convenience as well.
     *
     * @param caster The caster using this skill
     * @param target The KraftRPG entity targeted
     * @param entity The raw bukkit entity targeted
     * @return final SkillCastResults
     */
    public SkillCastResult useSkill(SkillCaster caster, IEntity target, E entity);
}
