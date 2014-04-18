package com.afterkraft.kraftrpg.api.attributes;

import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

/**
 * @author gabizou
 */
public enum AttributeType {
    STRENGHT("Strength"),
    DEXTERITY("Dexterity"),
    INTELLIGENCE("Intelligence"),
    VITALITY("Vitality");

    private final String name;
    private AttributeType(String name) {
        this.name = name;
    }

    @Override
    public final String toString() {
        return this.name;
    }

    private final static Map<String, AttributeType> BY_NAME = Maps.newHashMap();

    static {
        for (AttributeType attribute : values()) {
            BY_NAME.put(attribute.toString(), attribute);
            BY_NAME.put(attribute.toString().subSequence(0, 3).toString(), attribute);
        }
    }

    public static AttributeType getAttribute(final String name) {
        return BY_NAME.get(name);
    }

    public static AttributeType matchAttribute(final String name) {
        Validate.notNull(name, "IAttribute Name cannot be null");
        AttributeType result = BY_NAME.get(name.toUpperCase());

        if (result == null) {
            String filtered = name.toUpperCase();
            filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
            result = BY_NAME.get(filtered);
        }
        return result;
    }
}
