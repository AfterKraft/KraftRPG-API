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
package com.afterkraft.kraftrpg.api.skills;

import org.spongepowered.api.entity.Entity;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.common.skills.TargetedSkill;

/**
 * A Targeted skill is an Active skill that requires an {@link IEntity} target. The skill will
 * usually perform some sort of special effect at the targeted entity, however, special note should
 * be taken care of: Targeted uses the type E to allow refinement as to the types of Entities are
 * valid for the skill. If the skill requires a Creature, {@link #useSkill(SkillCaster, IEntity,
 * Entity)} will only be called when the SkillCaster has targeted a Creature.  The default
 * implementation is a {@link TargetedSkill}.
 *
 * @param <E> The entity type to target
 */
public interface Targeted<E extends Entity> extends Active {

    /**
     * Apply this skill using the previously parsed state and includes the defined Entity target
     * that is found with ray-tracing. The IEntity is provided as a convenience to avoid having to
     * fetch it from the EntityManager. Likewise, the targeted Bukkit entity is provided for
     * convenience as well.
     *
     * @param caster The caster using this skill
     * @param target The KraftRPG entity targeted
     * @param entity The raw bukkit entity targeted
     *
     * @return final SkillCastResults
     */
    SkillCastResult useSkill(SkillCaster caster, IEntity target, E entity);
}
