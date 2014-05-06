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
package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

public interface Expirable extends IEffect, Timed {

    /**
     * Get the message that should be sent to players when this effect is
     * applied
     *
     * @return the message when this effect is applied
     */
    public String getApplyText();

    /**
     * Set the message that is sent to players when this effect is applied
     *
     * @param text that will show up when this effect is applied
     */
    public void setApplyText(String text);

    /**
     * Get the message that should be sent to players when this effect expires
     *
     * @return the message when this effect expires
     */
    public String getExpireText();

    /**
     * Set the message that is sent to players when this effect expires
     *
     * @param text that will show up when this effect expires
     */
    public void setExpireText(String text);

    /**
     * Fetch the duration of this Expirable Effect.
     *
     * @return the duration in milliseconds
     */
    public long getDuration();

    /**
     * Fetch the estimated Expire time in milliseconds
     *
     * @return the expire time in milliseconds.
     */
    public long getExpiry();

    /**
     * Fetch the estimated remaining time of this Effect
     *
     * @return the estimated remaining time in milliseconds
     */
    public long getRemainingTime();

    /**
     * Check if this Effect is expired
     *
     * @return true if this Effect is expired
     */
    public boolean isExpired();

    /**
     * Manually expire this Effect. This will set the expire time to the current
     * System time.
     */
    public void expire();

    /**
     * Get the {@link com.afterkraft.kraftrpg.api.entity.SkillCaster} applying
     * this expirable effect.
     *
     * @return the SkillCaster applying this effect
     */
    public SkillCaster getApplier();

    public void setApplier(SkillCaster caster);
}
