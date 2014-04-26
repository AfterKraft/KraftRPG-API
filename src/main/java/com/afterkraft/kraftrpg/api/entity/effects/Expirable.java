package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.Champion;

public interface Expirable extends IEffect {

    public String getApplyText();

    public void setApplyText(String text);

    public String getExpireText();

    public void setExpireText(String text);

    /**
     * Fetch the duration of this Expirable Effect.
     * @return the duration in milliseconds
     */
    public long getDuration();

    /**
     * Fetch the estimated Expire time in milliseconds
     * @return the expire time in milliseconds.
     */
    public long getExpiry();

    /**
     * Fetch the estimated remaining time of this Effect
     * @return the estimated remaining time in milliseconds
     */
    public long getRemainingTime();

    /**
     * Check if this Effect is expired
     * @return true if this Effect is expired
     */
    public boolean isExpired();

    /**
     * Manually expire this Effect. This will set the expire
     * time to the current System time.
     */
    public void expire();

    /**
     * Get the {@link com.afterkraft.kraftrpg.api.entity.Champion} applying this expirable effect.
     * @return
     */
    public Champion getApplier();

    public void setApplier(Champion player);
}
