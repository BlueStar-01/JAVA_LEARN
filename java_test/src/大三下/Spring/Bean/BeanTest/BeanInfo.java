package 大三下.Spring.Bean.BeanTest;

import lombok.Data;

import java.util.List;

@Data
public class BeanInfo {
    private String id;
    private String className;
    private List<String> dependencies;
    private List<Property> properties;
}
