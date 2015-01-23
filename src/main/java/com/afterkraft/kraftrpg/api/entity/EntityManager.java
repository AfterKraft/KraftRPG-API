/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api.entity;

import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.player.Player;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.storage.PlayerData;

/**
 * A standard entity manager. Centralized manager to maintain links for the proxy objects known as
 * {@link IEntity} and it's sub classes to the original objects represented by the Bukkit API.
 */
public interface EntityManager extends Manager {

    /**
     * Return the {@link IEntity} for the designated Entity. The
     * IEntity is guaranteed to be affected by KraftRPG if it is not returned by null
     *
     * @param entity The bukkit entity to get the proxy object of
     *
     * @return The KraftRPG proxy object for the entity
     * @throws IllegalArgumentException If the entity is null or invalid
     */
    Optional<? extends IEntity> getEntity(Entity entity);

    /**
     * Retrieve the {@link com.afterkraft.kraftrpg.api.entity.Monster} object for this Player.
     *
     * @param entity to get the Monster object of.
     *
     * @return the Monster object
     * @throws IllegalArgumentException If the entity is null or invalid
     */
    Optional<? extends Monster> getMonster(Living entity);

    /**
     * Retrieve the {@link com.afterkraft.kraftrpg.api.entity.Champion} object for this Player.
     *
     * @param player to get the Champion object of.
     *
     * @return the Champion object, or null if the player is not valid
     * @throws IllegalArgumentException If the player is null or invalid
     */
    Optional<? extends Champion> getChampion(Player player);

    /**
     * Attempts to load a {@link com.afterkraft.kraftrpg.api.entity.Champion} with a link to a
     * Player and {@link com.afterkraft.kraftrpg.api.storage.PlayerData} that allows interaction
     * with the rest of KraftRPG.  When needing data regarding a Player who is offline and the
     * data is not intended to be modified, fetching the PlayerData directly
     * from Storage is recommended.
     *
     * @param uuid          of the Player in question
     *
     * @return The loaded Champion belonging to the UUID, if available
     */
    Optional<? extends Champion> getChampion(UUID uuid);

    /**
     * Check if the given {@link Entity} is already managed by this EntityManager.
     * If true, attempting to add a new {@link IEntity} via
     * {@link #addEntity(IEntity)} will fail. This is provided as a utility check for extending the
     * functionality of KraftRPG.
     *
     * @param entity to check
     *
     * @return true if the entity's UUID is already added to the managed entities
     * @throws IllegalArgumentException If the entity is null or invalid
     */
    boolean isEntityManaged(Entity entity);

    /**
     * Create a new Champion instance from the given player and data from the database.
     *
     * @param player Bukkit Player object
     * @param data   PlayerData object
     *
     * @return Constructed Champion
     */
    Champion createChampionWithData(Player player, PlayerData data);

    /**
     * Add the given entity to be managed by KraftRPG. This is to allow custom IEntities to exist in
     * the world that normally would not be considered an {@link com.afterkraft.kraftrpg.api.entity.IEntity}.
     * This can be used to add customized {@link com.afterkraft.kraftrpg.api.entity.Monster}s and
     * possibly {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}s. It will perform checks
     * against the current map of managed {@link Entity} and return false if the
     * entity is already registered. If the entity is registered, killing the entity and spawning a
     * new one in it's place is possible.  It is important that any custom entities are added
     * through this method so that KraftRPG can function as intended.
     *
     * @param entity to be managed by KraftRPG
     *
     * @return true if the entity's {@link java.util.UUID} did not exist and adding the entity was
     * successful.
     * @throws IllegalArgumentException If the entity is not valid
     */
    boolean addEntity(IEntity entity);

    /**
     * Spawns a new LivingEntity of the type given and automatically assigned to the owner.
     *
     * @param owner The owner of the summon
     * @param type  The type of LivingEntity to spawn
     *
     * @return The summon
     * @throws IllegalArgumentException If the owner is null
     * @throws IllegalArgumentException If the type is null
     * @throws IllegalArgumentException If the type is not a living type
     */
    Summon createSummon(SkillCaster owner, EntityType type);

    Set<Summon> getSummons(SkillCaster owner);


    /**
     * Removes the desired champion due to logout or quit. This is only supported to remove tracking
     * of the champion.
     *
     * @param c The champion to remove
     */
    void removeChampion(Champion c);
}
