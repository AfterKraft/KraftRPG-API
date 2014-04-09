package com.afterkraft.kraftrpg.api.entity;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.effects.RPGEffect;
import com.afterkraft.kraftrpg.api.entity.effects.RPGEffectType;
import com.afterkraft.kraftrpg.api.entity.effects.Expirable;
import com.afterkraft.kraftrpg.api.entity.effects.Periodic;
import com.afterkraft.kraftrpg.api.events.entity.effects.EffectAddEvent;
import com.afterkraft.kraftrpg.api.events.entity.effects.EffectRemoveEvent;

/**
 * Author: gabizou
 */
public abstract class RPGEntity {

    private final Map<String, RPGEffect> effects = new HashMap<String, RPGEffect>();
    protected final RPGPlugin plugin;
    private final UUID uuid;
    protected WeakReference<LivingEntity> lEntity;
    protected Map<String, Double> healthMap = new ConcurrentHashMap<String, Double>();
    protected final String name;

    public RPGEntity(RPGPlugin plugin, LivingEntity lEntity, String name) {
        this.plugin = plugin;
        this.lEntity = new WeakReference<LivingEntity>(lEntity);
        this.name = name != null ? name : lEntity.getType().name();
        this.uuid = lEntity.getUniqueId();
    }

    public String getName() {
        return name;
    }

    public final boolean isValid() {
        return lEntity.get() != null;
    }

    public final boolean isEntityValid() {
        return this.isValid() && this.getEntity().isValid();
    }

    /**
     * Returns the entity associated with this character if the entity has not
     * been garbage collected already (in which case, this RPGEntity will need
     * to be removed from the system.
     * @return the associated LivingEntity for this RPGEntity or null if the
     * LivingEntity no longer exists
     */
    public LivingEntity getEntity() {
        if (!isEntityValid()) {
            return null;
        }
        return lEntity.get();
    }

    /**
     * Attempts to find the effects from the given name
     *
     * @param name
     * @return the effects with the name - or null if not found
     */
    public RPGEffect getEffect(String name) {
        if (!this.isEntityValid()) {
            return null;
        }
        return effects.get(name.toLowerCase());
    }

    /**
     * get a Clone of all effects active on the hero
     *
     * @return
     */
    public Set<RPGEffect> getEffects() {
        if (!this.isEntityValid()) {
            return null;
        }
        return new HashSet<RPGEffect>(effects.values());
    }

    /**
     * Adds the effects onto the hero, and calls it's apply method initiating it's first tic.
     *
     * @param RPGEffect
     */
    public void addEffect(RPGEffect RPGEffect) {
        if (RPGEffect == null || !this.isEntityValid())
            return;

        EffectAddEvent event = new EffectAddEvent(this, RPGEffect);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        if (hasEffect(RPGEffect.getName())) {
            removeEffect(getEffect(RPGEffect.getName()));
        }

//        plugin.getEffectManager().manageEffect(this, RPGEffect);

        effects.put(RPGEffect.getName().toLowerCase(), RPGEffect);
//        RPGEffect.apply(this);
    }

    /**
     * Checks if the hero currently has the effects with the given name.
     *
     * @param name
     * @return boolean
     */
    public boolean hasEffect(String name) {
        if (!this.isEntityValid()) {
            return false;
        }
        return effects.containsKey(name.toLowerCase());
    }

