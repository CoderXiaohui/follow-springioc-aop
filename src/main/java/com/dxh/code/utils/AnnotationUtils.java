package com.dxh.code.utils;

import com.dxh.code.anno.Component;
import com.dxh.code.anno.Repository;
import com.dxh.code.anno.Service;

/**
 * @Author: Dengxh
 * @Date: 2020/11/29 22:07
 * @Description:
 */
public class AnnotationUtils {

    public static String getAnnotationName(Class<?> clazz) throws Exception {
        String annotationValue = getAnnotationValue(clazz);
        String name = "";
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length==0){
            name=toLowerCaseFrist(clazz.getSimpleName());
        }else{
            if (interfaces.length==1){
                name=toLowerCaseFrist( interfaces[0].getSimpleName());
            }else{
                if (annotationValue==null || annotationValue.length()==0){
                    throw new Exception("Must be set the value");
                }else{
                    name = toLowerCaseFrist(annotationValue);
                }
            }
        }
        return name;
    }


    public static String getAnnotationValue(Class<?> clazz) throws Exception {

        Component annotation = clazz.getAnnotation(Component.class);
        if (null != annotation){
            return annotation.value();
        }
        Service serviceAnnotation = clazz.getAnnotation(Service.class);
        if (null != serviceAnnotation){
            return serviceAnnotation.value();
        }
        Repository repositoryAnnotation = clazz.getAnnotation(Repository.class);
        if (null != repositoryAnnotation){
            return repositoryAnnotation.value();
        }
        throw new Exception("this class not found Annotation");
    }


    public static String toLowerCaseFrist(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);//UpperCase大写
        return  name;
    }



}
