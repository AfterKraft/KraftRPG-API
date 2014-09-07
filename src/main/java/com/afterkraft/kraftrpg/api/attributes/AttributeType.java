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
package com.afterkraft.kraftrpg.api.attributes;

import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

/**
 * Attributes are defined through configurations and different attributes have
 * different effects. AttributeTypes are defined by the configurations and
 * {@link com.afterkraft.kraftrpg.api.skills.Skill}s can use the various
 * attribute types to define final damage, reagent requirements, cooldowns and
 * even immunity to some effects.
 */
public enum AttributeType {

    MANA_COST_REDUCTION("Mana Cost Reduction", "mana-cost-reduction"),
    MANA_COST_INCREASE("Mana Cost Increase", "mana-cost-increase"),
    HEALTH_COST_INCREASE("Health Cost Increase", "health-cost-increase"),
    HEALTH_COST_REDUCTION("Health Cost Reduction", "health-cost-reduction"),
    SKILL_DAMAGE_INCREASE("Skill Damage Increase", "skill-damage-increase"),
    SKILL_DAMAGE_REDUCTION("Skill Damage Reduction", "skill-damage-reduction"),
    SKILL_COOLDOWN_REDUCTION("Skill Cooldown Reduction", "skill-cooldown-reduction"),
    SKILL_COOLDOWN_INCRASE("Skill Cooldown Increase", "skill-cooldown-increase"),
    SKILL_BUFF_INCREASE("Skill Buff Increase", "skill-buff-increase"),
    SKILL_BUFF_REDUCTION("Skill Buff Reduction", "skill-buff-reduction");
    private final static Map<String, AttributeType> BY_NAME = Maps.newHashMap();

    static {
        for (AttributeType attribute : values()) {
            BY_NAME.put(attribute.toString(), attribute);
            BY_NAME.put(attribute.toString().subSequence(0, 3).toString(), attribute);
        }
    }

    private final String name;
    private final String config;

    private AttributeType(String name, String config) {
        this.name = name;
        this.config = config;
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
