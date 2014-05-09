package com.afterkraft.kraftrpg.api.entity;

import com.afterkraft.kraftrpg.api.Manager;

public interface CombatTracker extends Manager {

    public void enterCombat(Sentient target, EnterCombatReason reason);

    public void enterCombatWith(Sentient target, Sentient attacker, EnterCombatReason reason);

    public void leaveCombat(Sentient target, LeaveCombatReason reason);

    public void leaveCombatWith(Sentient target, Sentient attacker, LeaveCombatReason reason);

    public boolean isInCombat(Sentient sentient);

    public boolean isInCombatWith(Sentient target, Sentient potentialAttacker);
}
