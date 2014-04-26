package com.afterkraft.kraftrpg.api.entity.effects;

import java.lang.ref.WeakReference;
import java.util.Map;

import org.bukkit.entity.LivingEntity;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.LeaveCombatReason;

/**
 * @author gabizou
 */
public interface Combat extends IEffect {

    public boolean isInCombat();

    public boolean isInCombatWith(LivingEntity target);

    public void enterCombatWith(LivingEntity target, EnterCombatReason reason);

    public void leaveCombatWith(Champion champion, LivingEntity target, LeaveCombatReason reason);

    public void leaveCombatFromDeath(Champion champion);

    public void leaveCombatFromLogout(Champion champion);

    public void leaveCombatFromSuicide(Champion champion);

    public long getTimeLeft();

    public LivingEntity getLastCombatant();

    public Map<WeakReference<LivingEntity>, EnterCombatReason> getCombatants();

    public void clearCombatants();

    public void resetTimes();

}
