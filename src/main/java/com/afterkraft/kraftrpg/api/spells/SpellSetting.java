package com.afterkraft.kraftrpg.api.spells;

/**
 * @author gabizou
 */
public enum SpellSetting {

    AMOUNT("amount"),
    APPLY_TEXT("apply-text"),
    CHANCE("chance"),
    CHANCE_PER_LEVEL("chance-per-level"),
    COOLDOWN("cooldown"),
    DAMAGE("damage"),
    DAMAGE_INCREASE_PER_AGILITY("damage-increase-per-agility"),
    DAMAGE_INCREASE_PER_CHARISMA("damage-increase-per-charisma"),
    DAMAGE_INCREASE_PER_INTELLECT("damage-increase-per-intellect"),
    DAMAGE_INCREASE_PER_STRENGTH("damage-increase-per-strength"),
    DAMAGE_INCREASE_PER_WISDOM("damage-increase-per-wisdom"),
    DAMAGE_TICK("tick-damage"),
    DAMAGE_TICK_INCREASE_PER_AGILITY("tick-damage-increase-per-agility"),
    DAMAGE_TICK_INCREASE_PER_CHARISMA("tick-damage-increase-per-charisma"),
    DAMAGE_TICK_INCREASE_PER_INTELLECT("tick-damage-increase-per-intellect"),
    DAMAGE_TICK_INCREASE_PER_STRENGTH("tick-damage-increase-per-strength"),
    DAMAGE_TICK_INCREASE_PER_WISDOM("tick-damage-increase-per-wisdom"),
    DEATH_TEXT("death-text"),
    DELAY_TEXT("delay-text"),
    DELAY("delay"),
    DURATION("duration"),
    DURATION_INCREASE_PER_LEVEL("duration-increase-per-level"),
    DURATION_INCREASE_PER_AGILITY("duration-increase-per-agility"),
    DURATION_INCREASE_PER_CHARISMA("duration-increase-per-charisma"),
    DURATION_INCREASE_PER_INTELLECT("duration-increase-per-intellect"),
    DURATION_INCREASE_PER_STRENGTH("duration-increase-per-strength"),
    DURATION_INCREASE_PER_WISDOM("duration-increase-per-wisdom"),
    EFFECTIVENESS_INCREASE_PER_AGILITY("effectiveness-increase-per-agility"),
    EFFECTIVENESS_INCREASE_PER_CHARISMA("effectiveness-increase-per-charisma"),
    EFFECTIVENESS_INCREASE_PER_INTELLECT("effectiveness-increase-per-intellect"),
    EFFECTIVENESS_INCREASE_PER_STRENGTH("effectiveness-increase-per-strength"),
    EFFECTIVENESS_INCREASE_PER_WISDOM("effectiveness-increase-per-wisdom"),
    EXP("exp"),
    EXPIRE_TEXT("expire-text"),
    HEALING("healing"),
    HEALING_INCREASE_PER_AGILITY("healing-increase-per-agility"),
    HEALING_INCREASE_PER_CHARISMA("healing-increase-per-charisma"),
    HEALING_INCREASE_PER_INTELLECT("healing-increase-per-intellect"),
    HEALING_INCREASE_PER_STRENGTH("healing-increase-per-strength"),
    HEALING_INCREASE_PER_WISDOM("healing-increase-per-wisdom"),
    HEALING_TICK("tick-healing"),
    HEALING_TICK_INCREASE_PER_AGILITY("tick-healing-increase-per-agility"),
    HEALING_TICK_INCREASE_PER_CHARISMA("tick-healing-increase-per-charisma"),
    HEALING_TICK_INCREASE_PER_INTELLECT("tick-healing-increase-per-intellect"),
    HEALING_TICK_INCREASE_PER_STRENGTH("tick-healing-increase-per-strength"),
    HEALING_TICK_INCREASE_PER_WISDOM("tick-healing-increase-per-wisdom"),
    HEALTH_COST("health-cost"),
    INTERRUPT_TEXT("interrupt-text"),
    LEVEL("level"),
    MANA("mana"),
    MANA_REDUCE_PER_LEVEL("mana-reduce-per-level"),
    MAX_DISTANCE("max-distance"),
    MAX_DISTANCE_INCREASE_PER_AGILITY("max-distance-increase-per-agility"),
    MAX_DISTANCE_INCREASE_PER_CHARISMA("max-distance-increase-per-charisma"),
    MAX_DISTANCE_INCREASE_PER_INTELLECT("max-distance-increase-per-intellect"),
    MAX_DISTANCE_INCREASE_PER_STRENGTH("max-distance-increase-per-strength"),
    MAX_DISTANCE_INCREASE_PER_WISDOM("max-distance-increase-per-wisdom"),
    NO_COMBAT_USE("no-combat-use"),
    PERIOD("period"),
    RADIUS("radius"),
    RADIUS_INCREASE_PER_AGILITY("radius-increase-per-agility"),
    RADIUS_INCREASE_PER_CHARISMA("radius-increase-per-charisma"),
    RADIUS_INCREASE_PER_INTELLECT("radius-increase-per-intellect"),
    RADIUS_INCREASE_PER_STRENGTH("radius-increase-per-strength"),
    RADIUS_INCREASE_PER_WISDOM("radius-increase-per-wisdom"),
    REAGENT_COST("reagent-cost"),
    REAGENT("reagent"),
    STAMINA("stamina"),
    UNAPPLY_TEXT("unapply-text"),
    USE_TEXT("use-text");

    private final String node;

    SpellSetting(String node) {
        this.node = node;
    }

    public String node() {
        return this.node;
    }

    @Override
    public String toString() {
        return this.node;
    }
}