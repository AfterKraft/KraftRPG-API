package com.afterkraft.kraftrpg.api.entity;

import java.util.Set;

import com.afterkraft.kraftrpg.api.entity.effects.EffectType;
import com.afterkraft.kraftrpg.api.entity.effects.IEffect;

/**
 * @author gabizou
 */
public interface Mage {

    public int getMana();

    public void setMana(int mana);

    public double getHealth();

    public void setHealth(double health);

    public void updateInventory();

    /**
     * Returns (if available) the named {@link com.afterkraft.kraftrpg.api.entity.effects.IEffect}
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
     * Adds the given Effect to this Champion. Added Effects will be applied on the next tick so as to avoid
     *
     * @param IEffect
     */
    public void addEffect(IEffect IEffect);

    public boolean hasEffect(String name);

    public boolean hasEffectType(EffectType type);

    public void removeEffect(IEffect IEffect);

    public void manualRemoveEffect(IEffect IEffect);

    public void clearEffects();

    public void manualClearEffects();

    /**
     *
     * @return true if this Champion is marked in combat
     */
    public boolean isInCombat();

    /**
     *
     */
    public void enterCombat(EnterCombatReason reason);

    /**
     *
     * @param reason the designated reason for leaving combat
     */
    public void leaveCombat(LeaveCombatReason reason);

}
