package com.afterkraft.kraftrpg.api.spells;

import java.util.UUID;

/**
 * This Specific SpellArgument should contain a targetted entity UUID for the
 * purpose of binding and storage as a Player name may be transient between the
 * targetted Player logging in and out.
 */
public abstract class TargetedSpellArgument extends SpellArgument {

    /**
     * This is known to be the last known username of the targetted entity
     */
    private String entityName;

    /**
     * This is the uuid for the targetted entity
     */
    private UUID entityID;

    protected TargetedSpellArgument(String[] args) {
        super(args);
    }

    /**
     * Return the last known name of the targetted {@link
     * org.bukkit.entity.LivingEntity}
     *
     * @return the last known name of the targetted entity
     */
    public String getTargetName() {
        return this.entityName;
    }

    /**
     * Set the targetted entity's username
     *
     * @param name
     */
    protected void setTargetName(String name) {
        this.entityName = name;
    }

    /**
     * Return the stored UUID of the targetted Player, this is safe to retrieve
     * the OfflinePlayer or Player
     *
     * @return the UUID of the targetted Player
     */
    public UUID getTargetUniqueID() {
        return this.entityID;
    }

    /**
     * Set the targetted entity's UUID
     *
     * @param uuid
     */
    protected void setTargetUniqueID(UUID uuid) {
        this.entityID = uuid;
    }

}