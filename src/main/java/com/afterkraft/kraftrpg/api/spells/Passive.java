package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;

/**
 * @author gabizou
 */
public interface Passive {

    public void tryApplyingToEntity(IEntity entity);

    public void tryApplyingToMonster(Monster monster);

    public void tryApplyingToPlayer(Champion player);
}
