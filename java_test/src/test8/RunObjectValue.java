package test8;

import java.util.ArrayList;
import java.util.List;

class ObjectValueService {
    private List<String> getUserIds = new ArrayList<String>();


    public void get(Long id) {
        synchronized (this) {
            if (getUserIds.size() < 3) {
                getUserIds.add(Thread.currentThread().getName());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "秒杀成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "秒杀失败");
            }
        }
    }

    public void show() {
        System.out.println("商家公布名单");
        getUserIds.forEach(s -> System.out.println(s + ":"));
    }
}

public class RunObjectValue {
    public static void main(String[] args) throws InterruptedException {
        ObjectValueService object = new ObjectValueService();
        ObjectValueThread a = new ObjectValueThread(object);
        a.setName("a");
        ObjectValueThread b = new ObjectValueThread(object);
        b.setName("b");
        ObjectValueThread c = new ObjectValueThread(object);
        c.setName("c");
        ObjectValueThread d = new ObjectValueThread(object);
        d.setName("d");
        ObjectValueThread f = new ObjectValueThread(object);
        f.setName("f");

        a.start();
        b.start();
        c.start();
        d.start();
        f.start();

        a.join();
        b.join();
        c.join();
        d.join();
        f.join();

        object.show();
    }
}

class ObjectValueThread extends Thread {
    private ObjectValueService service;

    public ObjectValueThread(ObjectValueService s) {
        super();
        this.service = s;
    }

    @Override
    public void run() {
        service.get(getId());
    }
}
