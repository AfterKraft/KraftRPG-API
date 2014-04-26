package com.afterkraft.kraftrpg.api.spells;

/**
 * @author gabizou
 */
public enum SpellCastResult {

    DEAD,
    NOT_DEFINED_IN_ACTIVE_ROLES,
    RESTRICTED_IN_ROLES,
    CANCELLED,
    INVALID_TARGET,
    FAIL,
    ROLE_RESTRICTED,
    LOW_MANA,
    LOW_HEALTH,
    LOW_LEVEL,
    LOW_STAMINA,
    MISSING_REAGENT,
    NO_COMBAT,
    NORMAL,
    ON_GLOBAL_COOLDOWN,
    ON_COOLDOWN,
    REMOVED_EFFECT,
    SKIP_POST_USAGE,
    START_DELAY,
    ON_WARMUP,
    UNTARGETTABLE_TARGET


}
