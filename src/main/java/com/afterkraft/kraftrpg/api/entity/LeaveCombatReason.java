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


public enum LeaveCombatReason {
    /**
     * Normal death by fall, fire, suffocation etc.
     */
    DEATH,
    /**
     * Death by suicide, defined by the circumstance of the death.
     */
    SUICIDE,
    /**
     * Leaving combat due to an error on the server or KraftRPG
     */
    ERROR,
    /**
     * Due to an expiration of the combat timer with another entity
     */
    TIMED,
    /**
     * When the champion logs off the server
     */
    LOGOUT,
    /**
     * When the champion is kicked from the server
     */
    KICK,
    /**
     * When the target has died
     */
    TARGET_DEATH,
    /**
     * When the target has logged off
     */
    TARGET_LOGOUT,
    /**
     * Customized reason, possibly third party plugins.
     */
    CUSTOM,

}
