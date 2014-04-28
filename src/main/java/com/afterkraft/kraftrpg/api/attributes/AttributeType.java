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

    MANA_COST_REDUCTION("Mana Cost Reduction"),
    MANA_COST_INCREASE("Mana Cost Increase"),
    HEALTH_COST_INCREASE("Health Cost Increase"),
    HEALTH_COST_REDUCTION("Health Cost Reduction"),
    SKILL_DAMAGE_INCREASE("Skill Damage Increase"),
    SKILL_DAMAGE_REDUCTION("Skill Damage Reduction"),
    SKILL_COOLDOWN_REDUCTION("Skill Cooldown Reduction"),
    SKILL_COOLDOWN_INCRASE("Skill Cooldown Increase"),
    SKILL_BUFF_INCREASE("Skill Buff Increase"),
    SKILL_BUFF_REDUCTION("Skill Buff Reduction");
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
