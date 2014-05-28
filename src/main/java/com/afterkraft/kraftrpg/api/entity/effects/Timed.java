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

import java.util.concurrent.Delayed;

/**
 * A utility interface to deal with effects that implement
 * {@link java.util.concurrent.Delayed} It should be noted that the API
 * provides the following to implement Timed:
 * <ul>
 * <li>{@link com.afterkraft.kraftrpg.api.entity.effects.Expirable}</li>
 * <li>{@link com.afterkraft.kraftrpg.api.entity.effects.Periodic}</li>
 * </ul>
 * and from the default implementations:
 * <ul>
 * <li>{@link com.afterkraft.kraftrpg.api.entity.effects.ExpirableEffect}</li>
 * <li>{@link com.afterkraft.kraftrpg.api.entity.effects.PeriodicEffect}</li>
 * <li>
 * {@link com.afterkraft.kraftrpg.api.entity.effects.PeriodicExpirableEffect}</li>
 * <li>{@link com.afterkraft.kraftrpg.api.entity.effects.PeriodicDamageEffect}
 * </li>
 * <li>
 * {@link com.afterkraft.kraftrpg.api.entity.effects.PeriodicHealingEffect}</li>
 * </ul>
 */
public interface Timed extends Delayed {

}
