package by.slizh.plants.web.security;

import by.slizh.plants.domain.exception.ResourceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.LocalDateTime;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(LocalDateTime.now() + " " + ((HttpServletRequest) servletRequest).getMethod()
                + " " + ((HttpServletRequest) servletRequest).getRequestURI());
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            System.out.println(1);
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
            System.out.println(2);
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ResourceNotFoundException ignored) {
            }
        }
        System.out.println(3);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
