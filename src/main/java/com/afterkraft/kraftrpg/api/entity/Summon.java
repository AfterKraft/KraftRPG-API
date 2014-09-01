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
