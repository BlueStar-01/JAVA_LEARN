package 大三下.Spring;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

//自主实现
public class XmlBeanFactory {
    //从配置文件加载
    private final Map<String, Object> map = new HashMap<String, Object>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public void init(File file) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

        NodeList beans = document.getElementsByTagName("bean");

        for (int i = 0; i < beans.getLength(); i++) {
            Element bean = (Element) beans.item(i);
            String aClass = bean.getAttribute("class");
            //根据类名创建实例。
            Class<?> clz = Class.forName(aClass);
            Object ins = clz.getConstructor().newInstance();

            put(bean.getAttribute("id"), ins);

            //属性注入
            NodeList property = bean.getElementsByTagName("property");
            for (int j = 0; j < property.getLength(); j++) {
                Element propertyElement = (Element) property.item(j);
                String propertyName = propertyElement.getAttribute("name");
                String setter = propertyElement.getAttribute("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
                if (propertyElement.hasAttribute("value")) {
                    String propertyValue = propertyElement.getAttribute("value");
                    ins.getClass().getMethod(setter, String.class).invoke(ins);
                } else if (propertyElement.hasAttribute("ref")) {
                    String refId = propertyElement.getAttribute("ref");
                    Object ref = getBean(refId);
                    ins.getClass().getMethod(setter, ref.getClass()).invoke(ins, ref);
                }
            }
        }


    }

    public Object getBean(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            /*調動父類的*/
            //super.getBean();
            return null;
        }
    }
}
