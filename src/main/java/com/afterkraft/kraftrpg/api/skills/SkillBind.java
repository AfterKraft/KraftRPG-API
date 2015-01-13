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
package com.afterkraft.kraftrpg.api.skills;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.service.persistence.DataSerializable;
import org.spongepowered.api.service.persistence.DataSource;
import org.spongepowered.api.service.persistence.data.DataContainer;

/**
 * Represents a binding of a Skill to an item type with prescribed skill arguments as a single
 * string. This can be serialized and saved to any form of database. This is primarily used by
 * Champions.
 */
public final class SkillBind implements DataSerializable {
    private final ItemType material;
    private final String skillName;
    private final String arguments;

    public SkillBind(ItemType material, String skillName, String argument) {
        this.material = material;
        this.skillName = skillName;
        this.arguments = argument;
    }

    public ItemType getMaterial() {
        return this.material;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public String getSkillArgument() {
        return this.arguments;
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("material", this.material.getId());
        ret.put("skill", this.skillName);
        ret.put("args", this.arguments);
        return ret;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }

    @Override
    public void serialize(DataSource source) {
    }
}
