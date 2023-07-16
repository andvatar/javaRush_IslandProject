package com.example.islandproject;

import com.example.islandproject.herbivores.*;
import com.example.islandproject.preditors.*;

public class WildlifeFactory {
    public Wildlife createWildlife(WildlifeType type) {
        return switch(type) {
            case Boar -> new Boar();
            case Buffalo -> new Buffalo();
            case Caterpillar -> new Caterpillar();
            case Deer -> new Deer();
            case Duck -> new Duck();
            case Goat -> new Goat();
            case Horse -> new Horse();
            case Mouse -> new Mouse();
            case Rabbit -> new Rabbit();
            case Sheep -> new Sheep();
            case Bear -> new Bear();
            case Boa -> new Boa();
            case Eagle -> new Eagle();
            case Fox -> new Fox();
            case Wolf -> new Wolf();
            case Plant -> new Plant();
        };
    }
}
