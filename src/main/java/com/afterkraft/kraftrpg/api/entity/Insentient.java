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
package com.afterkraft.kraftrpg.api.entity;

import javax.annotation.Nullable;
import java.util.Set;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.potion.PotionEffectType;

import com.google.common.base.Optional;

import com.afterkraft.kraftrpg.api.effects.EffectType;
import com.afterkraft.kraftrpg.api.effects.IEffect;
import com.afterkraft.kraftrpg.api.entity.resource.Resource;
import com.afterkraft.kraftrpg.api.listeners.DamageWrapper;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * Represents an Insentient being that allows retreival of the being's status and handles the basic
 * {@link com.afterkraft.kraftrpg.api.effects.IEffect}.  It is important to note that an Insentient,
 * and it's subclasses, may not always be attached to a {@link Living}. This
 * is why these methods are provided to assure that Effects and Skills may still function and apply
 * themselves to Insentient beings.  It is advisable that the following method may return null:
 * <code> {@link #getEntity()} </code> and therefor any common information retrieval should be
 * performed using the supplied methods instead of assuming the LivingEntity methods.
 */
public interface Insentient extends IEntity {

    /**
     * Attempts to fetch the attached LivingEntity of this Insentient Being,
     * unless a Living is not attached, to which this will return null.
     *
     * @return the attached LivingEntity, if there is no LivingEntity, then null.
     */
    @Override
    Optional<? extends Living> getEntity();

    /**
     * Gets the container of the resource desired. A resource wraps the
     * values
     *
     * @param clazz The class of the resource needing to be gotten
     * @param <T> The type of resource
     * @return
     */
    <T extends Resource> Optional<T> getResource(Class<T> clazz);

    /**
     * Get the current mana this being has.
     *
     * @return current mana
     */
    int getMana();

    /**
     * Set the total mana of the being.
     *
     * @param mana this being should have.
     *
     * @throws IllegalArgumentException If the mana is negative
     */
    void setMana(int mana);

    /**
     * Gets the current max mana
     *
     * @return the max mana for this being
     */
    int getMaxMana();

    /**
     * Sets the current maximum mana for this being.
     *
     * @param mana as the maximum
     *
     * @throws IllegalArgumentException If the new max mana is negative or zero
     */
    void setMaxMana(int mana);

    /**
     * Return the health of the being.
     *
     * @return health
     */
    double getHealth();

    /**
     * Attempts to set the health of the being. If the being is dead, the health will not be
     * updated.
     *
     * @param health to which the being should have.
     *
     * @throws IllegalArgumentException If health is negative
     */
    void setHealth(double health);

    /**
     * Return the max health for this being.
     *
     * @return maximum health
     */
    double getMaxHealth();

    /**
     * Add a fixed health bonus that does not get reset when {@link #recalculateMaxHealth()} is
     * called. The health bonus, unless provided specifically by KraftRPG, does not survive between
     * reloads, restarts, relogging and other times when the entity is stored and loaded back into
     * memory.
     *
     * @param key   id based on string to apply the max health
     * @param value of health to add as the maximum
     *
     * @return true if successful, if the key did not exist before.
     * @throws IllegalArgumentException If the key is null or empty
     */
    boolean addMaxHealth(String key, double value);

    /**
     * Removes an additional health modifier from the calculations for the {@link
     * Living#getMaxHealth()}. Removing KraftRPG specific mappings may have
     * unknown side-effects.
     *
     * @param key linking to the additional health bonus for this being
     *
     * @return true if successful, false if the mapping didn't exist.
     * @throws IllegalArgumentException If the key is null
     */
    boolean removeMaxHealth(String key);

    /**
     * Forcefully recalculates the total max health the linked {@link Living}
     * should have and applies it. This will also apply the current percentage of total health the
     * entity has compared to their current max health.
     *
     * @return the newly recalculated max health
     */
    double recalculateMaxHealth();

    /**
     * Attempts to heal the {@link Living} the defined amount of health.
     * This will not allow healing past the current max health
     *
     * @param amount to heal
     */
    void heal(double amount);

    /**
     * Clears out all health bonuses to the max health except the necessary provided by the default
     * implementation of KraftRPG.
     */
    void clearHealthBonuses();

    Optional<DamageWrapper> getDamageWrapper();

    void setDamageWrapper(@Nullable DamageWrapper wrapper);

    /**
     * Check if this being is dead
     *
     * @return true if dead
     */
    boolean isDead();

    /**
     * A helper method to get the internal no damage ticks for the represented LivingEntity.
     *
     * @return amount of no damage ticks
     */
    int getNoDamageTicks();

    /**
     * Assuming this being is affected by stamina, returns the current stamina of the being
     *
     * @return the stamina of this being
     */
    int getStamina();

    /**
     * Sets the stamina for the linked Insentient being. Stamina is a resource that some skills may
     * use and affects how a Player walks in the world.
     *
     * @param stamina The stamina to be set
     */
    void setStamina(int stamina);

    /**
     * Gets the maximum stamina for this being. Stamina is a resource that some skills may use and
     * affects how fast a Player walks.
     *
     * @return The maximum stamina
     */
    int getMaxStamina();

    /**
     * Modifies the current stamina of the being. Stamina can be negative
     *
     * @param staminaDiff to add to the current mana of the being
     */
    void modifyStamina(int staminaDiff);

    /**
     * A cleaning method provided to ensure possible connected client's view of an inventory is
     * properly refreshed, and not erroneously represented.
     */
    void updateInventory();

    /**
     * Assumes this being has a functional hand, this will return the {@link
     * ItemStack} in said hand.
     *
     * @return the ItemStack in the hand of the being.
     */
    Optional<ItemStack> getItemInHand();

    /**
     * Returns a proper {@link Inventory} that belongs to this being. It can be
     * modified and queried without exception.
     *
     * @return the inventory belonging to this being.
     */
    Inventory getInventory();

    /**
     * Provided as a utility method to get a copy of the being's armor. Implementations may vary,
     * but this should be assured to follow the index of:
     * <ol><li>0 - Boots</li><li>1 - Leggings</li><li>2 - Chestplate</li><li>3 - Helmet</li></ol>.
     *
     * <p>Any customized armor pieces not covered by this array should be handled on a case by
     * case basis.</p>
     *
     * @return The copy of the ItemStack list of the armor for this being
     */
    ItemStack[] getArmor();

    /**
     * Set the armor piece of the specified armor slot. The item can be null
     *
     * @param item      to set, if not null
     * @param armorSlot to set
     */
    void setArmor(ItemStack item, int armorSlot);

    /**
     * Check if this being is capable of equipping the {@link ItemStack}. This
     * is primarily used for damage listeners and other logic that may be needed throughout
     * KraftRPG
     *
     * @param itemStack to check
     *
     * @return true if the item is a valid item to equip
     */
    boolean canEquipItem(ItemStack itemStack);

    /**
     * Returns (if available) the named {@link com.afterkraft.kraftrpg.api.effects.IEffect}
     *
     * @param name the name of the desired Effect
     *
     * @return the named Effect if not null
     */
    Optional<IEffect> getEffect(String name);

    /**
     * Returns an unmodifiable set of Effects this Insentient being has active.
     *
     * @return an unmodifiable set of Effects this Insentient being has active.
     */
    Set<IEffect> getEffects();

    /**
     * Adds the given Effect to this Insentient being. Added Effects will be applied on the next
     * tick so as to avoid
     *
     * @param effect to apply to this Insentient
     *
     * @throws IllegalArgumentException If the effect is null
     */
    void addEffect(IEffect effect);

    /**
     * Add the {@link PotionEffect} to this Insentient being.  This method is
     * provided with the assurance that the entity would not have a {@link
     * java.util.ConcurrentModificationException} caused by multiple sources.
     *
     * @param potion the effect to be applied
     *
     * @throws IllegalArgumentException If the potion effect is null
     */
    void addPotionEffect(PotionEffect potion);

    /**
     * Remove the {@link PotionEffectType} from this Insentient.  This method is
     * provided with the assurance that the entity would not have a {@link
     * java.util.ConcurrentModificationException} caused by multiple sources.
     *
     * @param type of PotionEffect to remove
     *
     * @throws IllegalArgumentException If the potioneffect type is null
     */
    void removePotionEffect(PotionEffectType type);

    /**
     * Checks if this being has a specific PotionEffectType
     *
     * @param type to check
     *
     * @return true if the being has the queried type of potion effect
     * @throws IllegalArgumentException If the potion effect type is null
     */
    boolean hasPotionEffect(PotionEffectType type);

    /**
     * Check if this Insentient being has an {@link com.afterkraft.kraftrpg.api.effects.IEffect}
     * with the given name.
     *
     * @param name of the effect
     *
     * @return true if there is an active effect by the queried name
     * @throws IllegalArgumentException If the name is null or empty
     */
    boolean hasEffect(String name);

    /**
     * Check if this being has an effect of the given {@link com.afterkraft.kraftrpg.api.effects.EffectType}.
     *
     * @param type of effect
     *
     * @return true if this being has effect of the queried type
     * @throws IllegalArgumentException If the type is null
     */
    boolean hasEffectType(EffectType type);

    /**
     * Safely removes the {@link IEffect} from this being. This will call {@link
     * IEffect#remove(Insentient)} as effects may perform various actions accordingly.
     *
     * @param effect to be removed.
     *
     * @throws IllegalArgumentException If the effect is null
     */
    void removeEffect(IEffect effect);

    /**
     * Unsafely removes the IEffect from this being. It will not call {@link
     * IEffect#remove(Insentient)} and may leave behind some unintended {@link
     * PotionEffect}s on the being.
     *
     * @param effect being removed.
     *
     * @throws IllegalArgumentException If the effect is null
     */
    void manualRemoveEffect(IEffect effect);

    /**
     * Safely removes all active effects on this being.
     */
    void clearEffects();

    /**
     * Unsafely removes all active effects without calling {@link IEffect#remove(Insentient)}
     */
    void manualClearEffects();

    /**
     * Return the {@link com.afterkraft.kraftrpg.api.util.FixedPoint} this being should grant as a
     * reward for being killed by a {@link com.afterkraft.kraftrpg.api.entity.Sentient} being.
     *
     * @return the customized experience to grant to a killer
     */
    FixedPoint getRewardExperience();

    /**
     * Set the rewarding experience this being will grant to its killer.
     *
     * @param experience to grant
     */
    void setRewardExperience(FixedPoint experience);

    /**
     * Sends a message to this being. A helper method to avoid having to cast check the being or the
     * entity this being belongs to to send server messages.
     *
     * @param message to be sent
     */
    void sendMessage(String message);

    void sendMessage(String message, Object... args);

    /**
     * Check if this insentient is ignoring messages from the specified {@link
     * com.afterkraft.kraftrpg.api.skills.ISkill}
     *
     * @param skill that is possibly being ignored
     *
     * @return true if this being does not wish to listen to the skill messages
     */
    boolean isIgnoringSkill(ISkill skill);
}
