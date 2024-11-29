package com.itheima.Servlet.圆半径实验;

public class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getArea() {
        double area = Math.PI * radius * radius;
        return area;
    }
}
