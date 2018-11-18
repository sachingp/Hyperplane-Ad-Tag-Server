package com.ad.server;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({"rawtypes"})
public interface Cache {

  Class getType();

}
