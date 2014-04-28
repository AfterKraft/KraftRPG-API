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

import org.bukkit.entity.Player;


public interface Champion extends IEntity, SkillCaster {

    /**
     * Return the Bukkit {@link Player} object if it is still valid otherwise
     * null
     *
     * @return the bukkit Player object if not null
     */
    public Player getPlayer();

    /**
     * Set the Bukkit {@link Player} object for this Champion. This should
     * automatically call {@link #setEntity(org.bukkit.entity.LivingEntity)} as
     * long as the original UUID matches the new Player's UUID.
     *
     * @param player the Bukkit Player for this Champion to attach to
     */
    public void setPlayer(Player player);

}
