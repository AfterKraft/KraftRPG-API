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

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.Manager;


public interface EntityManager extends Manager {

    /**
     * Return the {@link com.afterkraft.kraftrpg.api.entity.IEntity} for the
     * designated LivingEntity
     *
     * @param entity
     * @return
     */
    public IEntity getEntity(LivingEntity entity);

    /**
     * Attempts to match the provided {@link com.afterkraft.kraftrpg.api.entity.SkillCaster}
     * to an existing {@link com.afterkraft.kraftrpg.api.entity.IEntity}. If not
     * found, and the caster is not a LivingEntity, this will return null.
     *
     * @param caster to get the IEntity of
     * @return the IEntity of the caster or null if the caster is not a
     * LivingEntity
     */
    public IEntity getEntity(SkillCaster caster);

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

    public boolean addChampion(Champion champion);

    public boolean addMonster(Monster monster);

    public Monster getMonster(UUID uuid);

    public Champion getChampion(UUID uuid);

}
