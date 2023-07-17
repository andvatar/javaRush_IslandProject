package com.example.islandproject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public abstract class Animal extends Wildlife {

    protected volatile boolean canReproduce;
    protected boolean moved;
    private final WildlifeFactory wildlifeFactory = new WildlifeFactory();

    // миграция из одной локации в другую
    public void migrate(Island island) {
        move(findNextLocation(findPossibleLocations(island)));
    }

    // поиск и поедание пищи
    public void feed() {

        // все, что можем съесть
        List<Wildlife> foodList = location
                .getWildlife()
                .stream()
                .filter(wildlife -> this.getChanceToEat(wildlife.getType()) > 0 && wildlife != this)
                .toList();

        for (var food:foodList) {
            // животное съели пока оно искало еду :(
            if(!isAlive()) {
                break;
            }
            if(hp < 100) {
                // еду съели до нас, ищем дальше
                if(!food.isAlive()) {
                    continue;
                }
                if (lockPartners(food)) {
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

    // размножаться
    public void reproduce() {
        // список потенциальных партнеров = все животные своего класса в этой локации
        List<Animal> partners = location
                .getAnimals()
                .stream()
                .filter(animal -> animal.getType() == this.getType() && animal != this)
                .toList();

        for (var partner:partners) {
            // животное съели пока оно искало пару :(
            if(!isAlive()) {
                break;
            }
            // партнера съели или успели спариться до нас
            if(!partner.isAlive() || !partner.canReproduce) {
                continue;
            }
            if (lockPartners(partner)) {
                try {
                    if (ThreadLocalRandom.current().nextInt(100) > 75) {
                        for (int i = 0; i < this.getKidsAmount(); i++) {
                            Animal child = (Animal) wildlifeFactory.createWildlife(this.getType());
                            child.setStartLocation(this.location);
                            child.canReproduce = false;
                            child.moved = false;
                        }
                    }
                } finally {
                    lock.unlock();
                    partner.lock.unlock();
                }
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
    // либо парнеров по спариванию
    private boolean lockPartners(Wildlife wildlife) {
        boolean eaterLocked = false;
        boolean foodLocked = false;

        try {
            eaterLocked = lock.tryLock(100, TimeUnit.MILLISECONDS);
            foodLocked = wildlife.lock.tryLock(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(!(eaterLocked && foodLocked)) {
                if(eaterLocked) {
                    lock.unlock();
                }
                if(foodLocked) {
                    wildlife.lock.unlock();
                }
            }
        }
        return eaterLocked && foodLocked;
    }

    // в конце цикла животные теряют 50 хп
    public void starve() {
        loseHP(50);
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

    public abstract int getKidsAmount();
}
