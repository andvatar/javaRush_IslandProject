package com.example.islandproject;

public class Plant extends Wildlife {

    private static final WildlifeType type = WildlifeType.Plant;
    private static final String icon = "\uD83D\uDC17";
    private static final double weight = 1;

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public WildlifeType getType() {
        return type;
    }

    @Override
    public void setStartLocation(Location location) {
        location.addPlant(this);
        this.location = location;
    }

    // от растения можно отгрызть кусок и оно останется живым
    @Override
    public double beEaten(double lackOfFood) {
        double eatenFood;
        if(lackOfFood > weight*hp/100) {
            eatenFood = weight*hp/100;
            hp = 0;
        }
        else {
            eatenFood = lackOfFood;
            hp -= (weight-eatenFood)/weight * 100;
        }
        return eatenFood;
    }
}
