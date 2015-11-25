/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.afterkraft.kraftrpg.api.entity.EntityService;
import com.afterkraft.kraftrpg.api.entity.combat.CombatTracker;
import com.afterkraft.kraftrpg.api.entity.party.PartyManager;
import com.afterkraft.kraftrpg.api.platform.Platform;
import com.afterkraft.kraftrpg.api.platform.PlatformManager;
import com.afterkraft.kraftrpg.api.role.RoleManager;
import com.afterkraft.kraftrpg.api.service.Service;
import com.afterkraft.kraftrpg.api.service.ServiceManager;
import com.afterkraft.kraftrpg.api.service.ServiceProvider.Type;
import com.afterkraft.kraftrpg.api.skill.SkillConfigManager;
import com.afterkraft.kraftrpg.api.skill.SkillService;
import com.afterkraft.kraftrpg.api.storage.StorageFrontend;
import com.afterkraft.kraftrpg.api.util.ConfigManager;
import com.afterkraft.kraftrpg.api.util.DamageManager;
import com.afterkraft.kraftrpg.api.util.Pair;
import com.afterkraft.kraftrpg.api.util.init.AnnotationCacheHelper;

/**
 * Utility class providing simple and fast method calls to various managers.
 */
public class RpgCommon implements PlatformManager {

    private Thread mainThread;
    private ClassLoader classloader;
    private Map<Type, List<ServiceProvider>> providers;
    private Map<Class<?>, List<Pair<Object, Method>>> initHooks;
    private Map<Class<?>, Service> services;
    private List<Class<?>> stoppedServices;
    private State state;
    private boolean testing = false;
    private ServiceProvider testingProvider = null;
    private Map<Class<?>, Service> testingServices = null;
    // BEGIN ExpansionManager
    private List<Platform> expansions;

    private RpgCommon() {
        this.mainThread = Thread.currentThread();
        this.classloader = RpgCommon.class.getClassLoader();
        initServiceManager();
        initPlatformManager();
    }

    private void initServiceManager() {
        this.providers = new EnumMap<>(ServiceProvider.Type.class);
        this.stoppedServices = Lists.newArrayList();
        this.state = State.STOPPED;
        this.services = Maps.newHashMap();
        this.initHooks = Maps.newHashMap();
    }

    private void initPlatformManager() {
        this.expansions = Lists.newArrayList();
    }


    public static PlatformManager getPlatformManager() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Gets the currently used {@link SkillConfigManager}.
     *
     * @return The currently used skill configuration manager
     */
    public static SkillConfigManager getSkillConfigManager() {
        return InstanceHolder.INSTANCE.getService(SkillConfigManager.class).get();
    }

    /**
     * Gets the currently used {@link CombatTracker}.
     *
     * @return The currently used combat manager
     */
    public static CombatTracker getCombatTracker() {
        return InstanceHolder.INSTANCE.getService(CombatTracker.class).get();
    }

    /**
     * Gets the currently used {@link EntityService}.
     *
     * @return The currently used entity manager
     */
    public static EntityService getEntityManager() {
        return InstanceHolder.INSTANCE.getService(EntityService.class).get();
    }

    /**
     * Gets the currently used {@link SkillConfigManager}.
     *
     * @return The currently used skill configuration manager
     */
    public static StorageFrontend getStorage() {
        return InstanceHolder.INSTANCE.getService(StorageFrontend.class).get();
    }

    /**
     * Gets the currently used {@link ConfigManager}.
     *
     * @return The currently used configuration manager
     */
    public static ConfigManager getConfigurationManager() {
        return InstanceHolder.INSTANCE.getService(ConfigManager.class).get();
    }

    /**
     * Gets the currently used {@link DamageManager}.
     *
     * @return The currently used damage manager
     */
    public static DamageManager getDamageManager() {
        return InstanceHolder.INSTANCE.getService(DamageManager.class).get();
    }

    /**
     * Gets the currently used {@link SkillService}.
     *
     * @return The currently used skill manager
     */
    public static SkillService getSkillManager() {
        return InstanceHolder.INSTANCE.getService(SkillService.class).get();
    }

    /**
     * Gets the currently used {@link RoleManager}.
     *
     * @return The currently used role manager
     */
    public static RoleManager getRoleManager() {
        return InstanceHolder.INSTANCE.getService(RoleManager.class).get();
    }

    /**
     * Gets the currently used {@link PartyManager}.
     *
     * @return The currently used party manager
     */
    public static PartyManager getPartyManager() {
        return InstanceHolder.INSTANCE.getService(PartyManager.class).get();
    }

    public static Server getServer() {
        return InstanceHolder.INSTANCE.getService(Server.class).get();
    }

    public static Game getGame() {
        return InstanceHolder.INSTANCE.getService(Game.class).get();
    }

    public static RpgPlugin getPlugin() {
        return InstanceHolder.INSTANCE.getService(RpgPlugin.class).get();
    }



    public static Logger getLogger() {
        return InstanceHolder.INSTANCE.getService(Logger.class).get();
    }

    @Override
    public void registerPlatform(Platform ex) {
        checkNotNull(ex);
        synchronized (this.expansions) {
            if (!this.expansions.contains(ex)) {
                this.expansions.add(ex);
            }
        }
    }

    @Override
    public Collection<Platform> getPlatforms() {
        return this.expansions;
    }

    private static class InstanceHolder {
        protected static final RpgCommon INSTANCE = new RpgCommon();
    }
}
