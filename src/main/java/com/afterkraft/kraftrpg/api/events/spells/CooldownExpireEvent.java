package com.afterkraft.kraftrpg.api.events.spells;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.events.ChampionEvent;
import com.afterkraft.kraftrpg.api.spells.ISpell;
import com.afterkraft.kraftrpg.api.spells.SpellArgument;

/**
 * @author gabizou
 */
public class CooldownExpireEvent extends ChampionEvent {

    private final ISpell spell;

    public CooldownExpireEvent(Champion champion, ISpell spell) {
        super(champion);
        this.spell = spell;
    }

    public ISpell getSpell() {
        return this.spell;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }

}
