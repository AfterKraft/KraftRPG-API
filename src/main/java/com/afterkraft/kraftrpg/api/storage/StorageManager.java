package com.afterkraft.kraftrpg.api.storage;

import com.afterkraft.kraftrpg.api.Manager;

/**
 * Handles storage implementations
 */
public interface StorageManager extends Manager {

    /**
     * Return the currently configured Storage for RPGPlugin.
     * @return
     */
    public RPGStorage getStorage();
}
