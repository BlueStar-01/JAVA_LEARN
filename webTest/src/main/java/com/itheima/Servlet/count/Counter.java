package com.itheima.Servlet.count;

public class Counter {
    private int count; //计数值

    public Counter() {
        this(0);
    }

    public Counter(int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void add() {
        count++;
    }

    public void reset() {
        count = 0;
    }
}