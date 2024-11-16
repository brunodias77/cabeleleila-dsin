package com.brunodias.dsin_cabeleleila_server.configurations.security.filters;

import com.brunodias.dsin_cabeleleila_server.configurations.security.users.ApplicationUserDetailsService;
import com.brunodias.dsin_cabeleleila_server.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationUserDetailsService _applicationUserDetailsService;

    @Autowired
    private TokenService _tokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = extractBearerToken(request);
            if(jwt != null && _tokenService.validateToken(jwt)){
                String email = _tokenService.getUserNameFromToken(jwt);
                UserDetails userDetails = _applicationUserDetailsService.loadUserByUsername(email);
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }catch (Exception e){
            logger.error("Não é possível definir a autenticação do usuário: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);

    }

    private String extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}

