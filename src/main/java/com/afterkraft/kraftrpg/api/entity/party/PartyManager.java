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
package com.afterkraft.kraftrpg.api.entity.party;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.PartyMember;

/**
 * A basic manager that which manages
 * {@link com.afterkraft.kraftrpg.api.entity.party.Party} for various
 * {@link com.afterkraft.kraftrpg.api.entity.PartyMember}
 */
public interface PartyManager extends Manager {

    /**
     * Creates a new {@link com.afterkraft.kraftrpg.api.entity.party.Party}
     * with the given {@link com.afterkraft.kraftrpg.api.entity.PartyMember}
     * leader as the leader and automatically adds the given members to the
     * party.
     *
     * @param partyLeader leader of the Party
     * @param members     to add to the party after creation
     * @return the party after members have been added
     * @throws IllegalArgumentException If the leader is null
     */
    public Party createParty(PartyMember partyLeader, PartyMember... members);

    /**
     * Check if two PartyMembers are considered friendly to each other.
     * <p/>
     * This method should be symmetrical and reflexive, but not necessarily
     * transitive.
     * <p/>
     * If this method returns false,
     * {@link #isEnemy(com.afterkraft.kraftrpg.api.entity.PartyMember, com.afterkraft.kraftrpg.api.entity.PartyMember)}
     * should not return false.
     * <p/>
     * (They may both, however, return true. Consider this a "neutral" state.)
     *
     * @param a One PartyMember
     * @param b Another PartyMember
     * @return if friendly - e.g., can heal
     * @throws IllegalArgumentException If either PartyMember is null
     */
    public boolean isFriendly(PartyMember a, PartyMember b);

    /**
     * Check if two PartyMembers are considered hostile to each other.
     * <p/>
     * This method should be symmetrical and reflexive, but not necessarily
     * transitive.
     * <p/>
     * If this method returns false,
     * {@link #isFriendly(com.afterkraft.kraftrpg.api.entity.PartyMember, com.afterkraft.kraftrpg.api.entity.PartyMember)}
     * should not return false.
     * <p/>
     * (They may both, however, return true. Consider this a "neutral" state.)
     *
     * @param a One PartyMember
     * @param b Another PartyMember
     * @return if hostile - e.g., can hurt
     * @throws IllegalArgumentException If either PartyMember is null
     */
    public boolean isEnemy(PartyMember a, PartyMember b);
}
