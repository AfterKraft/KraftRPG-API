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
package com.afterkraft.kraftrpg.api.handler;

public enum ItemAttributeType {
    // Note to maintainer: New additions need a corresponding line in KraftRPGPlugin
    GRANT_SKILL,
    BOOST_SKILL, ;

    private String attrName;

    private ItemAttributeType() {
        attrName = "kraftrpg." + name();
    }

    public String getAttributeName() {
        return attrName;
    }
}
