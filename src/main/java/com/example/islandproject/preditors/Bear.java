package com.example.islandproject.preditors;

import com.example.islandproject.WildlifeType;
import com.example.islandproject.Predator;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.util.Map.entry;

public class Bear extends Predator {
    private static final WildlifeType type = WildlifeType.Bear;
    private static final String icon = "\uD83D\uDC3B";
    private static final double weight = 500;
    private static final int maxMovementDistance = 2;
    private static final double foodToSaturation = 80;
    private static final int kidsAmount = 1;

    private static final Map<WildlifeType, Integer> chanceToEat =
            ImmutableMap.ofEntries(
                    entry(WildlifeType.Wolf, 0),
                    entry(WildlifeType.Boa, 80),
                    entry(WildlifeType.Fox, 0),
                    entry(WildlifeType.Bear, 0),
                    entry(WildlifeType.Eagle, 0),
                    entry(WildlifeType.Horse, 40),
                    entry(WildlifeType.Deer, 80),
                    entry(WildlifeType.Rabbit, 80),
                    entry(WildlifeType.Mouse, 90),
                    entry(WildlifeType.Goat, 70),
                    entry(WildlifeType.Sheep, 70),
                    entry(WildlifeType.Boar, 50),
                    entry(WildlifeType.Buffalo, 20),
                    entry(WildlifeType.Duck, 10),
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
