package ru.fitness.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final boolean allowCors;
    private final String authStr;

    public AuthFilter(
            @Value("${fitness.web.allow-cors:false}") boolean allowCors,
            @Value("${fitness.auth.str}") String authStr) {
        this.allowCors = allowCors;
        this.authStr = "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        if (allowCors) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
        }
        if (!request.getMethod().equals("OPTIONS") && !authStr.equals(request.getHeader("Authorization"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
