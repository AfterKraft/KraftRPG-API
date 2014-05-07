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

import org.bukkit.event.Cancellable;

import com.afterkraft.kraftrpg.api.entity.PartyMember;
import com.afterkraft.kraftrpg.api.entity.party.Party;


public class PartyInviteEvent extends PartyEvent implements Cancellable {

    private final PartyMember inviter;
    private final PartyMember invitee;
    private boolean cancelled;

    public PartyInviteEvent(Party party, PartyMember inviter, PartyMember invitee) {
        super(party);
        this.invitee = invitee;
        this.inviter = inviter;
        cancelled = false;
    }

    public PartyMember getInviter() {
        return inviter;
    }

    public PartyMember getInvitee() {
        return invitee;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}