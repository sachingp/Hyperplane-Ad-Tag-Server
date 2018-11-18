package com.ad.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for cache building from the repository.<br>
 * <p>
 * Attributes are,<br>
 * <ul>
 * <li>name - cache name - mandatory</li>
 * <li>key - array of attribute names to form the key - mandatory</li>
 * <li>value - array of attribute names to form the value - optional</li>
 * <li>keyType - type of key - optional</li>
 * <li>valueType - type of value - optional</li>
 * <li>custom - method name from respective class where structure build implementation is
 * present</li>
 * </ul>
 * <p>
 * Conditions are,<br>
 * <ul>
 * <li>when <i>value</i> left blank, will be taken the whole object as the value</li>
 * <li>when <i>keyType</i> are left to default, <i>custom</i> should present</li>
 * <li> when <i>custom</i> is empty, keyType</i> should be present</li>
 * </ul>
 */
@SuppressWarnings({"rawtypes"})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

  String name();

  String[] key();

  String[] value() default {};

  Class keyType() default Object.class;

  Class valueType() default Object.class;

  String custom() default "";

  boolean whole() default false;

}
