package com.afterkraft.kraftrpg.api.spells;

import java.util.UUID;

/**
 * This Specific SpellArgument should contain a targetted entity UUID for
 * the purpose of binding and storage as a Player name may be transient
 * between the targetted Player logging in and out.
 */
public abstract class TargettedSpellArgument extends SpellArgument {

    /**
     * This is known to be the last known username of the targetted entity
     */
    protected String entityName;

    /**
     * This is the uuid for the targetted entity
     */
    protected UUID entityID;

    protected TargettedSpellArgument(String[] args) {
        super(args);
    }

    /**
     * Set the targetted entity's UUID
     * @param uuid
     */
    void setTargetUniqueID(UUID uuid) {
        this.entityID = uuid;
    }

    /**
     * Set the targetted entity's username
     * @param name
     */
    void setTargetName(String name) {
        this.entityName = name;
    }

    /**
     * Return the last known name of the targetted {@link org.bukkit.entity.LivingEntity}
     * @return the last known name of the targetted entity
     */
    public String getTargetName() {
        return this.entityName;
    }

    /**
     * Return the stored UUID of the targetted Player, this is safe to retrieve
     * the OfflinePlayer or Player
     * @return the UUID of the targetted Player
     */
    public UUID getTargetUniqueID() {
        return this.entityID;
    }

}