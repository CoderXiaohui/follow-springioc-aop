package com.dxh.code.factory;


import com.dxh.code.anno.Autowired;
import com.dxh.code.anno.Component;
import com.dxh.code.anno.Transactional;
import com.dxh.code.utils.AnnotationUtils;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ConfigurationBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;


public class BeanFactory implements ServletContextListener {

    private static Map<String,Object> beanMap = new HashMap<>();

    public static Object getBean(String id){
        return beanMap.get(id);
    }


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //被扫描的包名
        String packageName = "com.dxh.code";

        try{
            Reflections refl = new Reflections(new ConfigurationBuilder()
                    .forPackages(packageName) // 指定路径URL
                    .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                    .addScanners(new MethodAnnotationsScanner() ) // 添加 方法注解扫描工具
                    .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
            );

            Set<Class<?>> set = refl.getTypesAnnotatedWith(Component.class);
            //1.通过反射实例化对象，存到单例池中
            for (Class<?> aClass : set) {
                //排除注解类
                if (aClass.isAnnotation()){
                    continue;
                }
                Object preBean = aClass.getDeclaredConstructor().newInstance();
                String annotationName = AnnotationUtils.getAnnotationName(aClass);
                beanMap.put(annotationName,preBean);
            }
            //2. 依赖关系注入
            beanMap.forEach(new BiConsumer<String, Object>() {
                @Override
                public void accept(String s, Object o) {
                    Field[] declaredFields = o.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        if (declaredField.isAnnotationPresent(Autowired.class)){
                            String name = declaredField.getName();
                            declaredField.setAccessible(true);
                            try {
                                declaredField.set(o,beanMap.get(name));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            //遍历map找到Transactional的注解，然后配置代理类
            beanMap.forEach(new BiConsumer<String, Object>() {
                @Override
                public void accept(String s, Object o) {
                    //判断是否有Transactional注解
                    if(o.getClass().isAnnotationPresent(Transactional.class)){
                        //获取代理工厂
                        ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
                        Class<?>[] interfaces = o.getClass().getInterfaces();//获取类实现的所有接口
                        if(interfaces.length>0){
                            //有实现接口使用Jdk动态代理
                            o = proxyFactory.getJdkProxy(o);
                        }else{
                            //没有实现接口使用cglib动态代理
                            o = proxyFactory.getCglibProxy(o);
                        }
                        beanMap.put(s,o);
                    }
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
