/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.skill;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.item.ItemType;

import com.google.common.collect.ImmutableList;

/**
 * Represents a binding of a Skill to an item type with prescribed skill arguments as a single
 * string. This can be serialized and saved to any form of database. This is primarily used by
 * Champions.
 */
public final class SkillBind implements DataSerializable {
    private final ItemType material;
    private final String skillName;
    private final List<String> arguments;

    public SkillBind(ItemType material, String skillName, List<String> argument) {
        this.material = checkNotNull(material);
        this.skillName = checkNotNull(skillName);
        this.arguments = ImmutableList.copyOf(argument);
    }

    public ItemType getMaterial() {
        return this.material;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public List<String> getSkillArgument() {
        return this.arguments;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        // TODO update to use proper data queries
        DataContainer container = new MemoryDataContainer();
        container.set(of("ItemType"), this.material.getId());
        container.set(of("SkillName"), this.skillName);
        container.set(of("SkillArgs"), this.arguments);
        return container;
    }


}
