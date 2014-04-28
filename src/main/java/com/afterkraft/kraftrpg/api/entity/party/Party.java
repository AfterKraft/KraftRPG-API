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

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * Party is a grouping of {@link com.afterkraft.kraftrpg.api.entity.Champion}s.
 * Useful for maintaining close contact with friendly Champions and the ability
 * to apply buffing effects to the Party. Parties add a new chat channel.
 * <p/>
 * Parties are not permanent and will be removed when one Champion remains.
 */
public interface Party {

    /**
     * Return an unmodifiable list of Champions within this Party.
     *
     * @return an unmodifiable list of Champions in this party.
     */
    public List<Champion> getChampions();

    /**
     * Attempts to add the requested {@link com.afterkraft.kraftrpg.api.entity.Champion}.
     * This will check if the Champion is not already in a party, and if they
     * are, the Champion's party will not be changed.
     *
     * @param champion being added to this party.
     * @return true if successful, false if the Champion could not be added for
     * any reason
     */
    public boolean addChampion(Champion champion);

    /**
     * Attempts to add the requested {@link com.afterkraft.kraftrpg.api.entity.Champion}
     * to this Party. If forced, the Champion's previous party (if any) will be
     * removed and the Champion will be added to this party. If not, the same
     * behavior of {@link #addChampion(com.afterkraft.kraftrpg.api.entity.Champion)}
     * will take place.
     *
     * @param champion being added to this party.
     * @param forced if true, ignore any existing parties the Champion is part
     * of and re-assign the Champion to this party.
     * @return true if successful, false if the Champion could not be added
     */
    public boolean addChampion(Champion champion, boolean forced);

    public void removeChampion(Champion champion);

    public void removeChampion(Champion champion, boolean forced);

    public void addChampionInvite(Champion champion);

    public void removeChampionInvite(Champion champion);

    public boolean hasChampionInvite(Champion champion);

    public int size();

    public void messageParty(String message, boolean announce);

    public boolean emptyParty();

    public boolean hasMember(Champion champion);

    public boolean hasMember(Player player);

    public List<UUID> getPartyMemberUUIDs();

    public void logOffChampion(Champion champion);

}
