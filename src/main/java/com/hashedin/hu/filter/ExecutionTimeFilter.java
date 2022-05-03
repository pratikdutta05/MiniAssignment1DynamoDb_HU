package com.hashedin.hu.filter;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.dsig.spec.XPathType;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;


@Component
@Data
@Order(1)
public class ExecutionTimeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeFilter.class);

    /**
     * @param filterConfig The configuration information associated with the
     *                     filter instance being initialised
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * @param req The request to process
     * @param resp The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        long time=0;
        Instant start = Instant.now();
        try {

            HttpServletRequest request=(HttpServletRequest)req;

            if(request.getHeader("X-Auth-Token").isEmpty()){
                throw new RuntimeException("Unauthorised Exception");
            }
            chain.doFilter(req, resp);

        } finally {
            Instant finish = Instant.now();
            time = Duration.between(start, finish).toMillis();
           HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.setHeader(
                    "X-TIME-TO-EXECUTE", String.valueOf(time));


            logger.info("{}: {} ms ", ((HttpServletRequest) req).getRequestURI(),  time);
        }
    }

    /**
     *
     */
    @Override
    public void destroy() {

    }
}
