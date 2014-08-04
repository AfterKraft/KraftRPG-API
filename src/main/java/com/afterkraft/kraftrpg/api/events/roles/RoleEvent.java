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

import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.events.entity.InsentientEvent;
import com.afterkraft.kraftrpg.api.roles.Role;


public abstract class RoleEvent extends InsentientEvent {
    private final Role rpgRole;

    protected RoleEvent(Sentient being, Role rpgRole) {
        super(being);
        this.rpgRole = rpgRole;
    }

    public final Role getRole() {
        return this.rpgRole;
    }

    @Override
    public Sentient getEntity() {
        return (Sentient) super.getEntity();
    }

    public final Sentient getSentientBeing() {
        return (Sentient) this.getEntity();
    }

}
