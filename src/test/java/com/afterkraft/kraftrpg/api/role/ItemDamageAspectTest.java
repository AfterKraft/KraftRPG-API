/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.role;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.role.aspect.ItemDamageAspect;
import com.afterkraft.kraftrpg.api.role.aspect.ItemDamageAspect.ItemDamageAspectBuilder;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.afterkraft.kraftrpg.api.util.TestItemType;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RpgCommon.class})
public class ItemDamageAspectTest {

    private RpgPlugin plugin;
    private Skill testSkill;

    @BeforeClass
    public static void setUp() {
        if (!RpgCommon.getServiceManager().isTesting()) {
            RpgCommon.getServiceManager().setTesting(new CoreServiceProvider());
        }
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
