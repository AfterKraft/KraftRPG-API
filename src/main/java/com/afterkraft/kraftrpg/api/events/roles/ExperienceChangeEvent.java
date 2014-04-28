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

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;


public class ExperienceChangeEvent extends RoleEvent implements Cancellable {

    private final FixedPoint original;
    private FixedPoint change;
    private boolean cancelled = false;

    public ExperienceChangeEvent(Insentient insentient, Role role, FixedPoint original, FixedPoint change) {
        super(insentient, role);
        this.original = original;
        this.change = change;
    }

    public FixedPoint getFromExperience() {
        return this.original;
    }

    public FixedPoint getChange() {
        return this.change;
    }

    public void setChange(FixedPoint experience) {
        this.change = experience;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
