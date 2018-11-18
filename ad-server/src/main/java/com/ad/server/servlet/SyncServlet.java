package com.ad.server.servlet;

import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sagupta
 */


public class SyncServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setStatus(HttpStatus.OK_200);
    resp.getWriter().println("EmbeddedJetty");
  }

}
