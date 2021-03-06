/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.role.aspect;


import java.util.Optional;

import com.afterkraft.kraftrpg.api.role.RoleAspect;

/**
 * Represents a resource aspect associated with a resource
 *
 * @param <T> The type of resource number to represent
 */
public interface ResourceAspect<T extends Number> extends RoleAspect {

    /**
     * Gets the resource at level 1.
     *
     * @return The resource at level 1
     */
    T getBaseResource();

    /**
     * Gets the resource regeneration rate at level 1.
     *
     * @return The resource regeneration rate at level 1
     */
    T getBaseResourceRegeneration();

    /**
     * Gets the resource increase per level
     *
     * @return The resource increase per level
     */
    T getResourceIncreasePerLevel();

    /**
     * Gets the resource regeneration increase per level.
     *
     * @return The resource regeneration increase per level
     */
    T getResourceRegenerationIncreasePerLevel();

    /**
     * Gets the resource available at the desired level.
     *
     * @param level The level
     *
     * @return The resource at the desired level, if available
     */
    Optional<T> getResourceAtLevel(int level);


}
