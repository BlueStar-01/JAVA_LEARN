package test4;

interface Speakable {
    void speak(String message);
}

abstract class Person implements Speakable {
    public abstract String toString();
}

class PersonProxy extends Person {
    private final Speakable speakable;

    public PersonProxy(Speakable speakable) {
        this.speakable = speakable;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void speak(String message) {
        System.out.println("Class begin");
        speakable.speak(message);
        System.out.println("Class end");
    }
}

public class Teacher extends Person {
    public static void main(String[] args) {
        Person person = new Teacher();
        PersonProxy personProxy = new PersonProxy(person);
        personProxy.speak("1、The java.lang and java.lang.reflect packages provide classes for java reflection.\n" +
                "2、Java Reflection is a process of examining or modifying the run time behavior of a class at run time.\n" +
                "3、The java.lang.Class class provides many methods that can be used to get metadata, examine and change the run time behavior of a class")
        ;
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
        return "[Positon=" + positon + " salary= " + salary + "]";
    }

    public int getSalary() {
        return salary;
    }
}


