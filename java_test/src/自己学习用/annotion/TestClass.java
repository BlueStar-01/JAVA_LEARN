package 自己学习用.annotion;



import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class TestClass {

    @Test
    public void TestDemo() {
        Class<Demo> demoClass = Demo.class;
        Annotation[] annotation = demoClass.getDeclaredAnnotations();
        for (Annotation annotation1 : annotation) {
            System.out.println(annotation1);
        }

        if (demoClass.isAnnotationPresent(MyTest4.class)) {
            MyTest4 annotation1 = demoClass.getAnnotation(MyTest4.class);
            System.out.println(annotation1.value());
            System.out.println(annotation1.aaa());
            System.out.println(annotation1.bbb());
        }
    }

    @Test
    public void TestDemoUnit() throws InvocationTargetException, IllegalAccessException {

    }
}
