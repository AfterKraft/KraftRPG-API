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
package com.afterkraft.kraftrpg.api.util;

import java.io.File;

/**
 * A generalized manager for configuration files used by all other Managers
 */
public interface ConfigManager {

    /**
     * Check for a specific configuration file. Utilized for SkillConfigManager
     * and more. This will check if the file exists, if it does not, it will be
     * pulled from the defaults provided by the implementation.
     *
     * @param config the file to check
     */
    public void checkForConfig(File config);

}
