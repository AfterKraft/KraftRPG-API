/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.util;

import java.util.Optional;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.world.Location;


/**
 * Standard properties provided for the API. This can too be used by skills for various
 * calculations.
 */
public interface Properties {

    int getDefaultGlobalCooldown();

    boolean isVaryingDamageEnabled();

    boolean isStarvingDamageEnabled();

    int getCombatTime();

    int getDefaultMaxStamina();

    int getDefaultStaminaRegeneration();

    /**
     * Gets the stamina increase for the desired Material. If the Material is not a food item, the
     * value of 0 will be given.
     *
     * @param foodMaterial The provided material
     *
     * @return The value of food hunger to satiate.
     */
    int getStaminaIncreaseForFood(ItemType foodMaterial);

    int getFoodHealPercent();

    int getFoodHealthPerTier();

    long getCombatPeriod();

    FixedPoint getMonsterExperience(Living entity, Location spawnPoint);

    double getExperienceLossMultiplier();

    double getExperienceLossMultiplierForPVP();

    FixedPoint getPlayerKillingExperience();

    boolean hasEntityRewardType(EntityType type);

    Optional<FixedPoint> getEntityReward(EntityType type);

    boolean allowSpawnCamping();

    double getSpawnCampingMultiplier();

    boolean isMobDamageDistanceModified();

    String getStorageType();

}
