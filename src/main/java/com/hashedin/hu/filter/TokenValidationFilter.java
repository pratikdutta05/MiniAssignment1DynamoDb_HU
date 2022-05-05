package com.hashedin.hu.filter;

import lombok.Data;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Data
public class TokenValidationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidationFilter.class);

    @Value("${application.ignore.list}")
    private String ignoreEndpoint;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request=(HttpServletRequest)req;

              List<String> list=Arrays.asList(ignoreEndpoint.split(","));

                System.out.println(request.getRequestURI());

            if( ( !list.contains(request.getRequestURI()) && request.getHeader("X-Auth-Token").isEmpty())){
                logger.info("Exception Occur X-Auth-Token is missing in header!{}",((HttpServletRequest) req).getRequestURI());
                HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
                ((HttpServletResponse) resp).setStatus(HttpStatus.SC_UNAUTHORIZED);
                ((HttpServletResponse) resp).sendError(HttpStatus.SC_UNAUTHORIZED,"X-Auth-Token Missing");
            }

            chain.doFilter(req, resp);

    }

    /**
     *
     */
    @Override
    public void destroy() {

    }
}
