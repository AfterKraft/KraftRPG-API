package com.afterkraft.kraftrpg.api.spells;

import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.permissions.Permission;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * PermissionSpell allows setting any other Plugin's permission to become a
 * Spell through permission restrictions. A PermissionSpell does not allow any
 * type of SpellArguments and it will always return true on Spell use.
 *
 * PermissionSpell can apply Permissions with both true and false
 * @author gabizou
 */
public class PermissionSpell extends Spell implements Permissible {

    private Map<String, Boolean> permissions;
    private Permission permission;

    public PermissionSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void initialize() { }

    @Override
    public void shutdown() { }


    @Override
    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
        this.permission = plugin.getServer().getPluginManager().getPermission(this.getName());
        if (this.permission != null) {
            plugin.getServer().getPluginManager().removePermission(this.permission);
        }

        this.permission = new Permission(getName(), "PermissionSpell " + getName(), this.permissions);
        plugin.getServer().getPluginManager().addPermission(this.permission);
    }

    @Override
    public void tryLearning(Champion player) {

    }

    @Override
    public boolean addSpellTarget(Entity entity, Champion champion) {
        return true;
    }
}
