/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.storage;

import com.afterkraft.kraftrpg.api.RPGPlugin;

/**
 * This class constructs a StorageFrontend once the StorageBackend is known.
 * Your implementation should look like this:
 * <p/>
 * <code>
 * return new MyCustomStorageFrontend(plugin, backend);
 * </code>
 * <p/>
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
