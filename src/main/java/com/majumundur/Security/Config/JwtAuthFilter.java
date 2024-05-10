package com.majumundur.Security.Config;

import com.majumundur.Security.Services.UserCredentialsService;
import com.majumundur.Security.Services.UserCredentialsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserCredentialsService credentialService;

    public JwtAuthFilter(JwtUtils jwtUtils, UserCredentialsServiceImpl credentialService) {
        this.jwtUtils = jwtUtils;
        this.credentialService = credentialService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String token = convertJwt(request);
            if(token != null && jwtUtils.verifyToken(token)){
                JwtClaim userInfo = jwtUtils.getUserInfoByToken(token);
                UserDetails userDetails = credentialService.loadByUserId(userInfo.getUserId());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authentication.setDetails(userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            log.error("Cannot Set User Auth : {}", e.getMessage());
        }

        filterChain.doFilter(request,response);
    }

    private String convertJwt(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}

