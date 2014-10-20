/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api;

import java.util.Collection;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.entity.damage.InsentientDamageEvent.DamageType;
import com.afterkraft.kraftrpg.api.handler.ServerInternals;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.util.PermissionsManager;

/**
 * Utility class providing simple and fast method calls to various managers.
 */
public final class RpgCommon {

    private static Server bukkitServer;
    private static PermissionsManager permissionsManager;
    private static RPGPlugin rpgPlugin;
    private static ServerInternals serverInternals;

    private static boolean isPluginEnabled = false;

    private RpgCommon() {

    }

    public static void finish() {
        isPluginEnabled = true;
    }

    public static boolean damageCheck(Insentient attacker, Insentient victim) {
        checkArgument(attacker != null, "Cannot have a null attacker!");
        checkArgument(victim != null, "Cannot have a null victim!");
        return getHandler().damageCheck(attacker, victim);
    }

    public static ServerInternals getHandler() {
        checkArgument(RpgCommon.serverInternals != null,
                "The plugin has not been initialized yet!");
        return RpgCommon.serverInternals;
    }

    public static void setHandler(ServerInternals handler) {
        checkArgument(handler != null, "Cannot set a null handler!");
        check();
        RpgCommon.serverInternals = handler;
    }

    private static void check() {
        if (isPluginEnabled) {
            throw new IllegalStateException("RPGPlugin is already enabled!");
        }
    }

    public static void setProjectileDamage(IEntity arrow, double damage) {
        getHandler().setProjectileDamage(arrow, damage);
    }

    public static boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                       double damage, DamageCause cause, boolean knockback) {
        return getHandler().damageEntity(target, attacker, skill, damage, cause, knockback);
    }

    public static boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                       Map<DamageType, Double> modifiers, DamageCause cause,
                                       boolean knockback, boolean ignoreDamageCheck) {
        return getHandler().damageEntity(target, attacker, skill, modifiers, cause, knockback,
                ignoreDamageCheck);
    }

    public static boolean healEntity(Insentient being, double tickHealth, ISkill skill,
                                     Insentient applier) {
        return getHandler().healEntity(being, tickHealth, skill, applier);
    }

    @SuppressWarnings("deprecated")
    public static Player getPlayerByName(String name) {
        return getCommonServer().getPlayer(name);
    }

    public static Server getCommonServer() {
        checkArgument(RpgCommon.bukkitServer != null, "The server has not been set yet!");
        return RpgCommon.bukkitServer;
    }

    public static void setCommonServer(Server bukkitServer) {
        checkArgument(bukkitServer != null, "Cannot set a null server!");
        check();
        RpgCommon.bukkitServer = bukkitServer;
    }

    @SuppressWarnings("deprecated")
    public static Player getPlayerExact(String name) {
        return getCommonServer().getPlayerExact(name);
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        return getCommonServer().getOnlinePlayers();
    }

    public static Champion getChampion(Player player) {
        return getPlugin().getEntityManager().getChampion(player);
    }

    public static RPGPlugin getPlugin() {
        checkArgument(RpgCommon.rpgPlugin != null,
                "The plugin has yet to be initialized");
        return RpgCommon.rpgPlugin;
    }

    public static void setPlugin(RPGPlugin plugin) {
        checkArgument(plugin != null, "Cannot set a null plugin!");
        checkArgument(!plugin.isEnabled(), "Cannot set a plugin that is already enabled!");
        check();
        RpgCommon.rpgPlugin = plugin;
    }

    public static void hideInsentient(Insentient being) {
        getHandler().hidePlayer(being);
    }

    public static boolean isOp(final IEntity entity) {
        return getPermissionManager().isOp(entity);
    }

    public static PermissionsManager getPermissionManager() {
        checkArgument(RpgCommon.permissionsManager != null,
                "PermissionManager has yet to be initialized");
        return RpgCommon.permissionsManager;
    }

    public static void setPermissionManager(PermissionsManager permissionManager) {
        checkArgument(permissionManager != null, "Cannot set a null permission manager!");
        check();
        RpgCommon.permissionsManager = permissionManager;
    }

    public static boolean hasPermission(final IEntity entity, final String permission) {
        return getPermissionManager().hasPermission(entity, permission);
    }

    public static boolean hasWorldPermission(final IEntity entity, final World world,
                                             final String permission) {
        return getPermissionManager().hasWorldPermission(entity, world, permission);
    }

    public static boolean hasWorldPermission(final IEntity entity, final String worldName,
                                             final String permission) {
        return getPermissionManager().hasWorldPermission(entity, worldName, permission);
    }

    public static void addGlobalPermission(final IEntity entity, final String permission) {
        getPermissionManager().addGlobalPermission(entity, permission);
    }

    public static void addWorldPermission(final IEntity entity, final World world,
                                          final String permission) {
        getPermissionManager().addWorldPermission(entity, world, permission);
    }

    public static void addWorldPermission(final IEntity entity, final String worldName,
                                          final String permission) {
        getPermissionManager().addWorldPermission(entity, worldName, permission);
    }

    public static void addTransientGlobalPermission(final IEntity entity, final String permission) {
        getPermissionManager().addTransientGlobalPermission(entity, permission);
    }

    public static void addTransientWorldPermission(final IEntity entity, final World world,
                                                   final String permission) {
        getPermissionManager().addTransientWorldPermission(entity, world, permission);
    }

    public static void addTransientWorldPermission(final IEntity entity, final String worldName,
                                                   final String permission) {
        getPermissionManager().addTransientWorldPermission(entity, worldName, permission);
    }

    public static void removeGlobalPermission(final IEntity entity, final String permission) {
        getPermissionManager().removeGlobalPermission(entity, permission);
    }

    public static void removeWorldPermission(final IEntity entity, final World world,
                                             final String permission) {
        getPermissionManager().removeWorldPermission(entity, world, permission);
    }

    public static void removeWorldPermission(final IEntity entity, final String worldName,
                                             final String permission) {
        getPermissionManager().removeWorldPermission(entity, worldName, permission);
    }

    public static void removeTransientGlobalPermission(final IEntity entity,
                                                       final String permission) {
        getPermissionManager().removeTransientGlobalPermission(entity, permission);
    }

    public static void removeTransientWorldPermission(final IEntity entity, final World world,
                                                      final String permission) {
        getPermissionManager().removeTransientWorldPermission(entity, world, permission);
    }

    public static void removeTransientWorldPermission(final IEntity entity, final String worldName,
                                                      final String permission) {
        getPermissionManager().removeTransientWorldPermission(entity, worldName, permission);
    }

    public static IEntity getEntity(Entity entity) {
        return getPlugin().getEntityManager().getEntity(entity);
    }

    public static void knockback(Insentient target, Insentient attacker, double damage) {
        checkArgument(attacker != null, "Cannot have a null attacker!");
        checkArgument(target != null, "Cannot have a null victim!");
        getHandler().knockBack(target, attacker, damage);
    }

    public static void knockBack(LivingEntity target, LivingEntity attacker, double damage) {
        knockback((Insentient) getPlugin().getEntityManager().getEntity(target),
                (Insentient) getPlugin().getEntityManager().getEntity(attacker), damage);
    }

    public static boolean damageEntity(LivingEntity target, Insentient attacker, ISkill skill,
                                       double damage, DamageCause cause, boolean knockback) {
        return getHandler().damageEntity(
                (Insentient) getPlugin().getEntityManager().getEntity(target), attacker, skill,
                damage, cause, knockback);
    }
}
