package com.dxh.code.anno;

import java.lang.annotation.*;

/**
 * @Author: Dengxh
 * @Date: 2020/11/29 17:24
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
