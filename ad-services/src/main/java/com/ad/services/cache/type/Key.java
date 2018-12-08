package com.ad.services.cache.type;

@SuppressWarnings({ "rawtypes" })
public class Key<T> extends AbstractCacheType<T> {

  private static final long serialVersionUID = -5634236630545275786L;

  public Key(final Class resource, final Class type, final String[] fields) {
    super(resource, type, fields);
  }

  public Key(final T t) {
    super(t);
  }

}