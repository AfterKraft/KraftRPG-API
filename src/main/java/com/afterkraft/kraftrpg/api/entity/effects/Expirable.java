package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.SpellCaster;

public interface Expirable extends IEffect {

    /**
     * Get the message that should be sent to players when this effect is
     * applied
     *
     * @return the message when this effect is applied
     */
    public String getApplyText();

    /**
     * Set the message that is sent to players when this effect is applied
     *
     * @param text that will show up when this effect is applied
     */
    public void setApplyText(String text);

    /**
     * Get the message that should be sent to players when this effect expires
     *
     * @return the message when this effect expires
     */
    public String getExpireText();

    /**
     * Set the message that is sent to players when this effect expires
     *
     * @param text that will show up when this effect expires
     */
    public void setExpireText(String text);

    /**
     * Fetch the duration of this Expirable Effect.
     *
     * @return the duration in milliseconds
     */
    public long getDuration();

    /**
     * Fetch the estimated Expire time in milliseconds
     *
     * @return the expire time in milliseconds.
     */
    public long getExpiry();

    /**
     * Fetch the estimated remaining time of this Effect
     *
     * @return the estimated remaining time in milliseconds
     */
    public long getRemainingTime();

    /**
     * Check if this Effect is expired
     *
     * @return true if this Effect is expired
     */
    public boolean isExpired();

    /**
     * Manually expire this Effect. This will set the expire time to the current
     * System time.
     */
    public void expire();

    /**
     * Get the {@link com.afterkraft.kraftrpg.api.entity.SpellCaster} applying
     * this expirable effect.
     *
     * @return the SpellCaster applying this effect
     */
    public SpellCaster getApplier();

    public void setApplier(SpellCaster caster);
}
