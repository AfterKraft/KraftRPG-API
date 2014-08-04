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
package com.afterkraft.kraftrpg.api.effects;

import java.util.concurrent.Delayed;

import com.afterkraft.kraftrpg.api.effects.common.DamagingEffect;
import com.afterkraft.kraftrpg.api.effects.common.HealingEffect;

/**
 * A utility interface to deal with effects that implement
 * {@link java.util.concurrent.Delayed} It should be noted that the API
 * provides the following to implement Timed:
 * <ul>
 * <li>{@link Expirable}</li>
 * <li>{@link Periodic}</li>
 * </ul>
 * and from the default implementations:
 * <ul>
 * <li>{@link ExpirableEffect}</li>
 * <li>{@link PeriodicEffect}</li>
 * <li>{@link PeriodicExpirableEffect}</li>
 * <li>{@link DamagingEffect}</li>
 * <li>{@link HealingEffect}</li>
 * </ul>
 */
public interface Timed extends Delayed {

}
