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
package com.afterkraft.kraftrpg.api.event.damage;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.spongepowered.api.event.cause.entity.damage.source.common.AbstractDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.common.AbstractDamageSourceBuilder;

import com.google.common.base.Objects;

import com.afterkraft.kraftrpg.api.skill.Skill;

public class SkillDamageSource extends AbstractDamageSource {

    private final Skill skill;

    public static Builder builder() {
        return new Builder();
    }


    public SkillDamageSource(AbstractSkillDamageSource<?, ?> builder) {
        super(builder);
        this.skill = builder.skill;
    }

    public final Skill getSkill() {
        return this.skill;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", this.getType().getId())
                .add("isAbsolute", this.isAbsolute())
                .add("BypassesArmor", this.isBypassingArmor())
                .add("DifficultyScaled", this.isScaledByDifficulty())
                .add("Explosive", this.isExplosive())
                .add("Magical", this.isMagic())
                .add("AffectsCreative", this.doesAffectCreative())
                .add("skill", this.skill)
                .toString();
    }


    public static final class Builder extends AbstractSkillDamageSource<SkillDamageSource, Builder> {

        @Override
        public SkillDamageSource build() throws IllegalStateException {
            checkState(this.skill != null, "Skill cannot be null!");
            checkState(this.damageType != null, "DamageType cannot be null!");
            return new SkillDamageSource(this);
        }
    }

    public static abstract class AbstractSkillDamageSource<T extends SkillDamageSource, B extends
            AbstractSkillDamageSource<T, B>> extends AbstractDamageSourceBuilder<T, B> {

        protected Skill skill;

        public B skill(Skill skill) {
            this.skill = checkNotNull(skill, "Skill was null!");
            return (B) this;
        }

    }
}
