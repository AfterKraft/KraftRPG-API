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
package com.afterkraft.kraftrpg.api.events.entity;

import org.apache.commons.lang.Validate;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Insentient;


public abstract class InsentientEvent extends Event {

    private final Insentient entity;

    protected InsentientEvent(Insentient entity) {
        Validate.notNull(entity, "Cannot create an event for a null Insentient being!");
        this.entity = entity;
    }

    public Insentient getEntity() {
        return this.entity;
    }
}
