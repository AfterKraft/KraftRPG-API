/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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

import java.util.List;
import java.util.UUID;

import org.spongepowered.api.world.Location;

import com.afterkraft.kraftrpg.api.entity.Being;
import com.afterkraft.kraftrpg.api.role.ExperienceType;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * Party is a grouping of {@link Being}. Useful for maintaining close contact with friendly
 * Champions and the ability to apply buffing effects to the Party. Parties add a new chat channel.
 * Parties are not permanent and will be removed when one Champion remains.
 */
public interface Party {

    UUID getUniqueId();

    /**
     * Return an unmodifiable list of Champions within this Party.
     *
     * @return an unmodifiable list of Champions in this party.
     */
    List<Being> getMembers();

    /**
     * Attempts to add the requested {@link Being}. This will check if the Champion is not already
     * in a party, and if they are, the Champion's party will not be changed.
     *
     * @param member being added to this party.
     *
     * @return true if successful, false if the Champion could not be added for any reason
     * @throws IllegalArgumentException If the member is null
     */
    boolean addMember(Being member);

    /**
     * Attempts to add the requested {@link Being} to this Party. If forced, the Champion's previous
     * party (if any) will be removed and the Champion will be added to this party. If not, the same
     * behavior of {@link #addMember(Being)} will take place.
     *
     * @param member being added to this party.
     * @param forced if true, ignore any existing parties the Champion is part of and re-assign the
     *               Champion to this party.
     *
     * @return true if successful, false if the Champion could not be added
     * @throws IllegalArgumentException If the member is null
     */
    boolean addMember(Being member, boolean forced);

    /**
     * Removes the queried member if it is possible. This should be considered as throwing an event
     * of PartyLeaveEvent that may be cancelled for various reasons. If the event is cancelled, the
     * {@link Being} is not removed.
     *
     * @param member to remove
     *
     * @throws IllegalArgumentException if the member is null
     */
    void removeMember(Being member);

    /**
     * Just like {@link #removeMember(Being)}, except that the PartyLeaveEvent being cancelled will
     * have no effect if forced is true
     *
     * @param member to remove
     * @param forced true if the member will be removed without considering the PartyLeaveEvent's
     *               cancellation
     *
     * @throws IllegalArgumentException If the member is null
     */
    void removeMember(Being member, boolean forced);

    /**
     * Add an invite to this party for the supplied member. Usually, this will result in a message
     * to the {@link Being} noting of the invite taking place.
     *
     * @param member to invite
     *
     * @throws IllegalArgumentException If the member is null
     */
    void addMemberInvite(Being member);

    /**
     * Removes the invite for the member, if it exists.
     *
     * @param member to un-invite.
     *
     * @throws IllegalArgumentException If the member is null
     */
    void removeMemberInvite(Being member);

    /**
     * @param member The member to check whether they have an invite.
     *
     * @return True if the party member has an invite
     * @throws IllegalArgumentException If the member is null
     */
    boolean hasMemberInvite(Being member);

    int size();

    void messageParty(String message, boolean announce);

    boolean emptyParty();

    boolean hasMember(Being member);

    List<UUID> getMemberUniqueIds();

    void logOffMember(Being member);

    void gainExperience(FixedPoint experience, ExperienceType type,
                        Location location);

}
