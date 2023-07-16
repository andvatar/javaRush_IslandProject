package com.example.islandproject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Wildlife {

    // миграция из одной локации в другую
    public void migrate(Island island) {
        move(findNextLocation(findPossibleLocations(island)));
    }

    public void feed() {

        List<Wildlife> foodList = location
                .getWildlife()
                .stream()
                .filter(wildlife -> this.getChanceToEat(wildlife.getType()) > 0 && wildlife != this)
                .toList();

        for (var food:foodList) {
            if(hp < 100) {
                if (lockFoodAndEater(food)) {
                    try {
                        if (ThreadLocalRandom.current().nextInt(100) > getChanceToEat(food.getType())) {
                            eat(food);
                        }
                    } finally {
                        lock.unlock();
                        food.lock.unlock();
                    }
                }
            }
            else {
                break;
            }
        }
    }

    private void eat(Wildlife food) {
        double foodAmount = food.beEaten(getLackOfFood());
        double plusHP = (foodAmount/this.getFoodToSaturation()) * this.hp * 0.2;
        double resultHP = this.hp + plusHP;
        if(resultHP > 100) {
            this.hp = 100;
        }
        else {
            this.hp = resultHP;
        }
    }

    // возвращает сколько кг съели (минимум из веса животного и оставшейся дневной потребности в еде)
    @Override
    public double beEaten(double lackOfFood) {
        double eatenFood = Math.min(lackOfFood, this.getWeight());
        hp = 0;
        return eatenFood;
    }

    // сколько осталось съесть до насыщения
    private double getLackOfFood() {
        return this.getFoodToSaturation()*hp/100;
    }

    // блокируем того кто есть и того кого едять
    private boolean lockFoodAndEater(Wildlife food) {
        boolean eaterLocked = false;
        boolean foodLocked = false;

        try {
            eaterLocked = lock.tryLock();
            foodLocked = food.lock.tryLock();
        }
        finally {
            if(!(eaterLocked && foodLocked)) {
                if(eaterLocked) {
                    lock.unlock();
                }
                if(foodLocked) {
                    food.lock.unlock();
                }
            }
        }
        return eaterLocked && foodLocked;
    }

    // в конце цикла животные теряют 20 хп
    public void starve() {
        loseHP(20);
    }

    // поиск следующей локации
    protected Location findNextLocation(Set<Location> locations) {
        //TODO the find best location, not random one
        if(locations.size()>0) {
            int i = ThreadLocalRandom.current().nextInt(locations.size());
            return locations.stream().skip(i).findFirst().get();
        }
        else {
            return location;
        }
    }

    // возвращает список возможных локаций для животного
    protected Set<Location> findPossibleLocations(Island island) {
        HashSet<Location> possibleLocations = new HashSet<>();
        for (int i = -getMaxMovementDistance(); i <= getMaxMovementDistance() ; i++) {
            for (int j = Math.abs(i)-getMaxMovementDistance(); j <= getMaxMovementDistance() - Math.abs(i) ; j++) {
                if(this.location.getX() + i >= 0 && this.location.getY() + j >= 0 && this.location.getX() + i < island.getWidth() && this.location.getY() + j < island.getHeight()) {
                    Location location = island.getLocation(this.location.getX() + i, this.location.getY() + j);
                    if(location.checkCapacity(getType())) {
                        possibleLocations.add(location);
                    }
                }
            }
        }
        return possibleLocations;
    }

    // переместиться из текущей локации или остаться
    protected void move(Location endLocation) {
        if(endLocation != this.location) {
            endLocation.addAnimal(this);
            this.location.removeAnimal(this);
            this.location = endLocation;
            this.moved = true;
        }
    }

    @Override
    public void setStartLocation(Location location) {
        location.addAnimal(this);
        this.location = location;
    }

    public abstract int getMaxMovementDistance();
    public abstract double getFoodToSaturation();

    public abstract double getChanceToEat(WildlifeType foodType);
}
