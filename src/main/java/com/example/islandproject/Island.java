package com.example.islandproject;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Island{
    private final Location[][] locations;
    private final WildlifeFactory wildlifeFactory;

    public Island(IslandSettings settings) {
        wildlifeFactory = new WildlifeFactory();
        locations = new Location[settings.getIslandWidth()][settings.getIslandHeight()];
        for (int i = 0; i < settings.getIslandWidth(); i++) {
            for (int j = 0; j < settings.getIslandHeight(); j++) {
                locations[i][j] = new Location(i, j);
            }
        }
        // заселяем остров
        for (var startAmount: settings.getStartAmount().entrySet()) {
            for (int i = 0; i < startAmount.getValue(); i++) {
                Wildlife wildlife = wildlifeFactory.createWildlife(startAmount.getKey());
                try {
                    Location startLocation = getStartLocation(wildlife.getType());
                    wildlife.setStartLocation(startLocation);
                }
                catch(RuntimeException e) {
                    break;
                }
            }
        }
    }

    public void startIteration() {
        // животные не перемещались в этой итерации
        getAllAnimals().stream().parallel().forEach(animal -> animal.moved = false);
        growPlants();
        // животные жрут (многопоточно, максимум 10 потоков)
        // ждем завершения всех потоков прежде чем перейти к следующему шагу
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(getWidth()*getHeight(), 10));
        List<Callable<Void>> taskList = new ArrayList<>();
        for (Location location:getAllLocations()) {
            taskList.add(() -> {
                location.startFeeding();
                return null;
            });
        }
        try {
            executorService.invokeAll(taskList);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // в конце каждого цикла животное теряет 20 хп
        getAllAnimals().stream().parallel().forEach(Animal::starve);
        // мертвые животные и растения исчезают с острова
        getAllLocations().stream().parallel().forEach(Location::removeDeadWildlife);
        // выжившие животные мигрируют
        // миграция не параллельная, так как я не придумал как это сделать
        getAllAnimals().stream().filter(animal -> !animal.moved).forEach(animal -> animal.migrate(this));
        System.out.println(this);
    }

    public boolean isAliveAnimals() {
        return getAllAnimals().size() > 0;
    }

    private void growPlants() {
        for (Location location : Arrays.stream(locations).flatMap(Arrays::stream).filter(location -> location.checkCapacity(WildlifeType.Plant)).toList()) {
            for (int i = 0; i < 20; i++) {
                wildlifeFactory.createWildlife(WildlifeType.Plant).setStartLocation(location);
                if(!location.checkCapacity(WildlifeType.Plant)) {
                    break;
                }
            }
        }
    }

    private List<Animal> getAllAnimals() {
        return getAllLocations().stream().flatMap(location -> location.getAnimals().stream()).toList();
    }

    private List<Wildlife> getAllWildlife() {
        return getAllLocations().stream().flatMap(location -> location.getWildlife().stream()).toList();
    }

    private List<Location> getAllLocations() {
        return Arrays.stream(locations).flatMap(Arrays::stream).toList();
    }

    private Location getStartLocation(WildlifeType wildlifeType) {
        List<Location> locationList =Arrays
                .stream(locations)
                .flatMap(Arrays::stream)
                .filter(location -> location.checkCapacity(wildlifeType))
                .toList();
        if(locationList.size() == 0) {
            throw new RuntimeException("Location not found");
        }
        return locationList.get(new Random().nextInt(locationList.size()));
    }

    public Location getLocation(int x, int y) {
        return locations[x][y];
    }

    public int getWidth() {
        return locations.length;
    }

    public int getHeight() {
        return locations[0].length;
    }

    @Override
    public String toString() {
        return "Island{" +
                "locations=\n"
                    + Arrays.stream(locations).flatMap(Arrays::stream).map(Location::toString).map(l -> l + "\n").toList() +
                '}';
    }
}
