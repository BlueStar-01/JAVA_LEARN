package test5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

interface PersonInf {
    void save();

    void updata();

    void add();

    void delect();
}

class AspectJTest {
    public void before() {
        System.out.println("before!");
    }

    public void after() {
        System.out.println("after!");
    }
}

class Handler implements InvocationHandler {
    private final Object target;

    Handler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        AspectJTest aspectJTest = new AspectJTest();
        aspectJTest.before();
        Object o = method.invoke(target, args);
        aspectJTest.after();
        return o;
    }
}

public class AOP {

    public static <T> T getProxyInstance(T src) {
        Handler handler = new Handler(src);

        T prosy = null;
        try {
            prosy = (T) Proxy.newProxyInstance(
                    src.getClass().getClassLoader(),
                    src.getClass().getInterfaces(),
                    handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return prosy;
    }

    public static void main(String[] args) {

        PersonInf personSQLImp = new PersonSQLImp();
        Dog dog = new GunDog();

        personSQLImp = getProxyInstance(personSQLImp);
        dog = getProxyInstance(dog);

        personSQLImp.add();
        personSQLImp.updata();
        personSQLImp.save();
        personSQLImp.delect();

        dog.run();
        dog.info();
    }
}

class PersonSQLImp implements PersonInf {
    public void save() {
        System.out.println("数据保存成功！");
    }

    public void updata() {
        System.out.println("数据更新成功！");
    }

    public void add() {
        System.out.println("数据插入成功！");
    }

    public void delect() {
        System.out.println("数据删除成功！");
    }
}

interface Dog {
    public void info();

    public void run();
}

class GunDog implements Dog {
    //info方法实现，仅仅打印一个字符串
    public void info() {
        System.out.println("我是一只猎狗");
    }

    //run方法实现，仅仅打印一个字符串
    public void run() {
        System.out.println("我奔跑迅速");
    }
}
