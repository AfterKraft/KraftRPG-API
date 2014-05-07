/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.listeners;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.RPGPlugin;

public abstract class AbstractListener implements Listener, Manager {

    protected RPGPlugin plugin;

    protected AbstractListener(RPGPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void initialize() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void shutdown() {
        HandlerList.unregisterAll(this);
    }
}
