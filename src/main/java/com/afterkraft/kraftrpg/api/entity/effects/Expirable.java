package com.afterkraft.kraftrpg.api.entity.effects;

public interface Expirable {

    public long getDuration();

    public long getExpiry();

    public long getRemainingTime();

    public boolean isExpired();
    
    public void expire();
}
