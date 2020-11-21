package com.msaccess.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParamAuthenticationFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;

        Principal principal = httpServletRequest.getUserPrincipal();

        if (principal != null) {
            if (principal.getName().equalsIgnoreCase("testUser")) {
                if (req.getParameterMap().containsKey("fromDate") ||
                        req.getParameterMap().containsKey("toDate") ||
                        req.getParameterMap().containsKey("fromAmount") ||
                        req.getParameterMap().containsKey("toAmount")) {

                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpServletResponse.setContentType("application/json");
                    //pass down the actual obj that exception handler normally send

                    Map<String, Object> resp = new HashMap<>();
                    resp.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                    resp.put("error", "Operation Not Allowed.");

                    ObjectMapper mapper = new ObjectMapper();
                    PrintWriter out = res.getWriter();
                    out.print(mapper.writeValueAsString(resp));
                    out.flush();
                    return;
                }
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
