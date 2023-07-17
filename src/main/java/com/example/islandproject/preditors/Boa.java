package com.example.islandproject.preditors;

import com.example.islandproject.WildlifeType;
import com.example.islandproject.Predator;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.util.Map.entry;

public class Boa extends Predator {
    private static final WildlifeType type = WildlifeType.Boa;
    private static final String icon = "\uD83D\uDC0D";
    private static final double weight = 15;
    private static final int maxMovementDistance = 1;
    private static final double foodToSaturation = 3;
    private static final int kidsAmount = 3;

    private static final Map<WildlifeType, Integer> chanceToEat =
            ImmutableMap.ofEntries(
                    entry(WildlifeType.Wolf, 0),
                    entry(WildlifeType.Boa, 0),
                    entry(WildlifeType.Fox, 15),
                    entry(WildlifeType.Bear, 0),
                    entry(WildlifeType.Eagle, 0),
                    entry(WildlifeType.Horse, 0),
                    entry(WildlifeType.Deer, 0),
                    entry(WildlifeType.Rabbit, 20),
                    entry(WildlifeType.Mouse, 40),
                    entry(WildlifeType.Goat, 0),
                    entry(WildlifeType.Sheep, 0),
                    entry(WildlifeType.Boar, 0),
                    entry(WildlifeType.Buffalo, 0),
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
