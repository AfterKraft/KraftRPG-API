package com.afterkraft.kraftrpg.api.skills;

import java.util.Collection;

import org.junit.BeforeClass;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.RPGTestPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

public class TestActiveSkill extends ActiveSkill {

    private static RPGPlugin plugin;

    public TestActiveSkill() {
        super(plugin, "TestActiveSkill");
    }

    @BeforeClass
    public static void setup() {
        plugin = RPGTestPlugin.getInstance();
    }

    @Override
    public SkillCastResult useSkill(SkillCaster caster) {
        return SkillCastResult.NORMAL;
    }

    @Override
    public Collection<SkillSetting> getUsedConfigNodes() {
        return null;
    }
}
