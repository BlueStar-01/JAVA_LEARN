package 大三上.test4;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

interface Calculator {
    public void cal(double a, double b);
}

class Add implements Calculator {
    public Add() {

    }

    public void cal(double a, double b) {
        System.out.println(a + " Addition " + b + " = " + (a + b));
    }
}

class Sub implements Calculator {
    public Sub() {
    }

    public void cal(double a, double b) {
        System.out.println(a + " Subtract " + b + " = " + (a - b));
    }
}

class Mul implements Calculator {
    public Mul() {
    }

    public void cal(double a, double b) {
        System.out.println(a + " Multiply " + b + " = " + a * b);
    }
}


public class T1 {
    public static String getAllSubClassName(Object c) {
        StringBuilder stringBuilder = new StringBuilder();

        return c.getClass().getSimpleName();
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        while (true) {
            //用户提示
            Scanner scanner = new Scanner(System.in);
            System.out.println("亲！输入进行的运算");
            //输入1
            String className = scanner.nextLine();
            //输入2
            String a = scanner.nextLine();
            //输入3
            String b = scanner.nextLine();
            //输出
            Class<?> aClass = Class.forName(className);
            Calculator instance = (Calculator) aClass.getConstructor().newInstance();
            instance.cal(Double.parseDouble(a), Double.parseDouble(b));
        }
    }
}