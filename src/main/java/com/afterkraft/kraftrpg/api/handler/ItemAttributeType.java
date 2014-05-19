package com.afterkraft.kraftrpg.api.handler;

public enum ItemAttributeType {
    // Note to maintainer: New additions need a corresponding line in KraftRPGPlugin
    GRANT_SKILL,
    BOOST_SKILL,
    ;

    private String attrName;

    private ItemAttributeType() {
        attrName = "kraftrpg." + name();
    }

    public String getAttributeName() {
        return attrName;
    }
}
