package com.afterkraft.kraftrpg.api.spells;

import java.util.Map;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * Represents a Permissive Spell
 */
public interface Permissible extends ISpell {

    public void setPermissions(Map<String, Boolean> permissions);

    public void tryLearning(Champion player);
}
