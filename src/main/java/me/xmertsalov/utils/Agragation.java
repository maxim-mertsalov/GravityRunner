package me.xmertsalov.utils;

public class Agragation {

    public Agragation() {}

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
