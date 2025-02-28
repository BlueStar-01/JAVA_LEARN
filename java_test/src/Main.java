import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.lang.annotation.*;
import java.util.Set;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Autowire {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Component {
}

class SimpleIocContainer {

    private Map<Class<?>, Object> components = new HashMap<>();

    public SimpleIocContainer() {

    }

    // 注册组件到容器
    public void register(Class<?> componentClass) {
        try {
            Object instance = componentClass.getDeclaredConstructor().newInstance();
            components.put(componentClass, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 自动装配标注了@Autowire注解的字段
    public void autowire(Object bean) throws IllegalAccessException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Autowire.class)) {
                field.setAccessible(true);
                if (components.containsKey(field.getType())) {
                    field.set(bean, components.get(field.getType()));
                } else {
                    field.set(bean, null);
                    System.out.println("容器中没有对应的Bean" + field.getName());
                }
            }
        }
    }

    // 获取Bean
    public <T> T getBean(Class<T> beanClass) {
        return beanClass.cast(components.get(beanClass));
    }

    // 扫描并注册组件
}


@Component
class DatabaseConnection {
    // Database connection details
}

@Component
class MyService {
    @Autowire
    private DatabaseConnection dbConnection;

    public void doSomething() {
        System.out.println("注入的对象的地址：" + dbConnection);
    }
}

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        SimpleIocContainer iocContainer = new SimpleIocContainer();

        // 模拟扫描并注册组件
        iocContainer.register(DatabaseConnection.class);
        iocContainer.register(MyService.class);

        // 获取MyService的实例并自动装配
        MyService myService = iocContainer.getBean(MyService.class);
        iocContainer.autowire(myService);

        // 使用自动装配后的MyService实例
        myService.doSomething();
    }
}