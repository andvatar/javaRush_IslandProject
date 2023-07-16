package com.example.islandproject;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Wildlife {
    public final Lock lock = new ReentrantLock();
    protected volatile double hp;
    protected boolean moved;

    protected Location location;

    public Wildlife() {
        hp = 100;
    }

    protected void loseHP(double damage) {
        hp -= damage;
    }

    // растение умирает, если его полностью съели
    // животное умирает всегда
    public abstract double beEaten(double lackOfFood);

    public abstract void setStartLocation(Location location);

    public abstract String getIcon();
    public abstract double getWeight();
    public abstract WildlifeType getType();

    public boolean isAlive() {
        return hp > 0;
    }
}
