package com.afterkraft.kraftrpg.api.roles;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.bukkit.Material;
import org.bukkit.configuration.MemoryConfiguration;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.RPGTestCreator;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.TestSkill;

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
                .setMpAt0(100)
                .setMpRegenAt0(1)
                .setMpPerLevel(10)
                .setMpRegenPerLevel(1)
                .setHpAt0(100)
                .setHpPerLevel(10)
                .setChoosable(true)
                .setItemDamage(Material.DIAMOND_HOE, 10)
                .setItemDamagePerLevel(Material.DIAMOND_HOE, 10)
                .setItemDamageVaries(Material.DIAMOND_HOE, true)
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
    public void testNullManaName() {
        Role.builder(this.plugin).setManaName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyManaName() {
        Role.builder(this.plugin).setManaName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMpPerLevel() {
        Role.builder(this.plugin).setMpPerLevel(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetMpAt0() {
        Role.builder(this.plugin).setMpAt0(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0() {
        Role.builder(this.plugin).setHpAt0(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0WithMaxNegative() {
        Role.builder(this.plugin).setHpAt0(Integer.MIN_VALUE);
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
        Role.builder(this.plugin).setItemDamage(Material.DIAMOND_HOE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullItemDamagePerLevel() {
        Role.builder(this.plugin).setItemDamagePerLevel(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroItemDamagePerLevel() {
        Role.builder(this.plugin).setItemDamagePerLevel(Material.DIAMOND_HOE, 0);
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
        Role.builder(this.plugin).addRoleSkill(null, new MemoryConfiguration());
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
        Role.builder(this.plugin).addRoleSkill(this.testSkill, new MemoryConfiguration());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRestrictedAndSkill() {
        Role.builder(this.plugin)
                .addRoleSkill(this.testSkill, new MemoryConfiguration())
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
                .setMpAt0(100)
                .setMpRegenAt0(1)
                .setMpPerLevel(10)
                .setMpRegenPerLevel(1)
                .setHpAt0(100)
                .setHpPerLevel(10)
                .setChoosable(true)
                .setItemDamage(Material.DIAMOND_HOE, 10)
                .setItemDamagePerLevel(Material.DIAMOND_HOE, 10)
                .setItemDamageVaries(Material.DIAMOND_HOE, true)
                .addRoleSkill(this.testSkill, new MemoryConfiguration())
                .build();
        test.getAllSkills();
        Set<ISkill> skills = ImmutableSet.<ISkill>builder().add(new TestSkill(this.plugin)).build();
        assertThat(test.getAllSkills(), CoreMatchers.is(skills));
    }

}
