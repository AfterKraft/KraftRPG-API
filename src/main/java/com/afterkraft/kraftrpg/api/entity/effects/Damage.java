package com.afterkraft.kraftrpg.api.entity.effects;

/**
 * @author gabizou
 */
public interface Damage extends Periodic {

    public double getTickDamage();

    public void setTickDamage(double tickDamage);

    public boolean isKnockback();

}
