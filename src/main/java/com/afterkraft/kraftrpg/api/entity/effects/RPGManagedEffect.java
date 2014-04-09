package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.RPGEntity;

/**
 * @author gabizou
 */
public class RPGManagedEffect {

    private final RPGEffect effect;
    private final RPGEntity entity;

    public RPGManagedEffect(RPGEntity entity, RPGEffect effect) {
        this.effect = effect;
        this.entity = entity;
    }

    public RPGEffect getEffect() {
        return this.effect;
    }

    public RPGEntity getEntity() {
        return this.entity;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 7;
        result = (prime * result) + this.effect.hashCode();
        result = (prime * result) + this.entity.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        }
        final RPGManagedEffect other = (RPGManagedEffect) obj;
        if (!this.effect.equals(other.effect)) {
            return false;
        } else if (!this.entity.equals(other.entity)) {
            return false;
        }
        return true;
    }
}
