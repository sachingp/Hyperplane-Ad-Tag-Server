package com.ad.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.server.pojo.CreativeTag;

@SuppressWarnings({ "rawtypes" })
@Repository("creativeTagRepo")
@RepositoryRestResource(collectionResourceRel = "creativeTags", path = "creativeTags")
public interface CreativeTagRepo extends JpaRepository<CreativeTag, Integer>, Cache {

  String TAG_GUID_CREATIVE = "active-tag-guids";
  String TAG_GUID_URL = "active-tag-url";

  default Class getType() {
    return CreativeTagRepo.class;
  }

  @Cacheable(name = TAG_GUID_CREATIVE, whole = true, key = {
      "tagGuid"}, keyType = String.class, valueType = CreativeTag.class)
  @Query("SELECT new CreativeTag(ct.tagGuid, cr.creativeId, ct.tagTypeId, ct.adPartnerId) FROM CreativeTag ct"
      + " INNER JOIN ct.creative cr INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<CreativeTag> findActiveCreativeGuids();

  @Cacheable(name = TAG_GUID_URL, whole = true, key = {
      "tagGuid"}, value = {"creativeMarkupUrl"}, keyType = String.class, valueType = String.class)
  @Query("SELECT new CreativeTag(ct.tagGuid, ct.creativeMarkupUrl) FROM CreativeTag ct"
      + " INNER JOIN ct.creative cr INNER JOIN cr.campaign c INNER JOIN c.advertiser ad INNER JOIN ad.account ac"
      + " WHERE cr.status = 1 AND c.status = 1 AND ad.status = 1 AND ac.status = 1")
  public List<CreativeTag> findActiveCreativeGuidUrls();

}