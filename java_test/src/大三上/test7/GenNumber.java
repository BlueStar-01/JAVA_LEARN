package 大三上.test7;

import java.util.Random;

class GenRandom extends Thread {
    //生成随机数的数量 -- 顺序型生成数量，和线程的生成数量不同
    private final int max;

    GenRandom(int m) {
        max = m;
    }

    //生成max个随机数--不要输出、不要保存，无意义，且浪费时间
    private static void genRandom(int max) {
        Random r = new Random();
        for (int i = 0; i < max; i++)
            r.nextInt();
    }

    //顺序型生成--max=100万
    public static void seqGen(int max) {
        genRandom(max);
    }

    //用线程生成--4个线程，max=25万
    public void run() {
        Integer len = max / 4;
        genRandom(len);
    }
}

public class GenNumber {
    public static void main(String[] args) {
        //这里取100万，太小很难计算时间
        final int max = 1000000000;
        //获取开始时间
        long startTime = System.currentTimeMillis();
        //执行顺序型
        GenRandom.seqGen(max);
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("顺序型运行时间：" + (endTime - startTime) + "ms");
        //在这里补充使用四个线程生成 100万个随机数}

        //获取开始时间
        startTime = System.currentTimeMillis();
        //执行顺序型
        GenRandom random1 = new GenRandom(max);
        GenRandom random2 = new GenRandom(max);
        GenRandom random3 = new GenRandom(max);
        GenRandom random4 = new GenRandom(max);
        try {
            random1.start();
            random2.start();
            random3.start();
            random4.start();
            random1.join();
            random2.join();
            random3.join();
            random4.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("并行运行时间：" + (endTime - startTime) + "ms");

    }
}