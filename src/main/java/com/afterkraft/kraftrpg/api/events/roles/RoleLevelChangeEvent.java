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
package com.afterkraft.kraftrpg.api.events.roles;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.roles.Role;


public class RoleLevelChangeEvent extends RoleEvent {

    private final int fromLevel;
    private final int toLevel;
    private final boolean isMastering;

    public RoleLevelChangeEvent(Insentient being, Role rpgRole, int from, int to) {
        this(being, rpgRole, from, to, false);
    }

    public RoleLevelChangeEvent(Insentient being, Role rpgRole, int from, int to, boolean isMastering) {
        super(being, rpgRole);
        this.fromLevel = from;
        this.toLevel = to;
        this.isMastering = true;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public int getFromLevel() {
        return this.fromLevel;
    }

    public int getToLevel() {
        return this.toLevel;
    }

    public boolean isMastering() {
        return this.isMastering;
    }
}
