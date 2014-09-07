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
package com.afterkraft.kraftrpg.api.entity;

/**
 * A summon LivingEntity that is attached to a {@link SkillCaster}.
 * Summons for all intents and purposes should be considered a proxy of a
 * LivingEntity. A Summon may also have a timed amount to live, after which it
 * will be removed from the World. A Summon should not be considered to persist
 * when a SkillCaster is no longer valid.
 */
public interface Summon extends Insentient {

    /**
     * Gets the time in milliseconds this summon has remaining to live in the
     * world.
     *
     * @return The time left remaining in the world
     */
    public long getTimeLeftAlive();

    /**
     * Gets the linked summoner that summoned this summon.
     *
     * @return The skillcaster that summoned this summon
     */
    public SkillCaster getSummoner();

    /**
     * Removes the summon from the world. This is a utility method primarily to
     * reset the summon and unlink it from any physical contact.
     */
    public void remove();

}
