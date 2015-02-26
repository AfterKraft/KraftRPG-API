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

import java.util.List;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.notNull;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.spongepowered.api.service.persistence.data.DataContainer;
import org.spongepowered.api.text.message.Messages;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.RPGTestCreator;
import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.roles.aspects.HealthAspect;
import com.afterkraft.kraftrpg.api.roles.aspects.HealthAspect.HealthAspectBuilder;
import com.afterkraft.kraftrpg.api.roles.aspects.ManaAspect;
import com.afterkraft.kraftrpg.api.roles.aspects.ManaAspect.ManaAspectBuilder;
import com.afterkraft.kraftrpg.api.roles.aspects.RestrictedSkillAspect;
import com.afterkraft.kraftrpg.api.roles.aspects.RestrictedSkillAspect.RestrictedSkillAspectBuilder;
import com.afterkraft.kraftrpg.api.roles.aspects.SkillAspect;
import com.afterkraft.kraftrpg.api.roles.aspects.SkillAspect.SkillAspectBuilder;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillSetting;
import com.afterkraft.kraftrpg.api.util.DataUtil;

/**
 * Performs all tests on the Role object directly.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RpgCommon.class, DataUtil.class, Messages.class})
public class RoleTest {
    private RPGPlugin plugin;
    private RPGTestCreator creator;
    private ISkill testSkill;
    private DataContainer mockContainer;

    @Before
    public void setUp() {
        this.creator = new RPGTestCreator();
        assertTrue(this.creator.setup());
        this.plugin = this.creator.getMockPlugin();
        this.testSkill = this.creator.getMockSkill();

        this.mockContainer = createMock(DataContainer.class);
        expect(this.mockContainer.getInt(SkillSetting.LEVEL.node())).andReturn(Optional.of(1)).times(1);
        replay(this.mockContainer);
        mockStatic(DataUtil.class);
        expect(DataUtil.containerFromExisting(notNull(DataContainer.class)))
                .andStubReturn(Optional.of(this.mockContainer));
        PowerMock.replay(DataUtil.class);
    }

    @After
    public void cleanUp() {
        this.creator.cleanUp();
    }

    @Test(expected = NullPointerException.class)
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
                .setChoosable(true)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullType() {
        Role.builder(this.plugin).setType(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testIncompleteBuilder() {
        Role.builder(this.plugin).build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullName() {
        Role.builder(this.plugin).setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMpPerLevel() {
        ManaAspectBuilder builder = ManaAspect.builder();
        builder.manaPerLevel(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetMpAt0() {
        ManaAspectBuilder builder = ManaAspect.builder();
        builder.baseMana(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0() {
        HealthAspectBuilder builder = HealthAspect.builder();
        builder.setHealthAtZero(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0WithMaxNegative() {
        HealthAspectBuilder builder = HealthAspect.builder();
        builder.setHealthAtZero(0).setHealthPerLevel(-1);
        Role.builder(this.plugin).addAspect(builder.build());
    }

    @Test(expected = NullPointerException.class)
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

    @Test(expected = NullPointerException.class)
    public void testNullChild() {
        Role.builder(this.plugin).addChild(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullParent() {
        Role.builder(this.plugin).addParent(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullRemoveChild() {
        Role.builder(this.plugin).removeChild(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullRemoveParent() {
        Role.builder(this.plugin).removeParent(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullSkill() {
        SkillAspectBuilder builder = SkillAspect.builder();
        builder.addRoleSkill(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddSkillNullConfiguration() {
        SkillAspectBuilder builder = SkillAspect.builder();
        builder.addRoleSkill(this.testSkill, null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullRestrictedSkill() {
        RestrictedSkillAspectBuilder builder = RestrictedSkillAspect.builder();
        builder.addRestirctedSkill(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveNullRestrictedSkill() {
        RestrictedSkillAspectBuilder builder = RestrictedSkillAspect.builder();
        builder.removeRestrictedSkill(null);
    }

    @Test
    public void testValidAddSkill() {
        SkillAspectBuilder builder = SkillAspect.builder();
        builder.addRoleSkill(this.testSkill, this.mockContainer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRestrictedAndSkill() {
        SkillAspectBuilder builder = SkillAspect.builder();

        builder.addRoleSkill(this.testSkill, this.mockContainer);
        RestrictedSkillAspectBuilder restrictedBuilder = RestrictedSkillAspect.builder();
        restrictedBuilder.addRestirctedSkill(this.testSkill);
        Role.builder(this.plugin)
                .addAspect(builder.build())
                .addAspect(restrictedBuilder.build()) // Should fail HERE
                .build();
    }

    @Test
    public void testGetSkill() {
        SkillAspectBuilder builder = SkillAspect.builder();
        builder.addRoleSkill(this.testSkill, this.mockContainer);
        Role test = Role.builder(this.plugin)
                .setName("TestRole")
                .setType(Role.RoleType.PRIMARY)
                .setDescription("A test role for KraftRPG-API Unit Tests.")
                .setAdvancementLevel(1)
                .setMaxLevel(1)
                .addAspect(builder.build())
                .build();
        Optional<SkillAspect> aspectOptional = test.getAspect(SkillAspect.class);
        assertTrue(aspectOptional.isPresent());
        List<ISkill> skills = ImmutableList.<ISkill>builder().add(this.testSkill)
                .build();
        assertThat(aspectOptional.get().getAllSkills(), CoreMatchers.is(skills));
    }

}
