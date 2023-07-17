package com.example.islandproject.herbivores;

import com.example.islandproject.WildlifeType;
import com.example.islandproject.Herbivores;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.util.Map.entry;

public class Buffalo extends Herbivores {
    private static final WildlifeType type = WildlifeType.Buffalo;
    private static final String icon = "\uD83D\uDC03";
    private static final double weight = 700;
    private static final int maxMovementDistance = 3;
    private static final double foodToSaturation = 100;
    private static final int kidsAmount = 2;

    private static final Map<WildlifeType, Integer> chanceToEat =
            ImmutableMap.ofEntries(
                    entry(WildlifeType.Wolf, 0),
                    entry(WildlifeType.Boa, 0),
                    entry(WildlifeType.Fox, 0),
                    entry(WildlifeType.Bear, 0),
                    entry(WildlifeType.Eagle, 0),
                    entry(WildlifeType.Horse, 0),
                    entry(WildlifeType.Deer, 0),
                    entry(WildlifeType.Rabbit, 0),
                    entry(WildlifeType.Mouse, 0),
                    entry(WildlifeType.Goat, 0),
                    entry(WildlifeType.Sheep, 0),
                    entry(WildlifeType.Boar, 0),
                    entry(WildlifeType.Buffalo, 0),
                    entry(WildlifeType.Duck, 0),
                    entry(WildlifeType.Caterpillar, 0),
                    entry(WildlifeType.Plant, 100)
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
