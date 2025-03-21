package 大三上.test7;

public class Train {
    //共享变量
    private static Integer sum = 200;

    //同时只能有一个方法来访问
    public synchronized static Integer getSum() {
        return sum;
    }

    //添加方法锁同时只能执行一次
    public synchronized static void shopping() {
        sum--;
        System.out.println(sum + "：" + Thread.currentThread().getName());
    }

    static class window extends Thread {
        public void run() {
            while (getSum() > 0) {
                shopping();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        window window1 = new window();
        window window2 = new window();
        window window3 = new window();
        window window4 = new window();

        window1.start();
        window2.start();
        window3.start();
        window4.start();
    }
}
