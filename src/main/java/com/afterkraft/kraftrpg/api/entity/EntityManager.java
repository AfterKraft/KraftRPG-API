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
     */
    public IEntity getEntity(Entity entity);

    /**
     * Retrieve the {@link com.afterkraft.kraftrpg.api.entity.Champion} object
     * for this Player.
     * 
     * @param player to get the Champion object of.
     * @return the Champion object, or null if the player is not valid
     */
    public Champion getChampion(Player player);

    /**
     * Retrieve the {@link com.afterkraft.kraftrpg.api.entity.Monster} object
     * for this Player.
     * 
     * @param entity to get the Monster object of.
     * @return the Monster object, or null if the entity is not valid
     */
    public Monster getMonster(LivingEntity entity);

    /**
     * @param entity
     * @return
     */
    public boolean isMonsterSetup(LivingEntity entity);

    /**
     * Create a new Champion instance from the given player and data from the
     * database.
     * 
     * @param player Bukkit Player object
     * @param data PlayerData object
     * @return Constructed Champion
     */
    public Champion createChampion(Player player, PlayerData data);

    /**
     * Add the given monster to be managed by KraftRPG. This is so KraftRPG
     * can be notified of new entities being added. This can be used for
     * custom Monsters that are not normally handled by KraftRPG. If the
     * monster's {@link java.util.UUID} is already present in our mapping then
     * the monster will not be added.
     * 
     * @param monster to add
     * @return true if the monster was not already in our mappings.
     */
    public boolean addMonster(Monster monster);

    /**
     * Return the linked {@link com.afterkraft.kraftrpg.api.entity.Monster} if
     * the provided UUID is present in our tracked entities mapping.
     * 
     * @param uuid of the monster in question
     * @return the linked Monster object
     */
    public Monster getMonster(UUID uuid);

    /**
     * Attempts to load a {@link com.afterkraft.kraftrpg.api.entity.Champion}
     * with a link to a Player and
     * {@link com.afterkraft.kraftrpg.api.storage.PlayerData} that allows
     * interaction with the rest of KraftRPG.
     * 
     * When needing data regarding a Player who is offline and the data is not
     * intended to be modified, ignoreOffline can be used to retrieve the data
     * for a specific player.
     * 
     * @param uuid of the Player in question
     * @param ignoreOffline whether to load a fake Champion regardless whether
     *            the linked Player is offline
     * @return the loaded Champion belonging to the UUID, if not null
     */
    public Champion getChampion(UUID uuid, boolean ignoreOffline);

}
