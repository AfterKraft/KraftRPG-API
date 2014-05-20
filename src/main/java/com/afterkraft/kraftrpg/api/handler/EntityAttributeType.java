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
package com.afterkraft.kraftrpg.api.handler;

import org.bukkit.event.entity.CreatureSpawnEvent;

public enum EntityAttributeType {
    DAMAGE,
    EXPERIENCE,
    SPAWNX,
    SPAWNY,
    SPAWNZ,
    SPAWNREASON(0, CreatureSpawnEvent.SpawnReason.values().length), ;

    private String attrName;
    private double minValue, maxValue;

    private EntityAttributeType() {
        this(-Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private EntityAttributeType(double minValue, double maxValue) {
        this.attrName = "kraftrpg-" + name();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private EntityAttributeType(String attributeName, double minValue, double maxValue) {
        this.attrName = attributeName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public double getMin() {
        return minValue;
    }

    public double getMax() {
        return maxValue;
    }

    public String getIdentifier() {
        return attrName;
    }
}
