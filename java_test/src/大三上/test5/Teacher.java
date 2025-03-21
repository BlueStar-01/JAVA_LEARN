package 大三上.test5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Speakable {
    void speak(String message);
}

abstract class Person implements Speakable {
    public abstract String toString();
}

class SpeakHandler implements InvocationHandler {
    private final Speakable speakable;

    SpeakHandler(Speakable speak) {
        speakable = speak;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("speak")) {
            System.out.println("Before method ");
            speakable.speak(args[0].toString());
            System.out.println("After method ");
            return null;
        }
        return method.invoke(speakable, args);
    }
}

public class Teacher extends Person {
    public static void main(String[] args) {
        Person person = new Teacher();
        InvocationHandler handler = new SpeakHandler(person);

        Speakable p = (Speakable) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                Person.class.getInterfaces(),
                handler);
        p.speak("1111111111111111111111111111111");
    }


    public Teacher() {
    }

    public String positon;
    private int salary;

    public void speak(String message) {
        System.out.println("Speak: " + message);
    }

    @Override
    public String toString() {
        return "[Teacher=" + positon + " salary= " + salary + "]";
    }

    public int getSalary() {
        return salary;
    }
}