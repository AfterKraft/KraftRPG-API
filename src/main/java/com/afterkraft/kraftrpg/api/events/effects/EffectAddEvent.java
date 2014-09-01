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
package com.afterkraft.kraftrpg.api.events.effects;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.effects.IEffect;
import com.afterkraft.kraftrpg.api.entity.Insentient;


public class EffectAddEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Insentient being;
    private boolean isCancelled = false;
    private IEffect effect;

    public EffectAddEvent(Insentient mage, IEffect effect) {
        this.being = mage;
        this.effect = effect;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public IEffect getEffect() {
        return this.effect;
    }

    public Insentient getInsentientBeing() {
        return this.being;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
