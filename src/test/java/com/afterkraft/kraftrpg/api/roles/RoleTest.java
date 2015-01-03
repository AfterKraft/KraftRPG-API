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

import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMockRunner;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spongepowered.api.item.ItemTypes;

import com.google.common.collect.ImmutableSet;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.RPGTestCreator;
import com.afterkraft.kraftrpg.api.entity.resource.Resources;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.TestSkill;
import com.afterkraft.kraftrpg.common.persistence.data.MemoryDataContainer;

/**
 * Performs all tests on the Role object directly.
 */
@RunWith(EasyMockRunner.class)
public class RoleTest {
    private RPGPlugin plugin;
    private RPGTestCreator creator;
    private ISkill testSkill;

    @Before
    public void setUp() {
        this.creator = new RPGTestCreator();
        assertTrue(this.creator.setup());
        this.plugin = this.creator.getMockPlugin();
        this.testSkill = this.creator.getMockSkill();
    }

    @After
    public void cleanUp() {
        this.creator.cleanUp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBuilder() {
        Role.builder(null);
    }

    @Test
    public void testWithBuilder() {
        Role.builder(this.plugin);
    }

    @Test
    public void testCleanRoleBuild() {
        Role.builder(this.plugin)
                .setName("TestRole")
                .setType(Role.RoleType.PRIMARY)
                .setDescription("A test role for KraftRPG-API Unit Tests.")
                .setAdvancementLevel(1)
                .setMaxLevel(1)
                .setResourceAtZero(Resources.MANA, 100)
                .setResourceBaseRegeneration(Resources.MANA, 1)
                .setResourcePerLevel(Resources.MANA, 10)
                .setResourceRegenerationPerLevel(Resources.MANA, 1)
                .setResourceAtZero(Resources.HEALTH, 100)
                .setResourcePerLevel(Resources.HEALTH, 10)
                .setChoosable(true)
                .setItemDamage(ItemTypes.DIAMOND_HOE, 10)
                .setItemDamagePerLevel(ItemTypes.DIAMOND_HOE, 10)
                .setItemDamageVaries(ItemTypes.DIAMOND_HOE, true)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullType() {
        Role.builder(this.plugin).setType(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testIncompleteBuilder() {
        Role.builder(this.plugin).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        Role.builder(this.plugin).setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMpPerLevel() {
        Role.builder(this.plugin).setResourcePerLevel(Resources.MANA, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetMpAt0() {
        Role.builder(this.plugin).setResourceAtZero(Resources.MANA, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0() {
        Role.builder(this.plugin).setResourceAtZero(Resources.HEALTH, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0WithMaxNegative() {
        Role.builder(this.plugin).setResourceAtZero(Resources.HEALTH, Integer
                                                            .MIN_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDescription() {
        Role.builder(this.plugin).setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAdvancementLevel() {
        Role.builder(this.plugin).setAdvancementLevel(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroMaxLevel() {
        Role.builder(this.plugin).setMaxLevel(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullItemDamage() {
        Role.builder(this.plugin).setItemDamage(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroItemDamage() {
        Role.builder(this.plugin).setItemDamage(ItemTypes.DIAMOND_HOE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullItemDamagePerLevel() {
        Role.builder(this.plugin).setItemDamagePerLevel(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroItemDamagePerLevel() {
        Role.builder(this.plugin).setItemDamagePerLevel(ItemTypes.DIAMOND_HOE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullChild() {
        Role.builder(this.plugin).addChild(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParent() {
        Role.builder(this.plugin).addParent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRemoveChild() {
        Role.builder(this.plugin).removeChild(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRemoveParent() {
        Role.builder(this.plugin).removeParent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullSkill() {
        Role.builder(this.plugin).addRoleSkill(null, new MemoryDataContainer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSkillNullConfiguration() {
        Role.builder(this.plugin).addRoleSkill(this.testSkill, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullRestrictedSkill() {
        Role.builder(this.plugin).addRestirctedSkill(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullRestrictedSkill() {
        Role.builder(this.plugin).removeRestrictedSkill(null);
    }

    @Test
    public void testValidAddSkill() {
        Role.builder(this.plugin).addRoleSkill(this.testSkill, new MemoryDataContainer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRestrictedAndSkill() {
        Role.builder(this.plugin)
                .addRoleSkill(this.testSkill, new MemoryDataContainer())
                .addRestirctedSkill(this.testSkill);
    }

    @Test
    public void testGetSkill() {
        Role test = Role.builder(this.plugin)
                .setName("TestRole")
                .setType(Role.RoleType.PRIMARY)
                .setDescription("A test role for KraftRPG-API Unit Tests.")
                .setAdvancementLevel(1)
                .setMaxLevel(1)
                .setResourceAtZero(Resources.MANA, 100)
                .setResourceBaseRegeneration(Resources.MANA, 1)
                .setResourcePerLevel(Resources.MANA, 10)
                .setResourceRegenerationPerLevel(Resources.MANA, 1)
                .setResourceAtZero(Resources.HEALTH, 100)
                .setResourcePerLevel(Resources.HEALTH, 10)
                .setChoosable(true)
                .setItemDamage(ItemTypes.DIAMOND_HOE, 10)
                .setItemDamagePerLevel(ItemTypes.DIAMOND_HOE, 10)
                .setItemDamageVaries(ItemTypes.DIAMOND_HOE, true)
                .addRoleSkill(this.testSkill, new MemoryDataContainer())
                .build();
        test.getAllSkills();
        Set<ISkill> skills = ImmutableSet.<ISkill>builder().add(new TestSkill(this.plugin)).build();
        assertThat(test.getAllSkills(), CoreMatchers.is(skills));
    }

}
