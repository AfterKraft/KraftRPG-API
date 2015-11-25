package com.afterkraft.kraftrpg.api.event.skill;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.event.Event;

import com.afterkraft.kraftrpg.api.RpgPlugin;

public class RegisterSkillEvent implements Event {

    private final RpgPlugin plugin;

    public RegisterSkillEvent(RpgPlugin plugin) {
        this.plugin = checkNotNull(plugin);
    }

}
