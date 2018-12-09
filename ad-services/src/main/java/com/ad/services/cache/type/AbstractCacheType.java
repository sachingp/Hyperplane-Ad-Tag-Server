package com.ad.services.cache.type;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.ad.services.exception.AdServicesException;

@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractCacheType<T> implements Serializable {

  private static final long serialVersionUID = -5979516965451450133L;

  protected final Class resource;
  protected final Class type;
  protected final String[] fields;

  protected T t;

  protected AbstractCacheType(final Class resource, final Class type, final String... fields) {
    this.resource = resource;
    this.type = type;
    this.fields = fields;
  }

  protected AbstractCacheType(final T t) {
    resource = null;
    this.type = t.getClass();
    fields = null;
    this.t = t;
  }

  public T get() {
    return t;
  }

  public void set(final T t) {
    this.t = t;
  }

  public void set(final List list) throws AdServicesException {
    if (list == null || list.isEmpty()) {
      return;
    }
    try {
      final int size = list.size();
      final Object[] args = new Object[size];
      final Class[] argTypes = new Class[size];
      for (int i = 0; i < size; i++) {
        final Object o = list.get(i);
        if (o == null) {
          args[i] = null;
          argTypes[i] = Object.class;
          continue;
        }
        args[i] = o;
        argTypes[i] = o.getClass();
      }
      if (PrimitiveType.isPrimitive(type)) {
        t = primitiveType(args[0]);
      } else {
        final Constructor argsConstructor = type.getConstructor(argTypes);
        t = (T) argsConstructor.newInstance(args);
      }
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      log.error(e.getMessage(), e);
      throw new AdServicesException("Exception while setting value by type.", e);
    }
  }

  private T primitiveType(final Object o) {
    switch (PrimitiveType.from(type)) {
      case INT:
        if (o == null) {
          return (T) new Integer(0);
        }
        return (T) new Integer((int) o);
      case FLOAT:
        if (o == null) {
          return (T) new Float(0);
        }
        return (T) new Float((float) o);
      case DOUBLE:
        if (o == null) {
          return (T) new Double(0);
        }
        return (T) new Double((double) o);
      case SHORT:
        if (o == null) {
          return (T) new Short((short) 0);
        }
        return (T) new Short((short) o);
      case BYTE:
        if (o == null) {
          return (T) new Byte((byte) 0);
        }
        return (T) new Byte((byte) o);
      case LONG:
        if (o == null) {
          return (T) new Long(0);
        }
        return (T) new Long((long) o);
    }
    return (T) o;
  }

  private enum PrimitiveType {
    INT("int", "Integer"),
    FLOAT("float", "Float"),
    DOUBLE("double", "Double"),
    SHORT("short", "Short"),
    BYTE("byte", "Byte"),
    LONG("long", "Long");

    private final String primitive;
    private final String wrapper;

    private PrimitiveType(final String primitive, final String wrapper) {
      this.primitive = primitive;
      this.wrapper = wrapper;
    }

    private static PrimitiveType from(final Class type) {
      if (type == null) {
        return null;
      }
      final String name = type.getSimpleName();
      final PrimitiveType[] values = PrimitiveType.values();
      for (final PrimitiveType value : values) {
        if (value.primitive.equals(name) || value.wrapper.equals(name)) {
          return value;
        }
      }
      return null;
    }

    private static boolean isPrimitive(final Class type) {
      if (type == null) {
        return false;
      }
      final String name = type.getSimpleName();
      final PrimitiveType[] values = PrimitiveType.values();
      for (final PrimitiveType value : values) {
        if (value.primitive.equals(name) || value.wrapper.equals(name)) {
          return true;
        }
      }
      return false;
    }

  }

}
