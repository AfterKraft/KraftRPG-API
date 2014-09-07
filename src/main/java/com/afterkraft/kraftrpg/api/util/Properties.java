/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
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


import org.bukkit.Location;
import org.bukkit.Material;
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
     * <p/>
     * It should be known that varying damages are also further customized by
     * {@link com.afterkraft.kraftrpg.api.roles.Role}
     *
     * @return true if enabled, false otherwise.
     */
    public boolean isVaryingDamageEnabled();

    public boolean isStarvingDamageEnabled();

    public int getCombatTime();

    public int getDefaultMaxStamina();

    public int getDefaultStaminaRegeneration();

    /**
     * Gets the stamina increase for the desired Material. If the Material is
     * not a food item, the value of 0 will be given.
     *
     * @param foodMaterial
     * @return
     */
    public int getStaminaIncreaseForFood(Material foodMaterial);

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

    public boolean isMobDamageDistanceModified();

    public String getStorageType();

    /**
     * Third party plugin, VanishNoPacket, checks if the use of VanishNoPacket
     * is configured to be enabled or disabled.
     * @return True if VanishNoPacket is configured to be used for invisibility
     */
    public boolean useVanishIfAvailable();
}
