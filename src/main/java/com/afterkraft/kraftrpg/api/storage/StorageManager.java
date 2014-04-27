package com.afterkraft.kraftrpg.api.storage;

import com.afterkraft.kraftrpg.api.Manager;

/**
 * Handles storage implementations
 */
public interface StorageManager extends Manager {


    /**
     * Set the current storage. WARNING: DOING THIS AFTER ALL DATA IS LOADED MAY
     * LEAD TO DATA LOSS.
     *
     * @param storage the RPGStorage to set as active storage to
     * @return true if successful in loading the RPGStorage
     */
    public boolean setStorage(RPGStorage storage);

    /**
     * Return the currently configured {@link com.afterkraft.kraftrpg.api.storage.RPGStorage}
     * for RPGPlugin.
     *
     * @return the currently set RPGStorage
     */
    public RPGStorage getStorage();
}
