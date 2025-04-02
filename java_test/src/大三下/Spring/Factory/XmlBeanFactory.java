package 大三下.Spring.Factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.aop.Pointcut;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import 大三下.Spring.Bean.BeanInfo;
import 大三下.Spring.Bean.Property;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

//自主实现
public class XmlBeanFactory {
    //从配置文件加载
    private final Map<String, Object> Beans = new HashMap<String, Object>();
    private final Map<String, Pointcut> pointcuts = new HashMap<>();
    private final ProxyFactory proxyFactory = new ProxyFactory();

    public void put(String key, Object value) {
        Beans.put(key, value);
    }

    public void init(File file) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document document = documentBuilderFactory.newDocumentBuilder().parse(file);

        NodeList beans = document.getElementsByTagName("bean");

        // 收集所有Bean定义和依赖
        Map<String, BeanInfo> beanDefMap = new HashMap<>();
        List<BeanInfo> beanDefs = new ArrayList<>();

        for (int i = 0; i < beans.getLength(); i++) {
            Element beanElement = (Element) beans.item(i);
            BeanInfo def = new BeanInfo();
            def.setId(beanElement.getAttribute("id"));
            def.setClassName(beanElement.getAttribute("class"));

            def.setDependencies(new ArrayList<>());
            def.setProperties(new ArrayList<>());
            NodeList properties = beanElement.getElementsByTagName("property");

            for (int j = 0; j < properties.getLength(); j++) {
                Element propElement = (Element) properties.item(j);
                Property prop = new Property();
                prop.setName(propElement.getAttribute("name"));

                if (propElement.hasAttribute("value")) {
                    prop.setValue(propElement.getAttribute("value"));
                } else if (propElement.hasAttribute("ref")) {
                    prop.setRef(propElement.getAttribute("ref"));
                    def.getDependencies().add(prop.getRef()); // 记录依赖
                }
                def.getProperties().add(prop);
            }
            beanDefs.add(def);
            beanDefMap.put(def.getId(), def);
        }

        List<String> sortedBeanIds = topSort(beanDefs, beanDefMap);

        for (String beanId : sortedBeanIds) {
            BeanInfo beanInfo = beanDefMap.get(beanId);
            //创建实例
            Class<?> clz = Class.forName(beanInfo.getClassName());
            Object instance = clz.getConstructor().newInstance();
            put(beanId, instance);
            //注入属性
            for (Property property : beanInfo.getProperties()) {
                String propertyName = property.getName();
                String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                // 查找参数类型匹配的setter方法
                Method setter = null;
                for (Method method : clz.getMethods()) {
                    if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                        setter = method;
                        break;
                    }
                }
                if (setter == null) {
                    throw new NoSuchMethodException("未找到setter方法: " + setterName);
                }
                Class<?> paramType = setter.getParameterTypes()[0];

                if (property.getValue() != null) {
                    // 处理基本类型转换
                    Object value = convertStringToType(property.getValue(), paramType);
                    setter.invoke(instance, value);
                } else if (property.getRef() != null) {
                    Object refBean = getBean(property.getRef());
                    if (!paramType.isInstance(refBean)) {
                        throw new IllegalArgumentException("类型不匹配: " + property.getRef());
                    }
                    setter.invoke(instance, refBean);
                }
            }
        }


        proxyFactory.setXmlBeanFactory(this);
        proxyFactory.initAopConfig(document);

        Beans.forEach((k, o) -> {
            if (proxyFactory.needProxy(o)) {
                Object proxy = proxyFactory.createProxy(o);
                Beans.put(k, proxy);
            }
        });
    }

    private static @NotNull List<String> topSort(@NotNull List<BeanInfo> beanDefs, Map<String, BeanInfo> beanDefMap) throws Exception {
        // 构建邻接表和入度表
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        for (BeanInfo def : beanDefs) {
            String beanId = def.getId();
            adj.put(beanId, new ArrayList<>());
            inDegree.put(beanId, 0);
        }

        for (BeanInfo def : beanDefs) {
            String from = def.getId();
            for (String depId : def.getDependencies()) {
                if (!beanDefMap.containsKey(depId)) {
                    throw new Exception("依赖的Bean未定义: " + depId);
                }
                adj.computeIfAbsent(depId, k -> new ArrayList<>()).add(from);
                inDegree.put(from, inDegree.get(from) + 1);
            }
        }

        // Kahn算法拓扑排序
        Queue<String> queue = new LinkedList<>();
        for (String beanId : inDegree.keySet()) {
            if (inDegree.get(beanId) == 0) {
                queue.add(beanId);
            }
        }

        List<String> sortedBeanIds = new ArrayList<>();
        while (!queue.isEmpty()) {
            String beanId = queue.poll();
            sortedBeanIds.add(beanId);
            for (String neighbor : adj.getOrDefault(beanId, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sortedBeanIds.size() != beanDefs.size()) {
            throw new Exception("存在循环依赖，无法解析Bean");
        }
        return sortedBeanIds;
    }

    public void registerPointcut(String id, Pointcut pointcut) {
        pointcuts.put(id, pointcut);
    }

    public Pointcut getPointcut(String id) {
        return pointcuts.get(id);
    }

    public Object getBean(String key) {
        if (Beans.containsKey(key)) {
            return Beans.get(key);
        } else {
            /*調動父類的*/
            //super.getBean();
            return null;
        }
    }

    // 辅助方法：字符串转对应类型
    private Object convertStringToType(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } // 其他类型处理...
        else {
            throw new IllegalArgumentException("不支持的属性类型: " + targetType);
        }
    }
}
