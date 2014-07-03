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

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * This is a simple wrapper for the damage dealt to an entity. It wraps the
 * various necessary information required to handle KraftRPG events.
 */
public class DamageWrapper {

    final DamageCause originalCause;
    final double originalDamage;
    double modifiedDamage;
    DamageCause modifiedCause;

    public DamageWrapper(DamageCause originalCause, double originalDamage, double modifiedDamage, DamageCause modifiedCause) {
        this.originalCause = originalCause;
        this.originalDamage = originalDamage;
        this.modifiedDamage = modifiedDamage;
        this.modifiedCause = modifiedCause;
    }

    public DamageCause getOriginalCause() {
        return originalCause;
    }

    public double getOriginalDamage() {
        return originalDamage;
    }

    public double getModifiedDamage() {
        return modifiedDamage;
    }

    public void setModifiedDamage(double modifiedDamage) {
        this.modifiedDamage = modifiedDamage;
    }

    public DamageCause getModifiedCause() {
        return modifiedCause;
    }

    public void setModifiedCause(DamageCause modifiedCause) {
        this.modifiedCause = modifiedCause;
    }
}
