/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.entity.roles;

import com.afterkraft.kraftrpg.api.skills.ISkill;

public class RoleSkill implements Comparable<RoleSkill> {
    private ISkill skill;
    private int level;

    public RoleSkill(ISkill skill, int level) {
        this.skill = skill;
        this.level = level;
    }

    public boolean skillEquals(ISkill other) {
        if (skill == other) return true;

        return skill.equals(other);
    }

    public ISkill getSkill() {
        return skill;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((skill == null) ? 0 : skill.hashCode());
        result = prime * result + level;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RoleSkill other = (RoleSkill) obj;
        if (level != other.level) return false;
        if (skill == null) {
            if (other.skill != null) return false;
        } else if (!skill.equals(other.skill)) return false;
        return true;
    }

    // this - other
    @Override
    public int compareTo(RoleSkill other) {
        int levelDiff = this.level - other.level;
        if (levelDiff != 0) return levelDiff;

        // null is always greater
        if (skill == null) {
            return 1;
        } else if (other.skill == null) {
            return -1;
        }
        return skill.getName().compareTo(other.skill.getName());
    }
}
