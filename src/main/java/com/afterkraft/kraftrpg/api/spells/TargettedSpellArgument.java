package com.afterkraft.kraftrpg.api.spells;

import java.util.UUID;

/**
 * This Specific SpellArgument should contain a targetted Player UUID for
 * the purpose of binding and storage as a Player name may be transient
 * between the targetted Player logging in and out.
 */
public abstract class TargettedSpellArgument extends SpellArgument {

    /**
     * This
     */
    protected String playerName;

    protected UUID playerID;

    protected TargettedSpellArgument(String[] args) {
        super(args);
    }

    public abstract String getName();

    void setPlayerUniqueID(UUID uuid) {
        this.playerID = uuid;
    }

    void setTargetPlayerName(String name) {
        this.playerName = name;
    }

    public String getTargetPlayerName() {
        return this.playerName;
    }

    public UUID getTargetPlayerUniqueID() {
        return this.playerID;
    }

}