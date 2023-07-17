package com.example.islandproject.preditors;

import com.example.islandproject.WildlifeType;
import com.example.islandproject.Predator;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.util.Map.entry;

public class Wolf extends Predator {

    private static final WildlifeType type = WildlifeType.Wolf;
    private static final String icon = "\uD83D\uDC3A";
    private static final double weight = 50;
    private static final int maxMovementDistance = 3;
    private static final double foodToSaturation = 8;
    private static final int kidsAmount = 2;

    private static final Map<WildlifeType, Integer> chanceToEat =
            ImmutableMap.ofEntries(
                    entry(WildlifeType.Wolf, 0),
                    entry(WildlifeType.Boa, 0),
                    entry(WildlifeType.Fox, 0),
                    entry(WildlifeType.Bear, 0),
                    entry(WildlifeType.Eagle, 0),
                    entry(WildlifeType.Horse, 10),
                    entry(WildlifeType.Deer, 15),
                    entry(WildlifeType.Rabbit, 60),
                    entry(WildlifeType.Mouse, 80),
                    entry(WildlifeType.Goat, 60),
                    entry(WildlifeType.Sheep, 70),
                    entry(WildlifeType.Boar, 15),
                    entry(WildlifeType.Buffalo, 10),
                    entry(WildlifeType.Duck, 40),
                    entry(WildlifeType.Caterpillar, 0),
                    entry(WildlifeType.Plant, 0)
            );

    @Override
    public int getMaxMovementDistance() {
        return maxMovementDistance;
    }

    @Override
    public double getFoodToSaturation() {
        return foodToSaturation;
    }

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
    public double getChanceToEat(WildlifeType foodType) {
        return chanceToEat.getOrDefault(foodType, 0) / 100d;
    }

    @Override
    public int getKidsAmount() {
        return kidsAmount;
    }
}
