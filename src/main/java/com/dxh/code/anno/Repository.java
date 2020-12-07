package com.dxh.code.anno;

import java.lang.annotation.*;

/**
 * @Author: Dengxh
 * @Date: 2020/11/29 18:48
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Repository{

    String value() default "";

}

