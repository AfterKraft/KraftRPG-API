package com.afterkraft.kraftrpg.api.attributes;

import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

/**
 * Attributes are defined through configurations and different attributes have
 * different effects. AttributeTypes are defined by the configurations and
 * {@link com.afterkraft.kraftrpg.api.spells.Spell}s can use the various
 * attribute types to define final damage, reagent requirements, cooldowns and
 * even immunity to some effects.
 */
public enum AttributeType {

    MANA_COST_REDUCTION("Mana Cost Reduction"),
    MANA_COST_INCREASE("Mana Cost Increase"),
    HEALTH_COST_INCREASE("Health Cost Increase"),
    HEALTH_COST_REDUCTION("Health Cost Reduction"),
    SPELL_DAMAGE_INCREASE("Spell Damage Increase"),
    SPELL_DAMAGE_REDUCTION("Spell Damage Reduction"),
    SPELL_COOLDOWN_REDUCTION("Spell Cooldown Reduction"),
    SPELL_COOLDOWN_INCRASE("Spell Cooldown Increase"),
    SPELL_BUFF_INCREASE("Spell Buff Increase"),
    SPELL_BUFF_REDUCTION("Spell Buff Reduction");
    private final static Map<String, AttributeType> BY_NAME = Maps.newHashMap();

    static {
        for (AttributeType attribute : values()) {
            BY_NAME.put(attribute.toString(), attribute);
            BY_NAME.put(attribute.toString().subSequence(0, 3).toString(), attribute);
        }
    }

    private final String name;

    private AttributeType(String name) {
        this.name = name;
    }

    public static AttributeType getAttributeType(final String name) {
        return BY_NAME.get(name);
    }

    public static AttributeType matchAttributeType(final String name) {
        Validate.notNull(name, "IAttribute Name cannot be null");
        AttributeType result = BY_NAME.get(name.toUpperCase());

        if (result == null) {
            String filtered = name.toUpperCase();
            filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
            result = BY_NAME.get(filtered);
        }
        return result;
    }

    @Override
    public final String toString() {
        return this.name;
    }
}
