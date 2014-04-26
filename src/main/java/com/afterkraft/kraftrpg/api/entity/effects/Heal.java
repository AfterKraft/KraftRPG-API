package com.afterkraft.kraftrpg.api.entity.effects;

/**
 * @author gabizou
 */
public interface Heal extends Periodic {

    public double getTickHealth();

    public void setTickHealth(double tickHealth);
}
