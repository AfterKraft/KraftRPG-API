package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.RPGEntity;
import com.afterkraft.kraftrpg.api.entity.RPGMonster;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;
import com.afterkraft.kraftrpg.api.spells.Spell;

/**
 * @author gabizou
 */
public abstract class RPGPeriodicExpirableEffect extends RPGExpirableEffect implements Periodic {

    private final long period;
    protected long lastTickTime = 0;

    public RPGPeriodicExpirableEffect(Spell skill, String name, long period, long duration) {
        super(skill, name, duration);
        this.period = period;
    }

    public RPGPeriodicExpirableEffect(Spell skill, RPGPlugin plugin, String name, long period, long duration) {
        super(skill, plugin, name, duration);
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
        return System.currentTimeMillis() >= (this.lastTickTime + this.period);
    }

    @Override
    public void tick(RPGEntity character) {
        this.lastTickTime = System.currentTimeMillis();
        if (character instanceof RPGPlayer) {
            this.tickPlayer((RPGPlayer) character);
        } else if (character instanceof RPGMonster) {
            this.tickMonster((RPGMonster) character);
        }
    }
}
