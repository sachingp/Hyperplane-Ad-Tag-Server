package com.ad.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class ReflectionUtil {

  private static final Map<Class, List<Field>> fieldMap = new HashMap<>();
  private static final Map<Class, Map<Class, List<Field>>> annotationFieldMap = new HashMap<>();
  private static final Map<Class, List<Method>> methodMap = new HashMap<>();
  private static final Map<Class, Map<Class, List<Method>>> annotationMethodMap = new HashMap<>();
  private static final Map<Class, Map<String, Field>> fieldByName = new HashMap<>();
  private static final Map<Class, Map<String, Method>> methodByName = new HashMap<>();

  private ReflectionUtil() {
    // do nothing
  }

  public static List<Field> getFields(final Class clazz) {
    if (clazz == null) {
      return null;
    }
    final List<Field> fields;
    if (fieldMap.containsKey(clazz)) {
      fields = fieldMap.get(clazz);
    } else {
      fields = new ArrayList<>();
      fieldMap.put(clazz, fields);
      initialize(clazz, fields);
    }
    return fields;
  }

  public static List<Field> getFields(final Class clazz, final Class annotation) {
    if (clazz == null) {
      return null;
    }
    final Map<Class, List<Field>> fields;
    final List<Field> annotatedFields;
    if (annotationFieldMap.containsKey(clazz)) {
      fields = annotationFieldMap.get(clazz);
      if (fields.containsKey(annotation)) {
        annotatedFields = fields.get(annotation);
      } else {
        annotatedFields = new ArrayList<>();
        fields.put(annotation, annotatedFields);
      }
    } else {
      fields = new HashMap<>();
      annotationFieldMap.put(clazz, fields);
      annotatedFields = new ArrayList<>();
      fields.put(annotation, annotatedFields);
      initialize(clazz, annotation, annotatedFields);
    }
    return annotatedFields;
  }

  public static List<Method> getMethods(final Class clazz) {
    if (clazz == null) {
      return null;
    }
    final List<Method> methods;
    if (methodMap.containsKey(clazz)) {
      methods = methodMap.get(clazz);
    } else {
      methods = new ArrayList<>();
      methodMap.put(clazz, methods);
      initializeMethods(clazz, methods);
    }
    return methods;
  }

  public static List<Method> getMethods(final Class clazz, final Class annotation) {
    if (clazz == null) {
      return null;
    }
    final Map<Class, List<Method>> methods;
    final List<Method> annotatedMethods;
    if (annotationMethodMap.containsKey(clazz)) {
      methods = annotationMethodMap.get(clazz);
      if (methods.containsKey(annotation)) {
        annotatedMethods = methods.get(annotation);
      } else {
        annotatedMethods = new ArrayList<>();
        methods.put(annotation, annotatedMethods);
      }
    } else {
      methods = new HashMap<>();
      annotationMethodMap.put(clazz, methods);
      annotatedMethods = new ArrayList<>();
      methods.put(annotation, annotatedMethods);
      initializeMethods(clazz, annotation, annotatedMethods);
    }
    return annotatedMethods;
  }

  public static <T> T getFieldValue(final Class<T> type, final Object object,
      final String fieldName) throws ReflectionException {
    if (type == null || object == null) {
      return null;
    }
    try {
      final Class clazz = object.getClass();
      final String getterName = getMethodName(fieldName);
      final Map<String, Field> fields;
      if (fieldByName.containsKey(clazz)) {
        fields = fieldByName.get(clazz);
      } else {
        fields = new HashMap<>();
        fieldByName.put(clazz, fields);
        initialize(clazz, fields);
      }
      final Map<String, Method> methods;
      if (methodByName.containsKey(clazz)) {
        methods = methodByName.get(clazz);
      } else {
        methods = new HashMap<>();
        methodByName.put(clazz, methods);
        initializeMethods(clazz, methods);
      }
      final Method method = methods.get(getterName);
      if (method == null) {
        final Field field = fields.get(fieldName);
        if (field == null) {
          log.warn("No field with name: {}", fieldName);
          return null;
        }
        log.info("Field Name: {} and field: {} in class: {}", fieldName, field, clazz);
        try {
          field.setAccessible(true);
          return (T) field.get(object);
        } finally {
          field.setAccessible(false);
        }
      }
      return (T) method.invoke(object);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      log.error(e.getMessage(), e);
      throw new ReflectionException(e);
    }
  }

  public static <T> T invoke(final Class resource, final Object object, final String methodName,
      final Class<T> type,
      final Object... args) throws ReflectionException {
    try {
      final Map<String, Method> methods;
      if (methodByName.containsKey(resource)) {
        methods = methodByName.get(resource);
      } else {
        methods = new HashMap<>();
        methodByName.put(resource, methods);
        initializeMethods(resource, methods);// FIXME - consider args and types
      }
      final Method method = methods.get(methodName);
      return (T) method.invoke(object, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      log.error(e.getMessage(), e);
      throw new ReflectionException(e);
    }
  }

  private static void initialize(final Class clazz, final List<Field> fields) {
    final Field[] declared = clazz.getDeclaredFields();
    for (final Field field : declared) {
      fields.add(field);
    }
  }

  private static void initialize(final Class clazz, final Map<String, Field> fields) {
    final Field[] declared = clazz.getDeclaredFields();
    for (final Field field : declared) {
      fields.put(field.getName(), field);
    }
  }

  private static void initialize(final Class clazz, final Class annotation,
      final List<Field> fields) {
    final Field[] declared = clazz.getDeclaredFields();
    for (final Field field : declared) {
      if (field.isAnnotationPresent(annotation)) {
        fields.add(field);
      }
    }
  }

  private static void initializeMethods(final Class clazz, final List<Method> methods) {
    final Method[] declared = clazz.getDeclaredMethods();
    for (final Method method : declared) {
      methods.add(method);
    }
  }

  private static void initializeMethods(final Class clazz, final Map<String, Method> methods) {
    final Method[] declared = clazz.getDeclaredMethods();
    for (final Method method : declared) {
      methods.put(method.getName(), method);
    }
  }

  private static void initializeMethods(final Class clazz, final Class annotation,
      final List<Method> methods) {
    final Method[] declared = clazz.getDeclaredMethods();
    for (final Method method : declared) {
      if (method.isAnnotationPresent(annotation)) {
        methods.add(method);
      }
    }
  }

  private static String getMethodName(final String fieldName) {
    if (fieldName == null || fieldName.trim().isEmpty()) {
      return null;
    }
    final String getterName =
        "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    return getterName;
  }

  public static boolean isPrimitive(final Class type) {
    return type.isPrimitive()
        || String.class.equals(type)
        || Integer.class.equals(type)
        || Boolean.class.equals(type)
        || Long.class.equals(type)
        || Date.class.equals(type)
        || Short.class.equals(type)
        || Double.class.equals(type)
        || Float.class.equals(type)
        || java.sql.Date.class.equals(type)
        || Byte.class.equals(type)
        || Character.class.equals(type)
        || BigDecimal.class.equals(type)
        || BigInteger.class.equals(type);
  }

}

