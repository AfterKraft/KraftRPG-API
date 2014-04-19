package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;

/**
 * Represents a passive spell that can not be activated by command or bind.
 * The recommended use of implementation is {@link com.afterkraft.kraftrpg.api.spells.PassiveSpell}
 */
public interface Passive {

    public void tryApplyingToEntity(IEntity entity);

    public void tryApplyingToMonster(Monster monster);

    public void tryApplyingToPlayer(Champion player);
}
