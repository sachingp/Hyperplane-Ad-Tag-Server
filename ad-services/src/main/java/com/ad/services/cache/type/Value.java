package com.ad.services.cache.type;

@SuppressWarnings({"rawtypes"})
public class Value<T> extends AbstractCacheType<T> {

  private static final long serialVersionUID = 3560334915284706796L;

  public Value(final Class resource, final Class type, final String[] fields) {
    super(resource, type, fields);
  }

  public Value(final T t) {
    super(t);
  }

}