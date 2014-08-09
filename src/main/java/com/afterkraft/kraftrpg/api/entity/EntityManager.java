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
package com.afterkraft.kraftrpg.api.entity;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.storage.PlayerData;


public interface EntityManager extends Manager {

    /**
     * Return the {@link com.afterkraft.kraftrpg.api.entity.IEntity} for the
     * designated Entity. The IEntity is guaranteed to be affected by KraftRPG
     * if it is not returned by null
     *
     * @param entity
     * @return
     * @throws IllegalArgumentException If the entity is null or invalid
     */
    public IEntity getEntity(Entity entity);

    /**
     * Retrieve the {@link com.afterkraft.kraftrpg.api.entity.Champion} object
     * for this Player.
     *
     * @param player to get the Champion object of.
     * @return the Champion object, or null if the player is not valid
     * @throws IllegalArgumentException If the player is null or invalid
     */
    public Champion getChampion(Player player);

    /**
     * Retrieve the {@link com.afterkraft.kraftrpg.api.entity.Monster} object
     * for this Player.
     *
     * @param entity to get the Monster object of.
     * @return the Monster object
     * @throws IllegalArgumentException If the entity is null or invalid
     */
    public Monster getMonster(LivingEntity entity);

    /**
     * Check if the given {@link org.bukkit.entity.Entity} is already managed
     * by this EntityManager. If true, attempting to add a new
     * {@link com.afterkraft.kraftrpg.api.entity.IEntity} via
     * {@link #addEntity(IEntity)} will fail. This is provided as a utility
     * check for extending the functionality of KraftRPG.
     *
     * @param entity to check
     * @return true if the entity's UUID is already added to the managed
     * entities
     * @throws IllegalArgumentException If the entity is null or invalid
     */
    public boolean isEntityManaged(Entity entity);

    /**
     * Create a new Champion instance from the given player and data from the
     * database.
     *
     * @param player Bukkit Player object
     * @param data   PlayerData object
     * @return Constructed Champion
     * @throws IllegalArgumentException If the player is null
     * @throws IllegalArgumentException If the data is null
     */
    public Champion createChampion(Player player, PlayerData data);

    /**
     * Add the given entity to be managed by KraftRPG. This is to allow custom
     * IEntities to exist in the world that normally would not be considered
     * an {@link com.afterkraft.kraftrpg.api.entity.IEntity}. This can be used
     * to add customized {@link com.afterkraft.kraftrpg.api.entity.Monster}s
     * and possibly {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}s.
     * It will perform checks against the current map of managed
     * {@link org.bukkit.entity.Entity} and return false if the entity is
     * already registered. If the entity is registered, killing the entity and
     * spawning a new one in it's place is possible.
     * <p/>
     * It is important that any custom entities are added through this method
     * so that KraftRPG can function as intended.
     *
     * @param entity to be managed by KraftRPG
     * @return true if the entity's {@link java.util.UUID} did not exist and
     * adding the entity was successful.
     * @throws IllegalArgumentException If the entity is null
     * @throws IllegalArgumentException If the entity is not valid
     */
    public boolean addEntity(IEntity entity);

    /**
     * Return the linked {@link com.afterkraft.kraftrpg.api.entity.Monster} if
     * the provided UUID is present in our tracked entities mapping.
     *
     * @param uuid of the monster in question
     * @return the linked Monster object
     * @throws IllegalArgumentException if the UUID is null
     */
    public Monster getMonster(UUID uuid);

    /**
     * Attempts to load a {@link com.afterkraft.kraftrpg.api.entity.Champion}
     * with a link to a Player and
     * {@link com.afterkraft.kraftrpg.api.storage.PlayerData} that allows
     * interaction with the rest of KraftRPG.
     * <p/>
     * When needing data regarding a Player who is offline and the data is not
     * intended to be modified, ignoreOffline can be used to retrieve the data
     * for a specific player.
     *
     * @param uuid          of the Player in question
     * @param ignoreOffline whether to load a fake Champion regardless whether
     *                      the linked Player is offline
     * @return the loaded Champion belonging to the UUID, if not null
     * @throws IllegalArgumentException If the uuid is null
     */
    public Champion getChampion(UUID uuid, boolean ignoreOffline);

}
