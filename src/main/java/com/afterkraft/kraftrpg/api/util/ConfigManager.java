package com.afterkraft.kraftrpg.api.util;

import java.io.File;

/**
 * @author gabizou
 */
public interface ConfigManager {

    /**
     * Check for a specific configuration file. Utilized for SpellConfigManager and more.
     * This will check if the file exists, if it does not, it will be pulled from the defaults
     * provided by the implementation.
     * @param config the file to check
     */
    public void checkForConfig(File config);

}
