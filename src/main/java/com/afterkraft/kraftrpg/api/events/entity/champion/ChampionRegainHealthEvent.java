package com.afterkraft.kraftrpg.api.events.entity.champion;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.events.entity.EntityRegainHealthEvent;
import com.afterkraft.kraftrpg.api.spells.ISpell;

/**
 * @author gabizou
 */
public class ChampionRegainHealthEvent extends EntityRegainHealthEvent {


    public ChampionRegainHealthEvent(Champion beneficiary, double healAmount, ISpell spell) {
        super(beneficiary, healAmount, spell);
    }

    public ChampionRegainHealthEvent(Champion beneficiary, double healAmount, ISpell spell, IEntity healer) {
        super(beneficiary, healAmount, spell, healer);
    }

    public Champion getChampion() {
        return (Champion) super.getEntity();
    }

    @Override
    public Champion getHealer() {
        return (Champion) super.getHealer();
    }
}
