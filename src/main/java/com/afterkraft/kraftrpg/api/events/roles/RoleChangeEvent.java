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

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.roles.Role;


public class RoleChangeEvent extends RoleEvent implements Cancellable {

    private final Role toRPGRole;
    private final boolean canCancel;
    private boolean cancelled;

    public RoleChangeEvent(Champion player, Role fromRPGClass, Role toRPGRole) {
        this(player, fromRPGClass, toRPGRole, true);
    }

    public RoleChangeEvent(Champion player, Role fromRPGClass, Role toRPGRole, boolean canCancel) {
        super(player, fromRPGClass);
        this.toRPGRole = toRPGRole;
        this.canCancel = canCancel;
        this.cancelled = false;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Role getToRPGRole() {
        return this.toRPGRole;
    }

    public Role getFromRPGRole() {
        return this.getRole();
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled && this.canCancel;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
