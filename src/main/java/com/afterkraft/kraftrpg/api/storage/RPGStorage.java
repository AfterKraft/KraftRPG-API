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

import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * Represents a storage implementation to save various things such as Champions
 * and more. This can be implemented by third parties to provide support for
 * other Storage APIs, though loaded dynamically like {@link
 * com.afterkraft.kraftrpg.api.skills.ISkill}s.
 * <p/>
 * RPGStorages should maintain an active cache system so as to reduce the need
 * to save all data Champion data at once.
 */
public abstract class RPGStorage {

    protected final RPGPlugin plugin;
    private final String name;

    public RPGStorage(final RPGPlugin plugin, final String name) {
        this.plugin = plugin;
        this.name = name;

    }

    public String getName() {
        return this.name;
    }

    /**
     * Load the Champion data if there is any. This should check with the
     * database whether the given Player does indeed have information available,
     * if not, this will return null.
     *
     * @param player the requested Player data
     * @return the loaded Champion instance if data exists, else returns null
     */
    public abstract Champion loadChampion(final Player player);

    /**
     * Saves the given {@link com.afterkraft.kraftrpg.api.entity.Champion} data.
     * If saveAll is true, all of the given Champion's data will be saved. If
     * not, only the necessary differences will be saved. This is important to
     * know when changing a Champion's active {@link com.afterkraft.kraftrpg.api.entity.roles.Role}s.
     *
     * @param champion the provided Champion that we should save data for
     * @param saveAll whether to save all available Champion data for the
     * provided Champion
     * @param now whether to save the Champion data on method call or place the
     * Champion data into queue for later saving
     * @return true if the save was successful
     */
    public abstract boolean saveChampion(final Champion champion, boolean saveAll, boolean now);

}
