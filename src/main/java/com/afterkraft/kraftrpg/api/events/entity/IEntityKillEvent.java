package com.afterkraft.kraftrpg.api.events.entity;

import com.afterkraft.kraftrpg.api.entity.IEntity;

/**
 * @author gabizou
 */
public class IEntityKillEvent extends IEntityEvent {

    private final IEntity victim;

    public IEntityKillEvent(IEntity attacker, IEntity victim) {
        super(attacker);
        this.victim = victim;
    }

    public IEntity getKiller() {
        return this.getEntity();
    }

    public IEntity getVictim() {
        return this.victim;
    }


}
