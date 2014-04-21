package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;

/**
 * @author gabizou
 */
public abstract class ActiveSpell<T extends SpellArgument> extends Spell implements Active<T> {

    private String usage = "";

    public ActiveSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public final String getUsage() {
        return this.usage;
    }

    @Override
    public final void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public void onWarmUp(Champion champion) {

    }

    @Override
    public T parse(Champion champion, String[] args) {
        return null;
    }

    @Override
    public boolean useSpell(Champion champion, T argument) {
        return false;
    }

}
