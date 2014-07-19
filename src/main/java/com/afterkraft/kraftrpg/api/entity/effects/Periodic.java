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
package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Represents an effect that will perform some action on the
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} per tick as defined
 * by {@link #tick(com.afterkraft.kraftrpg.api.entity.Insentient)}
 */
public interface Periodic extends IEffect, Timed {

    /**
     * Returns the last time the effect ticked
     * 
     * @return the time in milliseconds this effect was last ticked
     */
    public long getLastTickTime();

    /**
     * @return the duration of this effect
     */
    public long getPeriod();

    /**
     * Returns whether the effect is ready for ticking
     * 
     * @return - The ready state of this effect. DO NOT CALL IF THIS IS FALSE
     */
    public boolean isReady();

    /**
     * Ticks this effect on the specified entity.
     * 
     * @param being - The being this effect is being applied to
     * @throws IllegalArgumentException If the being is null
     */
    public void tick(Insentient being);

}
