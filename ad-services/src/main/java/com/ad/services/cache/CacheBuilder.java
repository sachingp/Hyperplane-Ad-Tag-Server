package com.ad.services.cache;

import java.util.Date;

import com.ad.server.Cache;
import com.ad.services.exception.AdServicesException;

public interface CacheBuilder<K, V> {

  void build(Cache cache) throws AdServicesException;

  void delta(Cache cache, Date lastModifiedOn) throws AdServicesException;

}
