package com.spring.security.filter;

import com.spring.security.config.UserInfoDetailService;
import com.spring.security.utility.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    UserInfoDetailService userInfoDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            username = jwtUtility.getUsernameFromToken(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userInfoDetailService.loadUserByUsername(username);

            try {
                if (jwtUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    // Token validation failed
                    // Return an HTTP 401 Unauthorized response with an error message
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation failed");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token validation failed");

                    return;

                }
            } catch (ExpiredJwtException e) {
                // Token has expired
                // Return an HTTP 401 Unauthorized response with an error message
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token validation failed");

                return;

            } catch (Exception ex) {
                // Token validation failed for other reasons
                // Return an HTTP 401 Unauthorized response with an error message
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation failed");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token validation failed");

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader("Authorization");
//        String username = null;
//        String token = null;
//
//        if (header != null && header.startsWith("Bearer ")) {
//            token = header.substring(7);
//            username = jwtUtility.getUsernameFromToken(token);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userInfoDetailService.loadUserByUsername(username);
//            try {
//                if (jwtUtility.validateToken(token, userDetails)) {
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                } else {
//                    // Token validation failed
//                    // Return an HTTP 401 Unauthorized response with an error message
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                    return;
//                }
//            } catch (Exception ex) {
//                // Token validation failed
//                // Return an HTTP 401 Unauthorized response with an error message
//                    System.out.println("Invalid token");
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader("Autherization");
//        String username=null;
//        String token=null;
//
//        if(header!=null && header.startsWith("Bearer "))
//        {
//            token = header.substring(7);
//           username = jwtUtility.getUsernameFromToken(token);
//        }
//
//        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//           UserDetails userDetails = userInfoDetailService.loadUserByUsername(username);
//           if(jwtUtility.validateToken(token,userDetails)){
//               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                       new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//               usernamePasswordAuthenticationToken.setDetails(
//                       new WebAuthenticationDetailsSource()
//                               .buildDetails(request)
//               );
//               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//           };
//        };
//        filterChain.doFilter(request,response);
//    }
