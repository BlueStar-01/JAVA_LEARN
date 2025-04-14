package 大三下.Spring.Factory;

import lombok.Getter;
import lombok.Setter;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ProxyFactory {
    @Setter
    @Getter
    private XmlBeanFactory xmlBeanFactory;

    @Getter
    private boolean isInit = false;

    // 存储所有 Advisor（切面 = 切点 + 通知）
    private final List<DefaultPointcutAdvisor> advisors = new ArrayList<>();

    /**
     * 解析 XML 中的 AOP 配置
     */
    public void initAopConfig(Document doc) {
        // 1. 获取所有 <aop:config> 标签
        NodeList aopConfigs = doc.getElementsByTagName("aop:config");
        for (int i = 0; i < aopConfigs.getLength(); i++) {
            Element config = (Element) aopConfigs.item(i);
            parseAopConfig(config);
        }
        isInit = true;
    }

    /**
     * 解析单个 <aop:config> 标签
     */
    private void parseAopConfig(Element configElement) {
        // 解析所有 <aop:aspect> 标签
        NodeList aspects = configElement.getElementsByTagName("aop:aspect");
        for (int i = 0; i < aspects.getLength(); i++) {
            Element aspectElement = (Element) aspects.item(i);
            parseAspect(aspectElement);
        }
    }

    /**
     * 解析 <aop:aspect> 标签
     */
    private void parseAspect(Element aspectElement) {
        String aspectRef = aspectElement.getAttribute("ref");
        Object adviceBean = xmlBeanFactory.getBean(aspectRef);

        // 解析该切面下的所有 <aop:pointcut> 和 <aop:before>
        NodeList children = aspectElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                String tagName = element.getLocalName();
                switch (tagName) {
                    case "pointcut":
                        parsePointcut(element, adviceBean);
                        break;
                    case "before":
                        parseBeforeAdvice(element, adviceBean);
                        break;
                    case "around":  // 新增环绕通知处理
                        parseAroundAdvice(element, adviceBean);
                        break;
                }
            }
        }
    }

    /**
     * 解析 <aop:pointcut> 标签
     */
    private void parsePointcut(Element pointcutElement, Object adviceBean) {
        String id = pointcutElement.getAttribute("id");
        String expression = pointcutElement.getAttribute("expression");

        // 创建 AspectJ 表达式切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        // 存储切点（此处简化为全局变量）
        xmlBeanFactory.registerPointcut(id, pointcut);
    }

    /**
     * 解析 <aop:before> 标签
     */
    private void parseBeforeAdvice(Element beforeElement, Object adviceBean) {
        String methodName = beforeElement.getAttribute("method");
        String pointcutRef = beforeElement.getAttribute("pointcut-ref");

        // 获取切点
        Pointcut pointcut = xmlBeanFactory.getPointcut(pointcutRef);
        if (pointcut == null) {
            throw new IllegalArgumentException("未找到切点: " + pointcutRef);
        }

        // 创建通知
        MethodBeforeAdvice advice = (MethodBeforeAdvice) createAdvice(adviceBean, methodName);

        // 创建 Advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(advice);
        advisors.add(advisor);
    }

    /**
     * 解析<aop:around>标签
     */
    private void parseAroundAdvice(Element aroundElement, Object adviceBean) {
        String methodName = aroundElement.getAttribute("method");
        String pointcutRef = aroundElement.getAttribute("pointcut-ref");

        // 获取切点
        Pointcut pointcut = xmlBeanFactory.getPointcut(pointcutRef);
        if (pointcut == null) {
            throw new IllegalArgumentException("未找到切点: " + pointcutRef);
        }

        // 创建环绕通知
        MethodInterceptor advice = createAroundAdvice(adviceBean, methodName);

        // 创建Advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(advice);
        advisors.add(advisor);
    }


    /**
     * 创建环绕通知对象
     */
    private MethodInterceptor createAroundAdvice(Object adviceBean, String methodName) {
        try {
            Method adviceMethod = adviceBean.getClass().getMethod(methodName, ProceedingJoinPoint.class);

            return new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation invocation) throws Throwable {
                    // 创建ProceedingJoinPoint
                    ProceedingJoinPoint pjp = new MethodInvocationProceedingJoinPoint((ProxyMethodInvocation) invocation);

                    // 通过反射调用通知方法
                    return adviceMethod.invoke(adviceBean, pjp);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("环绕通知方法签名错误，需要ProceedingJoinPoint参数", e);
        }
    }

    /**
     * 通过反射创建通知对象
     */
    private Advice createAdvice(Object adviceBean, String methodName) {
        try {
            // 获取通知方法，参数类型为 Method.class, Object[].class, Object.class
            Method adviceMethod = adviceBean.getClass().getMethod(
                    methodName,
                    Object.class,
                    Method.class,
                    Object[].class
            );

            // 返回 MethodBeforeAdvice 实现
            return (MethodBeforeAdvice) (targetMethod, args, target) -> {
                try {
                    // 调用通知方法，传递目标方法、参数和目标对象
                    adviceMethod.invoke(adviceBean, target, targetMethod, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("执行通知方法失败", e);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "通知类 " + adviceBean.getClass() + " 中未找到方法: " + methodName,
                    e
            );
        }
    }

    /**
     * 判断是否需要为 Bean 创建代理
     */
    public boolean needProxy(Object bean) {
        return !getMatchedAdvisors(bean).isEmpty();
    }

    /**
     * 获取匹配的 Advisor
     */
    private List<DefaultPointcutAdvisor> getMatchedAdvisors(Object bean) {
        List<DefaultPointcutAdvisor> matched = new ArrayList<>();
        for (DefaultPointcutAdvisor advisor : advisors) {
            if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                matched.add(advisor);
            }
        }
        return matched;
    }

    /**
     * 创建代理对象
     */
    public Object createProxy(Object target) {
        ProxyFactoryBean proxyFactory = new ProxyFactoryBean();
        proxyFactory.setTarget(target);
        for (DefaultPointcutAdvisor advisor : getMatchedAdvisors(target)) {
            proxyFactory.addAdvisor(advisor);
        }
        return proxyFactory.getObject();
    }
}