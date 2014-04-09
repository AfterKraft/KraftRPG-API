package com.afterkraft.kraftrpg.api.entity.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.RPGMonster;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;
import com.afterkraft.kraftrpg.api.spells.Spell;

/**
 * @author gabizou
 */
public class RPGExpirableEffect extends RPGEffect implements Expirable {

    private final long duration;
    private long expireTime;

    public RPGExpirableEffect(Spell skill, String name, long duration) {
        super(skill, name);
        this.duration = duration;
    }

    public RPGExpirableEffect(Spell skill, RPGPlugin plugin, String name, long duration) {
        super(plugin, skill, name);
        this.duration = duration;
    }

    @Override
    public void applyToMonster(RPGMonster monster) {
        super.applyToMonster(monster);
        this.expireTime = this.applyTime + this.duration;
    }

    @Override
    public void applyToPlayer(RPGPlayer hero) {
        super.applyToPlayer(hero);
        this.expireTime = this.applyTime + this.duration;
    }

    @Override
    public long getApplyTime() {
        return this.applyTime;
    }

    @Override
    public long getDuration() {
        return this.duration;
    }

    @Override
    public long getExpiry() {
        return this.expireTime;
    }

    @Override
    public long getRemainingTime() {
        return this.expireTime - System.currentTimeMillis();
    }

    @Override
    public boolean isExpired() {
        return !this.isPersistent() && System.currentTimeMillis() >= this.getExpiry();
    }

    @Override
    public void expire() {
        this.expireTime = System.currentTimeMillis();
    }

}
