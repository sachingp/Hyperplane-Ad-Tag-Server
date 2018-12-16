package com.ad.services;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdServicesHtmlWriter {

  public AdServicesHtmlWriter() {
    throw new AssertionError("Error while initializing the class");
  }


  /**
   * @return HTML data.
   */
  public static <T> String getMarkUp(String baseURL, final String display, final Set<T> params) {
    log.info("Preparing HTML for {} -- {}", baseURL, display);
    StringBuilder builder = new StringBuilder();
    builder.append(
        "<!DOCTYPE html><html lang=" + "\"" + "en" + "\"" + "><head>   <meta charset=" + "\""
            + "UTF-8" + "\"" + ">  <title>Uber - Ad Services</title></head><body BGCOLOR=" + "\""
            + "FFFFFF" + "\"" + "><div><img src=" + "\""
            + "https://uber.logo/uber.jpg" + "\"" + " style=" + "\""
            + "width:140px;" + "\"" + "><hr style=" + "\"" + "box-shadow: 0 0 10px 1px black;"
            + "\"" + "></hr><h1 align=" + "\"" + "center" + "\"" + " style=" + "\""
            + "font-size:200%;" + "\"" + " >" + display + "</h1><h3></h3>");
    builder.append("<p style=" + "\"" + "font-size:90%;" + "\"" + "><ul>");
    params.forEach(param -> {
      builder.append(
          "<li><a href=\"" + baseURL + "/" + param + "\">" + param
              + "</a></li>");
    });
    builder.append("</ul></p></div></body></html>");
    log.info("Prepared HTML: {}", builder);
    return builder.toString();
  }

}
