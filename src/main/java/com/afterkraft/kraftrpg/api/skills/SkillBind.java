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
package com.afterkraft.kraftrpg.api.skills;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("krpg-bind")
public final class SkillBind implements ConfigurationSerializable {
    private final Material material;
    private final String skillName;
    private final String arguments;

    public SkillBind(Map<String, Object> data) {
        this(Material.matchMaterial((String) data.get("material")), (String) data.get("skill"), (String) data.get("args"));
    }

    public SkillBind(Material material, String skillName, String argument) {
        this.material = material;
        this.skillName = skillName;
        this.arguments = argument;
    }

    public Material getMaterial() {
        return material;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public String getSkillArgument() {
        return this.arguments;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("material", material.name());
        ret.put("skill", skillName);
        ret.put("args", arguments);
        return ret;
    }
}
