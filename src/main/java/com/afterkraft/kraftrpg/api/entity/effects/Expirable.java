package com.afterkraft.kraftrpg.api.entity.effects;

public interface Expirable extends Effect {

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
}
