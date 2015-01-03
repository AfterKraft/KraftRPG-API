/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.common.skills.common;

import java.util.Map;

import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;

/**
 * Represents a bundle of permisson nodes that are granted as though they are skills. This has uses
 * for granting/denying permissions from external plugins.
 */
public interface Permissible extends ISkill {

    /**
     * Gets a copy of the current permissions attached to this permissible skill.
     *
     * @return A copy of the permission mappings
     */
    Map<String, Boolean> getPermissions();

    /**
     * Set this permission skill to apply the specified permission mapping.
     *
     * @param permissions to apply
     */
    void setPermissions(Map<String, Boolean> permissions);

    /**
     * Try to learn this permission skill's permission node along with any other permissions related
     * to this Permissible skill.
     *
     * @param being to learn this skill
     */
    void tryLearning(Sentient being);

    /**
     * Try to unlearn this permission skill's permission node along with any other permissions
     * related to this Permissible skill.
     *
     * @param being to unlearn
     */
    void tryUnlearning(Sentient being);
}
