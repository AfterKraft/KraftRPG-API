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

import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.notNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.spongepowered.api.data.DataContainer;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.role.aspect.HealthAspect;
import com.afterkraft.kraftrpg.api.role.aspect.HealthAspect.HealthAspectBuilder;
import com.afterkraft.kraftrpg.api.role.aspect.ManaAspect;
import com.afterkraft.kraftrpg.api.role.aspect.ManaAspect.ManaAspectBuilder;
import com.afterkraft.kraftrpg.api.role.aspect.RestrictedSkillAspect;
import com.afterkraft.kraftrpg.api.role.aspect.RestrictedSkillAspect.RestrictedSkillAspectBuilder;
import com.afterkraft.kraftrpg.api.role.aspect.SkillAspect;
import com.afterkraft.kraftrpg.api.role.aspect.SkillAspect.SkillAspectBuilder;
import com.afterkraft.kraftrpg.api.skill.Skill;

/**
 * Performs all tests on the Role object directly.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RpgCommon.class})
public class RoleTest {
    private RpgPlugin plugin;
    private Skill testSkill;
    private DataContainer mockContainer;

    @BeforeClass
    public static void setUp() {
        if (!RpgCommon.getServiceManager().isTesting()) {
            RpgCommon.getServiceManager().setTesting(new CoreServiceProvider());
        }
    }

    @Test
    public void testWithBuilder() {
        Role.builder();
    }

    @Test
    public void testCleanRoleBuild() {
        Role.builder()
                .setName("TestRole")
                .setType(Role.RoleType.PRIMARY)
                .setDescription("A test role for RpgCommon-API Unit Tests.")
                .setAdvancementLevel(1)
                .setMaxLevel(1)
                .setChoosable(true)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullType() {
        Role.builder().setType(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testIncompleteBuilder() {
        Role.builder().build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullName() {
        Role.builder().setName(null);
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
        Role.builder().addAspect(builder.build());
    }

    @Test(expected = NullPointerException.class)
    public void testNullDescription() {
        Role.builder().setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAdvancementLevel() {
        Role.builder().setAdvancementLevel(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroMaxLevel() {
        Role.builder().setMaxLevel(0);
    }

    @Test(expected = NullPointerException.class)
    public void testNullChild() {
        Role.builder().addChild(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullParent() {
        Role.builder().addParent(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullRemoveChild() {
        Role.builder().removeChild(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullRemoveParent() {
        Role.builder().removeParent(null);
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
        Role.builder()
                .addAspect(builder.build())
                .addAspect(restrictedBuilder.build()) // Should fail HERE
                .build();
    }

    @Test
    public void testGetSkill() {
        SkillAspectBuilder builder = SkillAspect.builder();
        builder.addRoleSkill(this.testSkill, this.mockContainer);
        Role test = Role.builder()
                .setName("TestRole")
                .setType(Role.RoleType.PRIMARY)
                .setDescription("A test role for RpgCommon-API Unit Tests.")
                .setAdvancementLevel(1)
                .setMaxLevel(1)
                .addAspect(builder.build())
                .build();
        Optional<SkillAspect> aspectOptional = test.getAspect(SkillAspect.class);
        assertTrue(aspectOptional.isPresent());
        List<Skill> skills = ImmutableList.<Skill>builder().add(this.testSkill)
                .build();
        assertThat(aspectOptional.get().getAllSkills(), CoreMatchers.is(skills));
    }

}
