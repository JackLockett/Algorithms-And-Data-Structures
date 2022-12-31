package com.company;

public record Plates(int plateDiameter, String plateType) {

    public int getPlateDiameter() {
        return plateDiameter;
    }

    public String getPlateType() {
        return plateType;
    }
}


