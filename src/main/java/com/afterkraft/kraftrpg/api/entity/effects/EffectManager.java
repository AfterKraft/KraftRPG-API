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

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Insentient;

/**
 * Basic manager for applying, ticking, and removing effects from
 * {@link com.afterkraft.kraftrpg.api.entity.Insentient} It can be used to
 * apply new effects via <code>
 *     {@link #manageEffect(com.afterkraft.kraftrpg.api.entity.Insentient, Timed)}
 * </code> and remove current effects via <code>
 *     {@link #queueRemoval(com.afterkraft.kraftrpg.api.entity.Insentient, Timed)}
 * </code>
 * 
 */
public interface EffectManager extends Manager {

    /**
     * Applies the Timed effect to the Insentient being. Depending on the
     * status of the Insentient being and the effect, the effect may not be
     * applied immediately.
     * 
     * @param being to apply the timed effect on
     * @param effect to apply
     */
    public void manageEffect(Insentient being, Timed effect);

    /**
     * 
     * @param being
     * @param effect
     */
    public void queueRemoval(Insentient being, Timed effect);
}
