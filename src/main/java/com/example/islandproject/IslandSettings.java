package com.example.islandproject;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import static java.util.Map.entry;

public class IslandSettings {
    private int islandWidth;
    private int islandHeight;
    private Map<WildlifeType, Integer> startAmount;

    public IslandSettings() {
        islandWidth = 3;
        islandHeight = 3;
        startAmount = ImmutableMap.ofEntries(
                entry(WildlifeType.Wolf, 2),
                entry(WildlifeType.Boa, 2),
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
                entry(WildlifeType.Duck, 5),
                entry(WildlifeType.Caterpillar, 20),
                entry(WildlifeType.Plant, 100)
        );
    }

    public int getIslandWidth() {
        return islandWidth;
    }

    public int getIslandHeight() {
        return islandHeight;
    }

    public Map<WildlifeType, Integer> getStartAmount() {
        return startAmount;
    }
}
