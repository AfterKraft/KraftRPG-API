package com.afterkraft.kraftrpg.api.entity.party;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * @author gabizou
 */
public interface PartyManager extends Manager {

    public Party createParty(Champion partyLeader, Champion... members);

}
