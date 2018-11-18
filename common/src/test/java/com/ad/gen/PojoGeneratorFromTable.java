package com.ad.gen;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class PojoGeneratorFromTable extends BaseGeneratorUtil {

  private static final String DIR = "src/main/java/com/ad/server/pojo";
  private static final String DB_URL = "jdbc:mysql://localhost/ad_server";
  private static final String USER = "root";
  private static final String PASS = "root";
  private static final String FIELD_TEMPLATE = "    @Column (name=\"{name}\")\n    private {type} {name_cc};\n";
  private static final String POJO_TEMPLATE = "package com.ad.server.pojo;"
      + "\n\nimport java.io.Serializable;"
      + "{date_import}"
      + "\n\nimport javax.persistence.Column;"
      + "\nimport javax.persistence.Entity;"
      + "\nimport javax.persistence.Id;"
      + "\nimport javax.persistence.Table;"
      + "\n\nimport lombok.AllArgsConstructor;"
      + "\nimport lombok.Data;"
      + "\nimport lombok.NoArgsConstructor;"
      + "\n\n@Data"
      + "\n@AllArgsConstructor"
      + "\n@NoArgsConstructor"
      + "\n@Entity"
      + "\n@Table (name = \"{table}\")"
      + "\npublic class {clazz} implements Serializable {"
      + "\n\n    private static final long serialVersionUID = 1L;"
      + "\n\n{fields}"
      + "}";
  private static final String DATE_IMPORT = "\nimport java.util.Date;";
  private static Connection conn;
  private static Statement stmt;

  @BeforeClass
  public static void init() throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(DB_URL, USER, PASS);
    stmt = conn.createStatement();
  }

  @AfterClass
  public static void end() throws Exception {
    stmt.close();
    conn.close();
  }

  @Test
  public void generatePojoTest() throws Exception {
    final Set<String> tables = getContentAsList("table.list");
    final Set<String> classNames = new HashSet<>(tables.size());
    for (final String table : tables) {
      final String clazz = getTypeName(Character.toUpperCase(table.charAt(0)) + table.substring(1));
      final String query = "desc " + table;
      final ResultSet results = stmt.executeQuery(query);
      boolean includeDate = false;
      final StringBuilder fields = new StringBuilder();
      while (results.next()) {
        final String column = results.getString("Field");
        final String type = results.getString("Type");
        final String key = results.getString("Key");
        String field = "";
        if ("PRI".equals(key)) {
          field = "    @Id\n";
        }
        final String fieldType = getType(type);
        if (!includeDate && "Date".equals(fieldType)) {
          includeDate = true;
        }
        field += FIELD_TEMPLATE.replace("{name}", column).replace("{name_cc}", getTypeName(column))
            .replace("{type}", fieldType);
        fields.append(field).append("\n");
      }
      final String pojo = POJO_TEMPLATE.replace("{clazz}", clazz).replace("{table}", table)
          .replace("{fields}", fields).replace("{date_import}", includeDate ? DATE_IMPORT : "");
      log.debug("Creating POJO for: {}", clazz);
      writeTo(DIR, clazz, pojo, false);
      classNames.add(clazz + ".java");
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

  private String getType(final String type) {
    final String temp;
    final int index;
    if ((index = type.indexOf('(')) != -1) {
      temp = type.substring(0, index);
    } else {
      temp = type;
    }
    switch (temp) {
      case "int":
        return "Integer";
      case "bigint":
        return "Long";
      case "smallint":
        return "Short";
      case "float":
        return "Float";
      case "double":
        return "Double";
      case "datetime":
      case "timestamp":
        return "Date";
      default:
        return "String";
    }
  }

}
