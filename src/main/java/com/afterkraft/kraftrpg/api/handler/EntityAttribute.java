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

import java.util.UUID;

import org.bukkit.entity.LivingEntity;


public class EntityAttribute {

    private EntityAttributeModifier valueModifier;
    private EntityAttributeModifier balanceModifier;

    public EntityAttribute(String name, EntityAttributeType type) {
        valueModifier = CraftBukkitHandler.getInterface().getEntityAttribute(UUID.nameUUIDFromBytes(new byte[]{ 8, 0, 3, type.getId() }), name);
        balanceModifier = CraftBukkitHandler.getInterface().getEntityAttribute(UUID.nameUUIDFromBytes(new byte[]{ 8, 1, 3, type.getId() }), name + "$NEG");
    }

    public double getValue() {
        return this.valueModifier.getValue();
    }

    public void setValue(double value) {
        this.valueModifier.setValue(value);
        this.balanceModifier.setValue(-value);
    }

    public double loadOrCreate(LivingEntity livingEntity, double alternateValue) {
        return CraftBukkitHandler.getInterface().loadOrCreate(this, livingEntity, alternateValue);
    }

    public enum EntityAttributeType {
        DAMAGE("Damage", (byte) -128),
        EXPERIENCE("Experience", (byte) -127),
        SPAWNX("SpawnX", (byte) -126),
        SPAWNY("SpawnY", (byte) -125),
        SPAWNZ("SpawnZ", (byte) -124),
        FROMSPAWNER("FromSpawner", (byte) -123);

        private String name;
        private byte id;

        EntityAttributeType(String name, byte id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public byte getId() {
            return this.id;
        }
    }

}
