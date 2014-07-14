package com.afterkraft.kraftrpg.api.skills;

import java.util.ArrayList;
import java.util.Collection;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;

public class TestSkill extends ActiveSkill {

    public TestSkill(RPGPlugin plugin) {
        super(plugin, "TestSkill");
    }

    @Override
    public SkillCastResult useSkill(SkillCaster caster) {
        return SkillCastResult.NORMAL;
    }

    @Override
    public Collection<SkillSetting> getUsedConfigNodes() {
        return new ArrayList<SkillSetting>();
    }
}
