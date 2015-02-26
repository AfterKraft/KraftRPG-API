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
import static com.google.common.base.Preconditions.checkState;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.World;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.util.PermissionsManager;
import com.afterkraft.kraftrpg.common.DamageCause;
import com.afterkraft.kraftrpg.common.DamageType;
import com.afterkraft.kraftrpg.common.handler.ServerInternals;

/**
 * Utility class providing simple and fast method calls to various managers.
 */
public final class RpgCommon {
    private static Server server;
    private static Game game;
    private static PermissionsManager permissionsManager;
    private static RPGPlugin plugin;
    private static ServerInternals serverInternals;

    private static boolean isPluginEnabled = false;

    private RpgCommon() {

    }

    public static void finish() {
        isPluginEnabled = true;
    }

    public static ServerInternals getHandler() {
        checkArgument(RpgCommon.serverInternals != null, "The plugin has not been initialized "
                + "yet!");
        return RpgCommon.serverInternals;
    }

    public static void setHandler(ServerInternals handler) {
        check();
        RpgCommon.serverInternals = handler;
    }

    public static Game getGame() {
        return RpgCommon.game;
    }

    public static void setGame(Game game) {
        check();
        RpgCommon.game = game;
    }

    public static Server getServer() {
        checkArgument(RpgCommon.server != null, "The server has not been set yet!");
        return RpgCommon.server;
    }

    public static void setCommonServer(Server bukkitServer) {
        check();
        RpgCommon.server = bukkitServer;
    }

    public static RPGPlugin getPlugin() {
        return RpgCommon.plugin;
    }

    public static void setPlugin(RPGPlugin plugin) {
        check();
        RpgCommon.plugin = plugin;
    }

    public static PermissionsManager getPermissionManager() {
        return RpgCommon.permissionsManager;
    }

    public static void setPermissionManager(PermissionsManager permissionManager) {
        check();
        RpgCommon.permissionsManager = permissionManager;
    }

    private static void check() {
        checkState(!isPluginEnabled, "RPGPlugin is already enabled!");
    }

    public static void setProjectileDamage(IEntity arrow, double damage) {
        getHandler().setProjectileDamage(arrow, damage);
    }

    public static boolean damageCheck(Insentient attacker, Insentient victim) {
        return getHandler().damageCheck(attacker, victim);
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

    public static Optional<Player> getPlayerByName(String name) {
        return getServer().getPlayer(name);
    }

    public static Optional<Player> getPlayerExact(String name) {
        return getServer().getPlayer(name);
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        return getServer().getOnlinePlayers();
    }

    public static Optional<? extends Champion> getChampion(Player player) {
        return getPlugin().getEntityManager().getChampion(player);
    }

    public static Optional<? extends IEntity> getEntity(Entity entity) {
        return getPlugin().getEntityManager().getEntity(entity);
    }

    public static void hideInsentient(Insentient being) {
        getHandler().hideInsentient(being);
    }

    public static boolean isOp(final IEntity entity) {
        return getPermissionManager().isOp(entity);
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

    public static void knockback(Insentient target, Insentient attacker, double damage) {
        getHandler().knockBack(target, attacker, damage);
    }

    public static void knockBack(Living target, Living attacker, double damage) {
        knockback((Insentient) getPlugin().getEntityManager().getEntity(target),
                (Insentient) getPlugin().getEntityManager().getEntity(attacker), damage);
    }

    public static boolean damageEntity(Living target, Insentient attacker,
                                       ISkill skill,
                                       double damage, DamageCause cause, boolean knockback) {
        return getHandler().damageEntity(
                (Insentient) getPlugin().getEntityManager().getEntity(target), attacker, skill,
                damage, cause, knockback);
    }

    public static Logger getLogger() {
        return game.getPluginManager().getLogger((PluginContainer) getPlugin());
    }
}
