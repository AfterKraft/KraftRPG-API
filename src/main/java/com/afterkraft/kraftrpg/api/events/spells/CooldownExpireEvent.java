package com.afterkraft.kraftrpg.api.events.spells;

import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.events.ChampionEvent;
import com.afterkraft.kraftrpg.api.spells.Spell;
import com.afterkraft.kraftrpg.api.spells.SpellArgument;

/**
 * @author gabizou
 */
public class CooldownExpireEvent extends ChampionEvent {

    private final Spell<? extends SpellArgument> spell;

    public CooldownExpireEvent(Champion champion, Spell<? extends SpellArgument> spell) {
        super(champion);
        this.spell = spell;
    }

    public Spell<? extends SpellArgument> getSpell() {
        return this.spell;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }

}
