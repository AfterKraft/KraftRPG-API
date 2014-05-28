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

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.PartyMember;
import com.afterkraft.kraftrpg.api.entity.party.Party;


public class PartyJoinEvent extends PartyEvent {

    private static final HandlerList handlers = new HandlerList();
    private final PartyMember member;

    public PartyJoinEvent(Party party, PartyMember member) {
        super(party);
        this.member = member;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PartyMember getMember() {
        return this.member;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
