package com.dxh.code.anno;

import java.lang.annotation.*;

/**
 * @Author: Dengxh
 * @Date: 2020/11/29 17:23
 * @Description:
 *
 * @Retention定义了该Annotation被保留的时间长短
 */
@Target({ElementType.TYPE})//Target说明了Annotation所修饰的对象范围  TYPE:用于描述类、接口(包括注解类型) 或enum声明
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component

public @interface Service {

    String value() default "";

}

