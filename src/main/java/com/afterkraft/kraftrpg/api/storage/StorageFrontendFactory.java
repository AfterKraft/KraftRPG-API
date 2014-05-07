package com.afterkraft.kraftrpg.api.storage;

import com.afterkraft.kraftrpg.api.RPGPlugin;

/**
 * This class constructs a StorageFrontend once the StorageBackend is known.
 * Your implementation should look like this:
 *
 * <code>
 * return new MyCustomStorageFrontend(plugin, backend);
 * </code>
 *
 * Factories replace the use of reflection.
 */
public abstract class StorageFrontendFactory {

    public abstract StorageFrontend construct(RPGPlugin plugin, StorageBackend backend);

    public static class DefaultFactory extends StorageFrontendFactory {
        @Override
        public StorageFrontend construct(RPGPlugin plugin, StorageBackend backend) {
            return new StorageFrontend(plugin, backend) {
            };
        }
    }
}
