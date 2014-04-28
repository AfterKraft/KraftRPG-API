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
package com.afterkraft.kraftrpg.api.skills;

import java.util.UUID;

/**
 * This Specific SkillArgument should contain a targetted entity UUID for the
 * purpose of binding and storage as a Player name may be transient between the
 * targetted Player logging in and out.
 */
public abstract class TargetedSkillArgument extends SkillArgument {

    /**
     * This is known to be the last known username of the targetted entity
     */
    private String entityName;

    /**
     * This is the uuid for the targetted entity
     */
    private UUID entityID;

    protected TargetedSkillArgument(String[] args) {
        super(args);
    }

    /**
     * Return the last known name of the targetted {@link
     * org.bukkit.entity.LivingEntity}
     *
     * @return the last known name of the targetted entity
     */
    public String getTargetName() {
        return this.entityName;
    }

    /**
     * Set the targetted entity's username
     *
     * @param name
     */
    protected void setTargetName(String name) {
        this.entityName = name;
    }

    /**
     * Return the stored UUID of the targetted Player, this is safe to retrieve
     * the OfflinePlayer or Player
     *
     * @return the UUID of the targetted Player
     */
    public UUID getTargetUniqueID() {
        return this.entityID;
    }

    /**
     * Set the targetted entity's UUID
     *
     * @param uuid
     */
    protected void setTargetUniqueID(UUID uuid) {
        this.entityID = uuid;
    }

}