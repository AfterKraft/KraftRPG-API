package com.afterkraft.kraftrpg.api.entity.roles;

import com.afterkraft.kraftrpg.api.entity.Champion;

public interface RoleRequirement {
    public boolean satisfied(Champion champion);

    public String getFailureMessage(Champion champion);
}
