package com.afterkraft.kraftrpg.api.entity;

import java.util.Set;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.effects.Effect;
import com.afterkraft.kraftrpg.api.entity.effects.EffectType;

/**
 * @author gabizou
 */
public interface IEntity {

    public String getName();

    public boolean isValid();

    public boolean isEntityValid();

    public LivingEntity getEntity();

    public void setEntity(LivingEntity entity);

    public Effect getEffect(String name);

    public Set<Effect> getEffects();

    public void addEffect(Effect effect);

    public boolean hasEffect(String name);

    public boolean hasEffectType(EffectType type);

    public void removeEffect(Effect effect);

    public void manualRemoveEffect(Effect effect);

    public void clearEffects();

    public void manualClearEffects();

    public boolean addMaxHealth(String key, double value);

    public boolean removeMaxHealth(String key);

    public void clearHealthBonuses();

}
