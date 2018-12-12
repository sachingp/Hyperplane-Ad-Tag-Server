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

  default Class getType() {
    return CreativeTag.class;
  }

  @Cacheable(name = TAG_GUID_CREATIVE, whole = true, key = {
      "tagGuid"}, value={"creativeId"}, keyType = String.class, valueType = Integer.class)
  @Query("SELECT new CreativeTag(ct.tagGuid, cr.creativeId) FROM CreativeTag ct"
      + " INNER JOIN ct.creative cr WHERE cr.status = 1")
  public List<CreativeTag> findActiveCreativeGuids();

}