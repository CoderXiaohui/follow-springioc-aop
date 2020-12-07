package com.dxh.code.anno;

import java.lang.annotation.*;

/**
 * @Author: Dengxh
 * @Date: 2020/11/29 17:46
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {

	String value() default "";


	Class<? extends Throwable>[] rollbackFor() default {};


	String[] rollbackForClassName() default {};


	Class<? extends Throwable>[] noRollbackFor() default {};


	String[] noRollbackForClassName() default {};

}
