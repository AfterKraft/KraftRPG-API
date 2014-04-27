package com.afterkraft.kraftrpg.api.entity.effects;

/**
 * @author gabizou
 */
public interface Damage extends Periodic {

    /**
     * Return the damage per tick for this effect.
     *
     * @return damage per tick
     */
    public double getTickDamage();

    /**
     * Set the damage per tick for this effect.
     *
     * @param tickDamage for this effect.
     */
    public void setTickDamage(double tickDamage);

    /**
     * Return whether this effect knocks back the affected
     *
     * @return true if this effect will knock back on damage
     */
    public boolean isKnockback();

}
