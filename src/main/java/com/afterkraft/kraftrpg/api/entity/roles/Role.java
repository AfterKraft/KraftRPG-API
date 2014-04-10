package com.afterkraft.kraftrpg.api.entity.roles;

import org.bukkit.Material;

import com.afterkraft.kraftrpg.api.spells.Spell;
import com.afterkraft.kraftrpg.api.spells.SpellArgument;

/**
 * @author gabizou
 */
public interface Role {

    public RoleState getState();

    public String getName();

    public boolean hasSpell(Spell<? extends SpellArgument> spell);

    public boolean hasSpell(String name);

    public double getItemDamage(Material type);

    public void setItemDamage(Material type, double damage);

    public double getItemDamagePerLevel(Material type);

    public void setItemDamagePerLevel(Material type, double damage);

}
