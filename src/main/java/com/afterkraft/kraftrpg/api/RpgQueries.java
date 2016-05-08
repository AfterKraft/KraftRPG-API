package com.afterkraft.kraftrpg.api;

import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.DataQuery;

public final class RpgQueries {

    public static final DataQuery FIXED_POINT_RAW = of("RawFixedPoint");
    public static final class RoleSkill {
        public static final DataQuery SKILL_NAME = of("SkillName");
        public static final DataQuery SKILL_CONFIGURATION = of("SkillConfiguration");
    }

    private RpgQueries() { }
}
