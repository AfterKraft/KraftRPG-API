package com.afterkraft.kraftrpg.api.spells;

import java.util.Map;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * @author gabizou
 */
public interface Permissible {

    public void setPermissions(Map<String, Boolean> permissions);

    public void tryLearning(Champion player);
}
