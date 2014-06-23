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

import java.util.Set;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.afterkraft.kraftrpg.api.entity.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.effects.IEffect;
import com.afterkraft.kraftrpg.api.listeners.DamageWrapper;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

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
public interface Insentient extends IEntity {

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
     * Gets the current max mana
     *
     * @return the max mana for this being
     */
    public int getMaxMana();

    /**
     * Sets the current maximum mana for this being.
     *
     * @param mana as the maximum
     */
    public void setMaxMana(int mana);

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
     * Return the max health for this being.
     *
     * @return maximum health
     */
    public double getMaxHealth();

    /**
     * Add a fixed health bonus that does not get reset when
     * {@link #recalculateMaxHealth()} is called. The health bonus, unless
     * provided specifically by KraftRPG, does not survive between reloads,
     * restarts, relogging and other times when the entity is stored and
     * loaded back into memory.
     * 
     * @param key id based on string to apply the max health
     * @param value of health to add as the maximum
     * @return true if successful, if the key did not exist before.
     */
    public boolean addMaxHealth(String key, double value);

    /**
     * Removes an additional health modifier from the calculations for the
     * {@link org.bukkit.entity.LivingEntity#getMaxHealth()}. Removing
     * KraftRPG specific mappings may have unknown side-effects.
     * 
     * @param key linking to the additional health bonus for this being
     * @return true if successful, false if the mapping didn't exist.
     */
    public boolean removeMaxHealth(String key);

    /**
     * Forcefully recalculates the total max health the linked
     * {@link org.bukkit.entity.LivingEntity} should have and applies it. This
     * will also apply the current percentage of total health the entity has
     * compared to their current max health.
     * 
     * @return the newly recalculated max health
     */
    public double recalculateMaxHealth();

    /**
     * Attempts to heal the {@link org.bukkit.entity.LivingEntity} the defined
     * amount of health. This will not allow healing past the current max
     * health
     * 
     * @param amount to heal
     */
    public void heal(double amount);

    /**
     * Clears out all health bonuses to the max health except the necessary
     * provided by the default implementation of KraftRPG.
     */
    public void clearHealthBonuses();


    public DamageWrapper getDamageWrapper();

    public void setDamageWrapper(DamageWrapper wrapper);

    /**
     * Check if this being is dead
     * 
     * @return true if dead
     */
    public boolean isDead();

    /**
     * A helper method to get the internal no damage ticks for the represented
     * LivingEntity.
     * 
     * @return amount of no damage ticks
     */
    public int getNoDamageTicks();

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
     * Provided as a utility method to get a copy of the being's armor.
     * Implementations may vary, but this should be assured to follow the
     * index representation provided by the following methods: <code>
     *     <ul>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexHelmet()}</li>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexChestPlate()} </li>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexLeggings()} </li>
     *         <li>{@link com.afterkraft.kraftrpg.api.handler.CraftBukkitHandler#getArmorIndexBoots()} </li>
     *     </ul>
     * </code>
     * 
     * @return The copy of the ItemStack list of the armor for this being
     */
    public ItemStack[] getArmor();

    /**
     * Set the armor piece of the specified armor slot. The item can be null
     * @param item to set, if not null
     * @param armorSlot to set
     */
    public void setArmor(ItemStack item, int armorSlot);

    /**
     * Check if this being is capable of equipping the
     * {@link org.bukkit.inventory.ItemStack}. This is primarily used for
     * damage listeners and other logic that may be needed throughout KraftRPG
     * 
     * @param itemStack to check
     * @return true if the item is a valid item to equip
     */
    public boolean canEquipItem(ItemStack itemStack);

    /**
     * Returns (if available) the named
     * {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect}
     * 
     * @param name the name of the desired Effect
     * @return the named Effect if not null
     */
    public IEffect getEffect(String name);

    /**
     * Returns an unmodifiable set of Effects this Insentient being has
     * active.
     * 
     * @return an unmodifiable set of Effects this Insentient being has
     *         active.
     */
    public Set<IEffect> getEffects();

    /**
     * Adds the given Effect to this Insentient being. Added Effects will be
     * applied on the next tick so as to avoid
     * 
     * @param effect to apply to this Insentient
     */
    public void addEffect(IEffect effect);

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

    /**
     * Return the {@link com.afterkraft.kraftrpg.api.util.FixedPoint} this
     * being should grant as a reward for being killed by a
     * {@link com.afterkraft.kraftrpg.api.entity.Sentient} being.
     * 
     * @return the customized experience to grant to a killer
     */
    public FixedPoint getRewardExperience();

    /**
     * Set the rewarding experience this being will grant to its killer.
     * 
     * @param experience to grant
     */
    public void setRewardExperience(FixedPoint experience);

    /**
     * Sends a message to this being. A helper method to avoid having to cast
     * check the being or the entity this being belongs to to send server
     * messages.
     * 
     * @param message to be sent
     */
    public void sendMessage(String message);

    /**
     * Check if this insentient is ignoring messages from the specified
     * {@link com.afterkraft.kraftrpg.api.skills.ISkill}
     * 
     * @param skill that is possibly being ignored
     * @return true if this being does not wish to listen to the skill
     *         messages
     */
    public boolean isIgnoringSkill(ISkill skill);
}
