package com.afterkraft.kraftrpg.api.skills.arguments;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skills.SkillArgument;

public class ExplicitPlayerArgument extends SkillArgument {
    private final boolean defaultYou;

    private Player matchedPlayer;

    public ExplicitPlayerArgument(boolean required, boolean defaultYou) {
        super(required);
        this.defaultYou = defaultYou;
    }

    public Player getMatchedPlayer() {
        return matchedPlayer;
    }

    // --------------------------------------------------------------

    @Override
    public String getUsageString(boolean optional) {
        if (optional) {
            if (defaultYou) {
                return "[player=you]";
            }
            return "[player]";
        } else {
            return "<player>";
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public int matches(SkillCaster caster, String[] allArgs, int startPosition) {
        String arg = allArgs[startPosition];
        Player p = Bukkit.getPlayerExact(arg);
        if (p != null) {
            return 1;
        }
        return -1;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void parse(SkillCaster caster, String[] allArgs, int startPosition) {
        matchedPlayer = Bukkit.getPlayerExact(allArgs[startPosition]);
    }

    @Override
    public void skippedOptional(SkillCaster caster) {
        if (defaultYou) {
            matchedPlayer = (Player) caster.getEntity();
        } else {
            matchedPlayer = null;
        }
    }

    @Override
    public void clean() {
        matchedPlayer = null;
    }

    @Override
    public List<String> tabComplete(SkillCaster caster, String[] allArgs, int startPosition) {
        // TODO Auto-generated method stub
        return null;
    }

}
