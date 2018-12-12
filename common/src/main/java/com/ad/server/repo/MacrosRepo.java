package com.ad.server.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.Macros;

@SuppressWarnings({"rawtypes"})
@Repository("macroRepo")
public interface MacrosRepo extends JpaRepository<Macros, Integer>, Cache {

  String PARTNER_MACRO = "partner-macro";

  default Class getType() {
    return Macros.class;
  }

  @Cacheable(name = PARTNER_MACRO, whole = true, key = {
      "adPartnerId"}, keyType = Integer.class, custom = "prepareByPartner")
  @Query("SELECT new Macros(p.adPartnerId, m.macroName, m.macroValue) FROM Macros m INNER JOIN m.partner p"
      + " WHERE p.status = 1 AND m.status = 1")
  public List<Macros> findActiveMacros();

  default Map<Integer, Map<String, String>> prepareByPartner(final List<Macros> macros) {
    if (macros == null || macros.isEmpty()) {
      return null;
    }
    final Map<Integer, Map<String, String>> map = new HashMap<>();
    macros.forEach(macro -> {
      final Map<String, String> byPartner;
      final Integer partnerId = macro.getPartnerId();
      if (map.containsKey(partnerId)) {
        byPartner = map.get(partnerId);
      } else {
        byPartner = new HashMap<>();
        map.put(partnerId, byPartner);
      }
      byPartner.put(macro.getMacroName(), macro.getMacroValue());
    });
    return map;
  }

}