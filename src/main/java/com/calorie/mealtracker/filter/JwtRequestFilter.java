package com.calorie.mealtracker.filter;

import com.calorie.mealtracker.error.NoAuthorizationHeaderException;
import com.calorie.mealtracker.error.NoTokenFoundException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.service.JwtUtilService;
import com.calorie.mealtracker.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
@CrossOrigin
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String path = httpServletRequest.getRequestURI();
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse); // Just continue chain.
        }


        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                System.out.println("Header: " + name + " value:" + httpServletRequest.getHeader(name));
            }
        }

        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);

        try {

            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                throw new NoAuthorizationHeaderException();
            } else {
                String jwt = authorizationHeader.substring(7);

                if (jwt.isEmpty()) throw new NoTokenFoundException();

                String username = jwtUtilService.extractUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                    if (jwtUtilService.validateToken(jwt, userDetails)) {

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                        filterChain.doFilter(httpServletRequest, httpServletResponse);

                    }
                }
            }

        } catch (NoAuthorizationHeaderException e) {
            e.printStackTrace();
        } catch (NoTokenFoundException e) {
            e.printStackTrace();
        }
    }

}
