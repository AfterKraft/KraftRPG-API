package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.RPGEntity;
import com.afterkraft.kraftrpg.api.entity.RPGMonster;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;
import com.afterkraft.kraftrpg.api.spells.Spell;

/**
 * Author: gabizou
 */
public abstract class RPGPeriodicEffect extends RPGEffect implements Periodic {

    private final long period;
    protected long lastTickTime = 0;

    public RPGPeriodicEffect(Spell spell, String name, long period) {
        super(spell, name);
        this.period = period;
    }

    public RPGPeriodicEffect(RPGPlugin plugin, String name, long period) {
        super(plugin, null, name);
        this.period = period;
    }

    @Override
    public long getLastTickTime() {
        return this.lastTickTime;
    }

    @Override
    public long getPeriod() {
        return this.period;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void tick(RPGEntity entity) {
        this.lastTickTime = System.currentTimeMillis();
        if (entity instanceof RPGPlayer) {
            this.tickPlayer((RPGPlayer) entity);
        } else if (entity instanceof RPGMonster) {
            this.tickMonster((RPGMonster) entity);
        }
    }

    @Override
    public void tickMonster(RPGMonster monster) { }

    @Override
    public void tickPlayer(RPGPlayer player) { }
}
