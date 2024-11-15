package test9;


class Plate {
    private final int MAX_PEACHES = 5;
    private int peaches = 0;

    // 客人取桃子
    public synchronized void takePeach() throws InterruptedException {
        while (peaches == 0) {
            // 如果果盘为空，客人等待
            wait();
        }
        // 取走一个桃子
        peaches--;
        System.out.println(Thread.currentThread().getName() + " 取走了一个桃子，剩余 " + peaches + " 个桃子。");
        // 通知服务员果盘中有空位了
        notifyAll();
    }

    // 服务员装桃子
    public synchronized void fillPeach() throws InterruptedException {
        while (peaches == MAX_PEACHES) {
            // 如果果盘已满，服务员等待
            wait();
        }
        // 装满桃子（直到最大容量）
        peaches = MAX_PEACHES;
        System.out.println(Thread.currentThread().getName() + " 装满了桃子，共有 " + peaches + " 个桃子。");
        // 通知客人果盘中有桃子了
        notifyAll();
    }
}

class Guest extends Thread {
    private final Plate plate;

    public Guest(Plate plate, String name) {
        super(name);
        this.plate = plate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep((int) (Math.random() * 1000)); // 模拟客人取桃子的时间间隔
                plate.takePeach();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Servant extends Thread {
    private final Plate plate;

    public Servant(Plate plate, String name) {
        super(name);
        this.plate = plate;
    }

    @Override
    public void run() {
        try {
            while (true) { // 服务员不断循环检查果盘状态
                plate.fillPeach();
                Thread.sleep((int) (Math.random() * 2000)); // 模拟服务员装桃子的时间间隔，并允许其他线程运行
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class PeachParty {
    public static void main(String[] args) {
        Plate plate = new Plate();

        // 创建客人线程
        Guest guest1 = new Guest(plate, "Guest1");
        Guest guest2 = new Guest(plate, "Guest2");
        Guest guest3 = new Guest(plate, "Guest3");

        // 创建服务员线程
        Servant servant = new Servant(plate, "Servant");

        // 启动线程
        guest1.start();
        guest2.start();
        guest3.start();
        servant.start();
    }
}