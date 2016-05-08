/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api.entity.party;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Being;

/**
 * A basic manager that which manages {@link Party} for various {@link Being}s
 */
public interface PartyService extends Manager {

    /**
     * Creates a new {@link Party} with the given {@link Being} leader as the leader and
     * automatically adds the given members to the party.
     *
     * @param partyLeader leader of the Party
     * @param members     to add to the party after creation
     *
     * @return the party after members have been added
     * @throws IllegalArgumentException If the leader is null
     */
    Party createParty(Being partyLeader, Being... members);

    /**
     * Check if two PartyMembers are considered friendly to each other.  This method should be
     * symmetrical and reflexive, but not necessarily transitive.  If this method returns false,
     * {@link #isEnemy(Being, Being)} should not return false. (They may both, however, return true.
     * Consider this a "neutral" state.)
     *
     * @param a One PartyMember
     * @param b Another PartyMember
     *
     * @return if friendly - e.g., can heal
     * @throws IllegalArgumentException If either PartyMember is null
     */
    boolean isFriendly(Being a, Being b);

    /**
     * Check if two PartyMembers are considered hostile to each other.  This method should be
     * symmetrical and reflexive, but not necessarily transitive.  If this method returns false,
     * {@link #isFriendly(Being, Being)} should not return false. (They may both, however, return
     * true. Consider this a "neutral" state.)
     *
     * @param a One PartyMember
     * @param b Another PartyMember
     *
     * @return if hostile - e.g., can hurt
     * @throws IllegalArgumentException If either PartyMember is null
     */
    boolean isEnemy(Being a, Being b);
}
