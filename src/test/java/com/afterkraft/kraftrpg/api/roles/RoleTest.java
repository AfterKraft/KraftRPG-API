package com.afterkraft.kraftrpg.api.roles;

import org.junit.BeforeClass;
import org.junit.Test;

import org.bukkit.Material;
import org.bukkit.configuration.MemoryConfiguration;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.RPGTestPlugin;
import com.afterkraft.kraftrpg.api.skills.TestActiveSkill;

public class RoleTest {
    private static RPGPlugin plugin;

    @BeforeClass
    public static void setUp() {
        plugin = RPGTestPlugin.getInstance();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBuilder() {
        Role.builder(null);
    }

    @Test
    public void testWithBuilder() {
        Role.builder(plugin);
    }

    @Test
    public void testCleanRoleBuild() {
        Role.builder(plugin)
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
        Role.builder(plugin).setType(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testIncompleteBuilder() {
        Role.builder(plugin).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        Role.builder(plugin).setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullManaName() {
        Role.builder(plugin).setManaName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyManaName() {
        Role.builder(plugin).setManaName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMpPerLevel() {
        Role.builder(plugin).setMpPerLevel(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetMpAt0() {
        Role.builder(plugin).setMpAt0(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0() {
        Role.builder(plugin).setHpAt0(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHpAt0WithMaxNegative() {
        Role.builder(plugin).setHpAt0(Integer.MIN_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDescription() {
        Role.builder(plugin).setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAdvancementLevel() {
        Role.builder(plugin).setAdvancementLevel(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroMaxLevel() {
        Role.builder(plugin).setMaxLevel(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullItemDamage() {
        Role.builder(plugin).setItemDamage(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroItemDamage() {
        Role.builder(plugin).setItemDamage(Material.DIAMOND_HOE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullItemDamagePerLevel() {
        Role.builder(plugin).setItemDamagePerLevel(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroItemDamagePerLevel() {
        Role.builder(plugin).setItemDamagePerLevel(Material.DIAMOND_HOE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullChild() {
        Role.builder(plugin).addChild(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParent() {
        Role.builder(plugin).addParent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRemoveChild() {
        Role.builder(plugin).removeChild(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRemoveParent() {
        Role.builder(plugin).removeParent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullSkill() {
        Role.builder(plugin).addRoleSkill(null, new MemoryConfiguration());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSkillNullConfiguration() {
        Role.builder(plugin).addRoleSkill(new TestActiveSkill(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullRestrictedSkill() {
        Role.builder(plugin).addRestirctedSkill(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullRestrictedSkill() {
        Role.builder(plugin).removeRestrictedSkill(null);
    }

    @Test
    public void testValidAddSkill() {
        Role.builder(plugin).addRoleSkill(new TestActiveSkill(), new MemoryConfiguration());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRestrictedAndSkill() {
        Role.builder(plugin)
                .addRoleSkill(new TestActiveSkill(), new MemoryConfiguration())
                .addRestirctedSkill(new TestActiveSkill());
    }

}