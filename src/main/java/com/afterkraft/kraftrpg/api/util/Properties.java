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
package com.afterkraft.kraftrpg.api.util;


import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public interface Properties {

    public int getDefaultGlobalCooldown();

    /**
     * Return if the {@link com.afterkraft.kraftrpg.api.util.DamageManager}
     * will calculate varying damages for {@link org.bukkit.entity.EntityType}
     * and {@link org.bukkit.Material}. If varying damage is enabled, all
     * damages are varied depending on each
     * {@link org.bukkit.event.entity.EntityDamageEvent}
     * 
     * It should be known that varying damages are also further customized by
     * {@link com.afterkraft.kraftrpg.api.roles.Role}
     * 
     * @return true if enabled, false otherwise.
     */
    public boolean isVaryingDamageEnabled();

    public boolean isStarvingDamageEnabled();

    public int getCombatTime();

    public int getFoodHealPercent();

    public int getFoodHealthPerTier();

    public long getCombatPeriod();

    public FixedPoint getMonsterExperience(LivingEntity entity, Location spawnPoint);

    public double getExperienceLossMultiplier();

    public double getExperienceLossMultiplierForPVP();

    public FixedPoint getPlayerKillingExperience();

    public boolean hasEntityRewardType(EntityType type);

    public FixedPoint getEntityReward(EntityType type);

    public boolean allowSpawnCamping();

    public double getSpawnCampingMultiplier();
}
