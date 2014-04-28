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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.afterkraft.storage.api.CommonStorage;

import com.afterkraft.kraftrpg.api.RPGPlugin;

/**
 * A generic storage that has implementations specific to KraftStorage API.
 */
public abstract class RPGCommonStorage extends RPGStorage implements CommonStorage {

    private List<UUID> playersNotToSave = new ArrayList<UUID>();

    public RPGCommonStorage(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void addPlayerNotToSave(Player player) {
        if (player == null) {
            return;
        }
        playersNotToSave.add(player.getUniqueId());
    }

    @Override
    public boolean isPlayerNotToSave(Player player) {
        return player != null && playersNotToSave.contains(player.getUniqueId());
    }

    @Override
    public void removePlayerNotToSave(Player player) {
        if (player == null) {
            return;
        }
        playersNotToSave.remove(player.getUniqueId());
    }

    @Override
    public List<UUID> getPlayerNotToSave() {
        return Collections.unmodifiableList(playersNotToSave);
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public void savePlayer(Player player) {
        if (player != null) {
            this.saveChampion(plugin.getEntityManager().getChampion(player), false, true);
        }
    }
}
