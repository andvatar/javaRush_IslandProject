package com.example.islandproject;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import static java.util.Map.*;

public class Location {
    private final List<List<? extends Wildlife>> wildlife;
    private final List<Animal> animals;
    private final List<Plant> plants;

    public final static Map<WildlifeType, Integer> capacities = ImmutableMap.ofEntries(
            entry(WildlifeType.Wolf, 30),
            entry(WildlifeType.Boa, 30),
            entry(WildlifeType.Fox, 30),
            entry(WildlifeType.Bear, 5),
            entry(WildlifeType.Eagle, 20),
            entry(WildlifeType.Horse, 20),
            entry(WildlifeType.Deer, 20),
            entry(WildlifeType.Rabbit, 150),
            entry(WildlifeType.Mouse, 500),
            entry(WildlifeType.Goat, 140),
            entry(WildlifeType.Sheep, 140),
            entry(WildlifeType.Boar, 50),
            entry(WildlifeType.Buffalo, 10),
            entry(WildlifeType.Duck, 200),
            entry(WildlifeType.Caterpillar, 1000),
            entry(WildlifeType.Plant, 200)
    );

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        plants = Collections.synchronizedList(new ArrayList<>());
                //new CopyOnWriteArrayList<>();
        animals = Collections.synchronizedList(new ArrayList<>());
                //new CopyOnWriteArrayList<>();
        wildlife = List.of(animals, plants);
    }

    // животные в этой локации начинают питаться и размножаться
    public void startActivity() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Void>> taskList = new ArrayList<>(); //= getAnimals().
        for (Animal animal:getAnimals()) {
            taskList.add(() -> {
                animal.feed();
                animal.reproduce();
                return null;
            });
        }

        try {
            executorService.invokeAll(taskList);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public boolean checkCapacity(WildlifeType type) {
        return wildlife.stream().flatMap(Collection::stream).filter(w -> w.getType() == type).count() < capacities.get(type);
    }

    public void removeDeadWildlife() {
        animals.removeIf(animal -> !animal.isAlive());
        plants.removeIf(plant -> !plant.isAlive());
    }

    public List<Wildlife> getWildlife() {
        return wildlife.stream().flatMap(Collection::stream).map(wildlife -> (Wildlife)wildlife).toList();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Location{" +
                "wildlife=" + wildlife.stream().flatMap(Collection::stream).collect(Collectors.groupingBy(Wildlife::getType, Collectors.counting())) +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}