package com.example.heroes.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(value={METHOD,ANNOTATION_TYPE})
@Documented
public @interface Timed {

	/**
	 * The maximum amount of time (in milliseconds) that a test execution can take
	 * without being marked as failed due to taking too long.
	 */
	long millis();

}
