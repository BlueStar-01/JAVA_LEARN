package da3xiao.test_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IStudentProxy {
    private static IStudent student;

    public static void main(String[] args) {
        try {
            //反射创建类
            student = (Student) Class.forName("da3xiao.test_proxy.Student").getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成代理类
        IStudent proxy = (IStudent) Proxy.newProxyInstance(student.getClass().getClassLoader(), student.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object o = null;
                if (method.getName().equals("doExercise")) {
                    System.out.println("方法执行前");
                    method.invoke(student, args);
                    System.out.println("方法执行后");
                }
                return o;
            }
        });
        //通过代理对象调用
        proxy.doExercise("(*´▽｀)ノノ");
    }
}
