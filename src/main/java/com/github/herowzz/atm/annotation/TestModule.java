package com.github.herowzz.atm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试模块
 * @author wangzz
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestModule {

	/**
	 * 名称
	 */
	String name() default "";

	/**
	 * 序号
	 */
	int order() default 0;

}
