package com.afterkraft.kraftrpg.api.handler;

import java.util.UUID;

/**
 * @author gabizou
 */
public abstract class EntityAttributeModifier {

    private double value;

    public EntityAttributeModifier(UUID id, String name) { }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
