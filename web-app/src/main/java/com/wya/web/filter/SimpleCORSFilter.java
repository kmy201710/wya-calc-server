package com.wya.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
          throws IOException, ServletException {
      HttpServletResponse response=(HttpServletResponse)servletResponse;
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      response.setHeader("Access-Control-Allow-Origin","*");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
      response.setHeader("Access-Control-Max-Age", "3600");
//      response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");
      response.setHeader("Access-Control-Allow-Headers", "Origin, x-access-token, X-Requested-With, Content-Type, Accept, " +
              "WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");

      filterChain.doFilter(servletRequest,servletResponse);
  }

  @Override
  public void destroy() {

  }
}