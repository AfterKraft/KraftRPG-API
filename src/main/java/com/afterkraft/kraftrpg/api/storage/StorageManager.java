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

import com.afterkraft.kraftrpg.api.Manager;

/**
 * Handles storage implementations
 */
public interface StorageManager extends Manager {
    /**
     * Register a new type of StorageBackend.
     *
     * @param storage the StorageBackend to register
     * @param identifiers Names that this storage responds to in configuration files
     */
    public void registerBackend(StorageBackend backend, String... identifiers);

    /**
     * Change from the default StorageFrontend to the specified one.
     *
     * @param frontend
     */
    public void changeFrontend(StorageFrontend frontend);

    /**
     * Return the currently configured {@link com.afterkraft.kraftrpg.api.storage.StorageFrontend}
     * for RPGPlugin.
     *
     * @return the currently set RPGStorage
     */
    public StorageFrontend getStorage();
}
