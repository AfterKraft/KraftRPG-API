package com.afterkraft.kraftrpg.api.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.afterkraft.storage.api.CommonStorage;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;

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
