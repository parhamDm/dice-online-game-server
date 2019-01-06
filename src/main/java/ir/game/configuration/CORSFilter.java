package ir.game.configuration;


import io.swagger.models.HttpMethod;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
//        if(request.getMethod().equals(HttpMethod.OPTIONS.name())){
//            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
//        }

            chain.doFilter(req, res);

    }
    public void destroy() {}
}
