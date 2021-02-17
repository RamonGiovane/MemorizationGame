package com.game.model;

import java.util.Comparator;

public class UserScore implements Comparable<UserScore>, Comparator<UserScore> {
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

    @Override
    public int compareTo(UserScore o) {
        int isBot1 = bot ? 0 : 1;
        int isBot2 = o.isBot() ? 0 : 1;

        return Integer.compare(score + isBot1, o.score + isBot2);
    }



    @Override
    public int compare(UserScore o1, UserScore o2) {
        int score1 = o1.getErrors() +( o1.isBot() ? 1 : 0);
        int score2 = o2.getErrors() + (o2.isBot() ? 1 : 0);

        return Integer.compare(score2, score1);
    }
}
