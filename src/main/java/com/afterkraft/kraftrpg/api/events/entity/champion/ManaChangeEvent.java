package com.afterkraft.kraftrpg.api.events.entity.champion;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.events.ChampionEvent;

/**
 * @author gabizou
 */
public class ManaChangeEvent extends ChampionEvent {

    private final int fromMana;
    private final int toMana;
    private final ManaChangeReason reason;

    public ManaChangeEvent(Champion player, int fromMana, int toMana, ManaChangeReason reason) {
        super(player);
        this.fromMana = fromMana;
        this.toMana = toMana;
        this.reason = reason;
    }

    public int getFromMana() {
        return this.fromMana;
    }

    public int getToMana() {
        return this.toMana;
    }

    public ManaChangeReason getReason() {
        return this.reason;
    }

    public enum ManaChangeReason {
        SPELL_USAGE,
        SPELL_RESULT,
        MANA_REGAIN,
        OTHER
    }
}
