package com.afterkraft.kraftrpg.api.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;

import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.skills.SkillBind;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * This class is not a stable API.
 */
public class PlayerData {
    /**
     * Main Roles.
     */
    public Role primary, profession;

    /**
     * Extra roles - these grant skills, but no hp/mana.
     */
    public final Set<Role> additionalRoles = new HashSet<Role>();

    /**
     * Experience data.
     */
    public final Map<Role, FixedPoint> exp = new HashMap<Role, FixedPoint>();

    /**
     * Raw bind data.
     */
    public final Map<Material, SkillBind> binds = new HashMap<Material, SkillBind>();

    /**
     * Map from cooldown name to expiry time, in UNIX time.
     */
    public final Map<String, Long> cooldowns = new HashMap<String, Long>();

}
