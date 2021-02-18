package com.game.model;

import java.util.Comparator;

public class UserScore {
    private int score;
    private int errors;
    private String name;
    private boolean bot;

    public UserScore() { }

    public UserScore(int score, int errors, String name, boolean bot) {
        this.score = score;
        this.errors = errors;
        this.name = name;
        this.bot = bot;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

}
