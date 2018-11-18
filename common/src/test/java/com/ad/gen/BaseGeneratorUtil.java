package com.ad.gen;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BaseGeneratorUtil {

  protected void writeTo(final String dir, final String name, final String content,
      final boolean overwrite) throws Exception {
    final File file = new File(dir + "/" + name + ".java");
    log.debug(name);
    log.debug(content);
    if (file.exists() && !overwrite) {
      return;
    }
    try (final Writer writer = new FileWriter(file);
        final BufferedWriter buff = new BufferedWriter(writer);) {
      buff.append(content);
      buff.flush();
    }
  }

  protected Set<String> getContentAsList(final String filename) throws Exception {
    final String dir = "src/test/resources/";
    final Set<String> tables = new HashSet<>();
    try (final Reader reader = new FileReader(dir + filename);
        final BufferedReader buff = new BufferedReader(reader)) {
      String line;
      while ((line = buff.readLine()) != null) {
        if (line.startsWith("#")) {
          continue;
        }
        tables.add(line.trim());
      }
    }
    return tables;
  }

  protected String getTypeName(final String name) {
    if (name.indexOf("_") == -1) {
      return name;
    }
    final StringBuilder type = new StringBuilder();
    final char[] array = name.toCharArray();
    for (int i = 0; i < array.length; i++) {
      if ('_' == array[i]) {
        i++;
        type.append(Character.toUpperCase(array[i]));
      } else {
        type.append(array[i]);
      }
    }
    return type.toString();
  }

}
