package 大三上.test6;

import java.util.concurrent.Callable;

class Thead1 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Integer sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}

class Thead2 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i + ":a");
        }
    }
}

class Thead3 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i + ":b");
        }
    }
}

class Q1 {
    public static void main(String[] args) throws Exception {
        Thead1 t = new Thead1();
        Thead2 thead2 = new Thead2();
        Thread thead3 = new Thread(new Thead3());
        Integer call = t.call();
        thead2.start();
        thead3.start();
        System.out.println(call);
    }
}
