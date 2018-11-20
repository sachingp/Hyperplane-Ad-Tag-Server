package com.ad.gen;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class RepoGeneratorFromTable extends BaseGeneratorUtil {

  private static final String DIR = "src/main/java/com/ad/server/repo";
  private static final String REPO_TEMPLATE = "package com.ad.server.repo;"
      + "\n\nimport org.springframework.data.jpa.repository.JpaRepository;"
      + "\nimport org.springframework.stereotype.Repository;"
      + "\n\nimport com.ad.server.Cache;"
      + "\nimport com.ad.server.pojo.{clazz};"
      + "\n\n@SuppressWarnings({ \"rawtypes\" })"
      + "\n@Repository"
      + "\npublic interface {clazz}Repo extends JpaRepository<{clazz}, Integer>, Cache {"
      + "\n\n  default Class getType() {"
      + "\n    return {clazz}.class;"
      + "\n  }"
      + "\n\n}";

  @Test
  public void generateRepoTest() throws Exception {
    final Set<String> tables = getContentAsList("table.list");
    final Set<String> classNames = new HashSet<>(tables.size());
    for (final String table : tables) {
      final String clazz = getTypeName(Character.toUpperCase(table.charAt(0)) + table.substring(1));
      final String repo = REPO_TEMPLATE.replace("{clazz}", clazz);
      log.debug("Creating POJO for: {} == {}", table, clazz);
      writeTo(DIR, clazz + "Repo", repo, false);
      classNames.add(clazz + "Repo.java");
    }
    final File dir = new File(DIR);
    final String[] files = dir.list();
    final Set<String> fileset = new HashSet<>();
    for (final String fileName : files) {
      fileset.add(fileName);
    }
    for (final String className : classNames) {
      assertTrue(fileset.contains(className));
    }
  }

}
