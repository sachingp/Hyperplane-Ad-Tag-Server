package com.ad.server.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.CreativeAssets;

@SuppressWarnings({ "rawtypes" })
@Repository("creativeAssetsRepo")
public interface CreativeAssetsRepo extends JpaRepository<CreativeAssets, Integer>, Cache {

  String CREATIVE_ASSETS = "creative-assets";

  default Class getType() {
    return CreativeAssetsRepo.class;
  }

  @Cacheable(name = CREATIVE_ASSETS, whole = true, key = {"creativeId"}, keyType = Integer.class, valueType = List.class, custom = "prepareByCreative")
  @Query("SELECT new CreativeAssets(cr.creativeId, ca.assetUrl, ca.clickUrl, at.assetTypeId, ass.assetSizeId) FROM CreativeAssets ca"
      + " INNER JOIN ca.creative cr INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac INNER JOIN ca.assetType at INNER JOIN ca.assetSize ass"
      + " WHERE ca.status=1 AND cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  List<CreativeAssets> findActiveCreativeAssets();

  default Map<Integer, List<CreativeAssets>> prepareByCreative(final List<CreativeAssets> assets) {
    if (assets == null || assets.isEmpty()) {
      return null;
    }
    final Map<Integer, List<CreativeAssets>> map = new HashMap<>();
    assets.forEach(asset -> {
      if (asset == null) {
        return;
      }
      final Integer creativeId = asset.getCreativeId();
      final List<CreativeAssets> list;
      if (map.containsKey(creativeId)) {
        list = map.get(creativeId);
      } else {
        list = new ArrayList<>();
        map.put(creativeId, list);
      }
      list.add(asset);
    });
    return map;
  }

}