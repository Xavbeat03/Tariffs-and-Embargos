package com.github.Xavbeat03.Tariffs.Objects;

import java.util.UUID;


public class TariffObject {
    private UUID origin;
    private UUID target;
    private String target_type;
    private Integer value;

    public TariffObject(UUID originUUID, UUID targetUUID, String targetTypeString, Integer tariffValue){
        this.origin = originUUID;
        this.target = targetUUID;
        this.target_type = targetTypeString;
        this.value = tariffValue;
    }


    public UUID getOrigin() {
        return origin;
    }

    public void setOrigin(UUID origin) {
        this.origin = origin;
    }

    public UUID getTarget() {
        return target;
    }

    public void setTarget(UUID target) {
        this.target = target;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
