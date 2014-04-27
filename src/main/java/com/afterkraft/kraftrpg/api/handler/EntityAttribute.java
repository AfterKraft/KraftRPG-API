package com.afterkraft.kraftrpg.api.handler;

import java.util.UUID;

import org.bukkit.entity.LivingEntity;

/**
 * @author gabizou
 */
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
