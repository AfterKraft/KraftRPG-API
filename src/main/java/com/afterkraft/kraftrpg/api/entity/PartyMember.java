package com.afterkraft.kraftrpg.api.entity;

import com.afterkraft.kraftrpg.api.entity.party.Party;

/**
 * @author gabizou
 */
public interface PartyMember {

    public boolean hasParty();

    public Party getParty();
}
