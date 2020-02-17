package com.github.herowzz.atm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.herowzz.atm.model.DriverType;

/**
 * 配置参数
 * @author wangzz
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration {

	/**
	 * 扫描包
	 */
	String packages() default "";

	/**
	 * 序号
	 */
	DriverType driverType();

}
