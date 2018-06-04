package com.uton.carsokApi.event.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface EventHandler {

	String name() default "";

	int maxRetry() default 10;
}
