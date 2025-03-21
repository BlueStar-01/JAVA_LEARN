package 大三上.test6;

import java.util.Random;

class taoshu implements Runnable {
    private Integer taozi = 100;

    public Integer getTaozi() {
        return taozi;
    }

    public void setTaozi(Integer taozi) {
        this.taozi = taozi;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
            taozi += new Random().nextInt(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.taozi++;
        System.out.println("结了个桃子现在有" + taozi);

    }
}

class wukong implements Runnable {
    private taoshu taozi;

    wukong(taoshu taozi) {
        this.taozi = taozi;
    }

    @Override
    public void run() {
        while (taozi.getTaozi() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("悟空吃了一个" + this.taozi.getTaozi());
            taozi.setTaozi(taozi.getTaozi() - 1);
        }
    }
}

class xianNu implements Runnable {
    private taoshu taozi;

    xianNu(taoshu taozi) {
        this.taozi = taozi;
    }

    @Override
    public void run() {
        while (taozi.getTaozi() > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("仙女发现有" + taozi.getTaozi() + "个桃子");
        }
    }
}

public class BMW {
    public static void main(String[] args) {
        taoshu taoshu = new taoshu();
        Thread thead1 = new Thread(new wukong(taoshu));
        Thread thead2 = new Thread(new xianNu(taoshu));
        new Thread(taoshu).start();
        thead1.start();
        thead2.start();
    }

}
