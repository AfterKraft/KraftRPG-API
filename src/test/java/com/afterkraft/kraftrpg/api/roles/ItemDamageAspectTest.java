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

package com.afterkraft.kraftrpg.api.roles;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.spongepowered.api.text.message.Messages;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.RPGTestCreator;
import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.roles.aspects.ItemDamageAspect;
import com.afterkraft.kraftrpg.api.roles.aspects.ItemDamageAspect.ItemDamageAspectBuilder;
import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.api.util.TestItemType;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RpgCommon.class, Messages.class})
public class ItemDamageAspectTest {

    private RPGPlugin plugin;
    private RPGTestCreator creator;
    private Skill testSkill;

    @Before
    public void setUp() {
        this.creator = new RPGTestCreator();
        assertTrue(this.creator.setup());
        this.plugin = this.creator.getMockPlugin();
        this.testSkill = this.creator.getMockSkill();
    }

    @Test(expected = NullPointerException.class)
    public void testNullItemDamage() {
        ItemDamageAspectBuilder builder = ItemDamageAspect.builder();
        builder.setBaseDamage(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroItemDamage() {
        ItemDamageAspectBuilder builder = ItemDamageAspect.builder();
        builder.setBaseDamage(new TestItemType("TestItem"), 0);
    }

    @Test(expected = NullPointerException.class)
    public void testNullItemDamagePerLevel() {
        ItemDamageAspectBuilder builder = ItemDamageAspect.builder();
        builder.setDamageIncrease(null, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidItemPerLevel() {
        ItemDamageAspectBuilder builder = ItemDamageAspect.builder();
        builder.setDamageIncrease(new TestItemType("TestItem"), 0);
    }
}
