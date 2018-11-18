package com.ad.gen;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class RepoTestGeneratorFromTable extends BaseGeneratorUtil {

  private static final String DIR = "src/test/java/com/ad/server/repo";
  private static final String REPO_TEMPLATE = "package com.ad.server.repo;"
      + "\n\nimport org.junit.runner.RunWith;"
      + "\nimport org.springframework.beans.factory.annotation.Autowired;"
      + "\nimport org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;"
      + "\nimport org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;"
      + "\nimport org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;"
      + "\nimport org.springframework.boot.test.context.SpringBootTest;"
      + "\nimport org.springframework.data.jpa.repository.JpaRepository;"
      + "\nimport org.springframework.test.context.TestPropertySource;"
      + "\nimport org.springframework.test.context.junit4.SpringJUnit4ClassRunner;"
      + "\n\nimport com.ad.server.repo.BaseRepoTest.TestApplication;"
      + "\n\n@SuppressWarnings({ \"rawtypes\" })"
      + "\n@RunWith(SpringJUnit4ClassRunner.class)"
      + "\n@SpringBootTest(classes = BaseRepoTest.TestApplication.class)"
      + "\n@TestPropertySource(\"classpath:test.properties\")"
      + "\n@DataJpaTest"
      + "\n@AutoConfigureTestDatabase(replace=Replace.NONE)"
      + "\npublic class {clazz}RepoMySqlIntegrationTest extends BaseRepoTest {"
      + "\n\n    @Autowired"
      + "\n    private {clazz}Repo repo;"
      + "\n\n    @Override"
      + "\n    protected JpaRepository getRepo() {"
      + "\n        return repo;"
      + "\n    }"
      + "\n\n}";

  @Test
  public void generateRepoTest() throws Exception {
    final Set<String> tables = getContentAsList("table.list");
    final Set<String> classNames = new HashSet<>(tables.size());
    for (final String table : tables) {
      final String clazz = getTypeName(Character.toUpperCase(table.charAt(0)) + table.substring(1));
      final String repo = REPO_TEMPLATE.replace("{clazz}", clazz);
      log.debug("Creating POJO for: {} == {}", table, clazz);
      writeTo(DIR, clazz + "RepoMySqlIntegrationTest", repo, false);
      classNames.add(clazz + "RepoMySqlIntegrationTest.java");
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
