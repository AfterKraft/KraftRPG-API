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
package com.afterkraft.kraftrpg.api.entity;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.afterkraft.kraftrpg.api.entity.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.effects.IEffect;

/**
 * Represents an Insentient being that allows retreival of the being's status
 * and handles the basic
 * {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect}.
 * 
 * It is important to note that an Insentient, and it's subclasses, may not
 * always be attached to a {@link org.bukkit.entity.LivingEntity}. This is why
 * these methods are provided to assure that Effects and Skills may still
 * function and apply themselves to Insentient beings.
 * 
 * It is advisable that the following method may return null: <code>
 *     {@link #getEntity()}
 * </code> and therefor any common information retrieval should be performed
 * using the supplied methods instead of assuming the LivingEntity methods.
 */
public interface Insentient {

    /**
     * Attempts to fetch the attached LivingEntity of this Insentient Being,
     * unless a LivingEntity is not attached, to which this will return null.
     * 
     * @return the attached LivingEntity, if there is no LivingEntity, then
     *         null.
     */
    public LivingEntity getEntity();

    /**
     * Get the current mana this being has.
     * 
     * @return current mana
     */
    public int getMana();

    /**
     * Set the total mana of the being.
     * 
     * @param mana this being should have.
     */
    public void setMana(int mana);

    /**
     * Return the health of the being.
     * 
     * @return health
     */
    public double getHealth();

    /**
     * Attempts to set the health of the being. If the being is dead, the
     * health will not be updated.
     * 
     * @param health to which the being should have.
     */
    public void setHealth(double health);

    /**
     * Check if this being is dead
     * 
     * @return true if dead
     */
    public boolean isDead();

    /**
     * Assuming this being is affected by stamina, returns the current stamina
     * of the being
     * 
     * @return the stamina of this being
     */
    public float getStamina();

    /**
     * Modifies the current stamina of the being. Stamina can be negative
     * 
     * @param staminaDiff to add to the current mana of the being
     */
    public void modifyStamina(float staminaDiff);

    /**
     * A cleaning method provided to ensure possible connected client's view
     * of an inventory is properly refreshed, and not erroneously represented.
     */
    public void updateInventory();

    /**
     * Assumes this being has a functional hand, this will return the
     * {@link org.bukkit.inventory.ItemStack} in said hand.
     * 
     * @return the ItemStack in the hand of the being.
     */
    public ItemStack getItemInHand();

    /**
     * Returns a proper {@link org.bukkit.inventory.Inventory} that belongs to
     * this being. It can be modified and queried without exception.
     * 
     * @return the inventory belonging to this being.
     */
    public Inventory getInventory();

    /**
     * Provided as a utility method to get the being's armor. Implementations
     * may vary, but this should be assured to follow the index representation
     * provided by the following methods: <code>
     *     <ul>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexHelmet()}</li>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexChestPlate()} </li>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexLeggings()} </li>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexBoots()} </li>
     *     </ul>
     * </code>
     * 
     * @return The unmodifiable ItemStack list of the armor for this being
     */
    public ItemStack[] getArmor();

    /**
     * Return the {@link org.bukkit.Location} of this being.
     * 
     * @return the location of the being.
     */
    public Location getLocation();

    /**
     * Returns (if available) the named
     * {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect}
     * 
     * @param name the name of the desired Effect
     * @return the named Effect if not null
     */
    public IEffect getEffect(String name);

    /**
     * Returns an unmodifiable set of Effects this Champion has active.
     * 
     * @return an unmodifiable set of Effects this Champion has active.
     */
    public Set<IEffect> getEffects();

    /**
     * Adds the given Effect to this Insentient being. Added Effects will be
     * applied on the next tick so as to avoid
     * 
     * @param IEffect
     */
    public void addEffect(IEffect IEffect);

    /**
     * Add the {@link org.bukkit.potion.PotionEffect} to this Insentient
     * being.
     * <p>
     * This method is provided with the assurance that the entity would not
     * have a {@link java.util.ConcurrentModificationException} caused by
     * multiple sources.
     * 
     * @param potion the effect to be applied
     */
    public void addPotionEffect(PotionEffect potion);

    /**
     * Remove the {@link org.bukkit.potion.PotionEffectType} from this
     * Insentient.
     * <p>
     * This method is provided with the assurance that the entity would not
     * have a {@link java.util.ConcurrentModificationException} caused by
     * multiple sources.
     * 
     * @param type of PotionEffect to remove
     */
    public void removePotionEffect(PotionEffectType type);

    /**
     * Checks if this being has a specific PotionEffectType
     * 
     * @param type to check
     * @return true if the being has the queried type of potion effect
     */
    public boolean hasPotionEffect(PotionEffectType type);

    /**
     * Check if this Insentient being has an
     * {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect} with the
     * given name.
     * 
     * @param name of the effect
     * @return true if there is an active effect by the queried name
     */
    public boolean hasEffect(String name);

    /**
     * Check if this being has an effect of the given
     * {@link com.afterkraft.kraftrpg.api.entity.effects.EffectType}.
     * 
     * @param type of effect
     * @return true if this being has effect of the queried type
     */
    public boolean hasEffectType(EffectType type);

    /**
     * Safely removes the {@link IEffect} from this being. This will call
     * {@link IEffect#remove(Insentient)} as effects may perform various
     * actions accordingly.
     * 
     * @param effect to be removed.
     */
    public void removeEffect(IEffect effect);

    /**
     * Unsafely removes the IEffect from this being. It will not call
     * {@link IEffect#remove(Insentient)} and may leave behind some unintended
     * {@link org.bukkit.potion.PotionEffect}s on the being.
     * 
     * @param effect being removed.
     */
    public void manualRemoveEffect(IEffect effect);

    /**
     * Safely removes all active effects on this being.
     */
    public void clearEffects();

    /**
     * Unsafely removes all active effects without calling
     * {@link IEffect#remove(Insentient)}
     */
    public void manualClearEffects();
}
