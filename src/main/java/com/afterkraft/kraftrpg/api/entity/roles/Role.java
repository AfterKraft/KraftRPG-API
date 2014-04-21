package com.afterkraft.kraftrpg.api.entity.roles;

import org.bukkit.Material;

import com.afterkraft.kraftrpg.api.spells.ISpell;
import com.afterkraft.kraftrpg.api.spells.SpellArgument;

/**
 * @author gabizou
 */
public interface Role {

    /**
     * Get the type of Role this is.
     * @return the {@link com.afterkraft.kraftrpg.api.entity.roles.RoleType}
     */
    public RoleType getType();

    /**
     * Return the configured name for this Role
     * @return the name for this role
     */
    public String getName();

    /**
     * Check if this Role has the given Spell
     * @param spell
     * @return
     */
    public boolean hasSpell(ISpell spell);

    public boolean hasSpell(String name);

    public double getItemDamage(Material type);

    public void setItemDamage(Material type, double damage);

    public double getItemDamagePerLevel(Material type);

    public void setItemDamagePerLevel(Material type, double damage);

}
