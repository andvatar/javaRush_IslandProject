package com.example.islandproject;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import static java.util.Map.entry;

public class IslandSettings {
    private final int islandWidth;
    private final int islandHeight;
    private final Map<WildlifeType, Integer> startAmount;

    public IslandSettings() {
        islandWidth = 3;
        islandHeight = 3;
        startAmount = ImmutableMap.ofEntries(
                entry(WildlifeType.Wolf, 4),
                entry(WildlifeType.Boa, 2),
                entry(WildlifeType.Fox, 4),
                entry(WildlifeType.Bear, 2),
                entry(WildlifeType.Eagle, 0),
                entry(WildlifeType.Horse, 4),
                entry(WildlifeType.Deer, 0),
                entry(WildlifeType.Rabbit, 4),
                entry(WildlifeType.Mouse, 0),
                entry(WildlifeType.Goat, 0),
                entry(WildlifeType.Sheep, 0),
                entry(WildlifeType.Boar, 0),
                entry(WildlifeType.Buffalo, 0),
                entry(WildlifeType.Duck, 5),
                // на моем острове гусеницы не умирают от голода и быстро размножаются, так что лучше их не плодить
                entry(WildlifeType.Caterpillar, 0),
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
