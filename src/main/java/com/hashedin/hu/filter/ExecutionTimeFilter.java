package com.hashedin.hu.filter;

import com.hashedin.hu.exception.UnauthorisedException;
import lombok.Data;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;


@Component
@Data
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
                logger.info("Exception Occur X-Auth-Token is missing in header!{}",((HttpServletRequest) req).getRequestURI());
                HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
                ((HttpServletResponse) resp).setStatus(HttpStatus.SC_UNAUTHORIZED);
                throw new UnauthorisedException("X-Auth-Token Missing");
            }


        } finally {

            Instant finish = Instant.now();
            time = Duration.between(start, finish).toMillis();
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.setHeader(
                    "X-TIME-TO-EXECUTE", String.valueOf(time));
            logger.info("{}: Execution Time {} ms ", ((HttpServletRequest) req).getRequestURI(),  time);

            chain.doFilter(req, resp);

        }
    }

    /**
     *
     */
    @Override
    public void destroy() {

    }
}