    /**
     * Checks if the character has the effects type specified
     * @param type
     * @return true if the character has the effects type
     */
    public boolean hasEffectType(RPGEffectType type) {
        if (!this.isEntityValid()) {
            return false;
        }
        for (final RPGEffect RPGEffect : effects.values()) {
            if (RPGEffect.isType(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method can NOT be called from an iteration over the effects set
     *
     * @param RPGEffect
     */
    public void removeEffect(RPGEffect RPGEffect) {
        if (RPGEffect == null || !this.isEntityValid())
            return;

        EffectRemoveEvent event = new EffectRemoveEvent(this, RPGEffect);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        if ((RPGEffect instanceof Expirable) || (RPGEffect instanceof Periodic)) {
//            plugin.getEffectManager().queueForRemoval(this, RPGEffect);
        }
        RPGEffect.remove(this);
        effects.remove(RPGEffect.getName().toLowerCase());
    }


    public void manualRemoveEffect(RPGEffect RPGEffect) {
        if (RPGEffect != null && this.isEntityValid()) {
            if ((RPGEffect instanceof Expirable) || (RPGEffect instanceof Periodic)) {
//                plugin.getEffectManager().queueForRemoval(this, RPGEffect);
            }
            effects.remove(RPGEffect.getName().toLowerCase());
        }
    }

    /**                RPGCombatEffect
     * Iterates over the effects of this character and force removes them
     */
    public void manualClearEffects() {
        if (!this.isEntityValid()) {
            return;
        }
        for (final RPGEffect RPGEffect : this.getEffects()) {
            this.manualRemoveEffect(RPGEffect);
        }
    }

    /**
     * Iterates over the effects this character has and removes them
     */
    public void clearEffects() {
        if (!this.isEntityValid()) {
            return;
        }
        for (final RPGEffect RPGEffect : this.getEffects()) {
            this.removeEffect(RPGEffect);
        }
    }

    @Deprecated
    public boolean addMaxHealth(String key, int value) {
        return addMaxHealth(key, Math.ceil(value));
    }
    /**
     * Adds to the character's maximum health.  Maps the amount of health to add to a key.
     * This operation will fail if the character already has a health value with the specific key.
     * This operation will add health to the character's current health.
     *
     * @param key to give
     * @param value amount
     * @return true if the operation was successful
     */
    public boolean addMaxHealth(String key, double value) {
        if (!this.isEntityValid()) {
            return false;
        }
        // can't add maxHealth to dead players
        if (this.getEntity().getHealth() < 1) {
            return false;
        }
        if (healthMap.containsKey(key)) {
            return false;
        }
        healthMap.put(key, value);
        this.getEntity().setMaxHealth(this.getEntity().getMaxHealth() + value);
        this.getEntity().setHealth(value + this.getEntity().getHealth());
        return true;
    }

    /**
     * Thread-Safe
     * Removes a maximum health addition on the character.
     * This will also remove current health from the character, down to a minimum of 1.
     *
     * @param key to remove
     * @return true if health was removed.
     */
    public boolean removeMaxHealth(String key) {
        if (!this.isEntityValid()) {
            return false;
        }
        LivingEntity entity = this.getEntity();
        Double old = healthMap.remove(key);
        double currentHealth = entity.getHealth();
        if (old != null) {
            double newHealth = entity.getHealth() - old;
            if (currentHealth > 0 && newHealth < 1) {
                newHealth = 1;

            }
            if (this.getEntity().getHealth() != 0) {
                entity.setHealth(newHealth);
            }
            entity.setMaxHealth(entity.getMaxHealth() - old);
            return true;
        }
        return false;
    }

    /**
     * Thread-Safe
     * removes all max-health bonuses from the character.
     */
    public void clearHealthBonuses() {
        if (!this.isEntityValid()) {
            return;
        }
        double current = this.getEntity().getHealth();
        Iterator<Map.Entry<String, Double>> iter = healthMap.entrySet().iterator();
        int minus = 0;
        while (iter.hasNext()) {
            Double val = iter.next().getValue();
            iter.remove();
            current -= val;
            minus += val;
        }
        if (this.getEntity().getHealth() != 0) {
            this.getEntity().setHealth(current < 0 ? 0 : current);
        }
        this.getEntity().setMaxHealth(this.getEntity().getMaxHealth() - minus);
    }

    @Override
    public int hashCode() {
        return this.isEntityValid() ? this.uuid.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RPGEntity) && this.isEntityValid() &&
                ((RPGEntity) obj).isEntityValid() &&
                ((RPGEntity) obj).getEntity().getUniqueId().equals(this.getEntity().getUniqueId());
    }

}
