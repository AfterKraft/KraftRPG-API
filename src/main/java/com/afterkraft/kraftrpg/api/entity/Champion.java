package com.afterkraft.kraftrpg.api.entity;

import java.util.Set;

import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.entity.roles.Role;
import com.afterkraft.kraftrpg.api.spells.Spell;
import com.afterkraft.kraftrpg.api.spells.SpellArgument;

/**
 * @author gabizou
 */
public interface Champion extends IEntity {

    public Player getPlayer();

    public void setPlayer(Player player);

    public Role getPrimaryRole();

    public Role getSecondaryRole();

    public void setPrimaryRole(Role role);

    public void setSecondaryRole(Role role);

    public Set<Role> getAdditionalRoles();

    public boolean addAdditionalRole(Role role);

    public boolean removeAdditionalRole(Role role);

    public int getLevel(Role role);

    public double getExperience(Role role);

    public boolean canPrimaryUseSpell(Spell<? extends SpellArgument> spell);

    public boolean canSecondaryUseSpell(Spell<? extends SpellArgument> spell);

    public double recalculateMaxHealth();
}
