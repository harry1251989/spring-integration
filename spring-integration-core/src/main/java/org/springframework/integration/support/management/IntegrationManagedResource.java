/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.support.management;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Clone of {@link ManagedResource} limiting beans thus annoated so that they
 * will only be exported by the {@code IntegrationMBeanExporter}.
 *
 * @author Gary Russell
 * @since 4.2
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IntegrationManagedResource {

	/**
	 * The annotation value is equivalent to the {@code objectName}
	 * attribute, for simple default usage.
	 * @return the value.
	 */
	String value() default "";

	String objectName() default "";

	String description() default "";

	int currencyTimeLimit() default -1;

	boolean log() default false;

	String logFile() default "";

	String persistPolicy() default "";

	int persistPeriod() default -1;

	String persistName() default "";

	String persistLocation() default "";

}
