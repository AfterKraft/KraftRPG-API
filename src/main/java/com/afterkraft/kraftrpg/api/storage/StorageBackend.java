package com.afterkraft.kraftrpg.api.storage;

import java.util.List;
import java.util.UUID;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * A backend for storage of player data. All methods in this class may be
 * assumed to take a long time, and should be called on threads.
 */
public interface StorageBackend {

    public boolean removePlayer(UUID uuid);

    public boolean savePlayer(Champion data);

    public Champion loadPlayer(UUID uuid);

    public List<UUID> getAllStoredUsers();
}
