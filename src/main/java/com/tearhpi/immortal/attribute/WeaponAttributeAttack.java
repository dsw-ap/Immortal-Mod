package com.tearhpi.immortal.attribute;

public enum WeaponAttributeAttack {
    FIRE("fire",1),
    WATER("water",2),
    EARTH("earth",4),
    AIR("air",3),
    LIGHT("light",5),
    DARKNESS("darkness",6),
    NONE("none",0);
    private final String attributeName;
    private final int index;
    WeaponAttributeAttack(String attributeName,int index) {
        this.attributeName = attributeName;
        this.index = index;
    }
    public String getattributeName() {
        return this.attributeName;
    }
    public int getattributeInt() {
        return this.index;
    }
}
