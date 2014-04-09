package com.afterkraft.kraftrpg.api.spells;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.RPGPlayer;

/**
 * @author gabizou
 */
public abstract class TargettedSpell<T extends TargettedSpell.TargettedSpellArgument> extends Spell<T> {

    public TargettedSpell(RPGPlugin plugin, String name) {
        super(plugin, name);
    }

    public abstract class TargettedSpellArgument extends SpellArgument {

        protected String playerName;

        protected TargettedSpellArgument(String[] args) {
            super(args);
        }

        public abstract String getName();

        void setTargetPlayerName(String name) {
            this.playerName = name;
        }

        public String getTargetPlayerName() {
            return this.playerName;
        }

    }
}
