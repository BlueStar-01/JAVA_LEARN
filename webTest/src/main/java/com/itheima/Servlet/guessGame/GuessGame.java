package com.itheima.Servlet.guessGame;

import java.io.Serializable;
import java.util.Random;

public class GuessGame implements Serializable {
    private Integer guess;
    private final Integer score;
    private Integer count;
    private Boolean isOver;

    public GuessGame() {
        score = new Random(System.currentTimeMillis()).nextInt(100);
        count = 0;
        isOver = false;
    }

    public boolean check(Integer guess) {
        return score == guess;
    }

    public String guess(Integer guess) {
        count++;
        if (guess > score) {
            return "太大了";
        } else if (guess < score) {
            return "太小了";
        }
        isOver = true;
        return score + "猜对了恭喜你(๑╹◡╹)ﾉ\"\"\"你一共挑战了" + count + "次";
    }

    public boolean isOver() {
        return isOver;
    }
}
