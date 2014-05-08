package com.afterkraft.kraftrpg.api.util;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

public interface SkillRequirement {
    public static final SkillRequirement TRUE = new SkillRequirement() {
        @Override
        public boolean isSatisfied(SkillCaster caster) {
            return true;
        }
    };

    public boolean isSatisfied(SkillCaster caster);
}
