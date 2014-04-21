package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Monster;

/**
 * Represents a passive spell that can not be activated by command or bind.
 * The recommended use of implementation is {@link com.afterkraft.kraftrpg.api.spells.PassiveSpell}
 */
public interface Passive extends ISpell {

    /**
     * Attempts to apply this passive spell to the given {@link com.afterkraft.kraftrpg.api.entity.IEntity}
     * @param entity to attempt to apply this passive spell to
     */
    public void tryApplyingToEntity(IEntity entity);

    /**
     * Attempt to apply this passive spell to the given {@link com.afterkraft.kraftrpg.api.entity.Monster}
     * @param monster to attempt to apply this passive spell to
     */
    public void tryApplyingToMonster(Monster monster);

    /**
     * Attempt to apply this passive spell to the given {@link com.afterkraft.kraftrpg.api.entity.Champion}
     * @param champion to attempt to apply this passive spell to
     */
    public void tryApplyingToPlayer(Champion champion);

}
