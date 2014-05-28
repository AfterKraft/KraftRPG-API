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

import com.afterkraft.kraftrpg.api.entity.party.Party;

/**
 * Represents a being that is allowed to join {@link Party}s.
 */
public interface PartyMember {

    /**
     * Check if this being is currently in a party
     * 
     * @return true if the being is in a party
     */
    public boolean hasParty();

    /**
     * Get the currently attached Party for this being.
     * 
     * @return
     */
    public Party getParty();

    /**
     * Set this member's current party.
     * 
     * @param party to set
     */
    public void setParty(Party party);

    /**
     * Leaves this member's current party.
     */
    public void leaveParty();
}
