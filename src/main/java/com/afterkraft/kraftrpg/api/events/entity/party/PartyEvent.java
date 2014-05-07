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
package com.afterkraft.kraftrpg.api.events.entity.party;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.party.Party;


public class PartyEvent extends Event {
    protected static final HandlerList handlers = new HandlerList();
    private final Party party;

    public PartyEvent(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return this.party;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}