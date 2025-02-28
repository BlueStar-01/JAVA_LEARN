package test4;

import java.lang.reflect.InvocationTargetException;


// FruitFactory.java
class FruitFactory extends Thread {

    public FruitFactory() {

    }
    public void run() {
        System.out.println(3);
    }
    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();
        System.out.println(1);
        fruitFactory.start();
        System.out.println(2);
    }
}