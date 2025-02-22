package ait.cohort49.hostel_casa_flamingo.security.filter;

import ait.cohort49.hostel_casa_flamingo.security.AuthInfo;
import ait.cohort49.hostel_casa_flamingo.security.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@Component
public class TokenFilter extends GenericFilterBean {
    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String accessToken = getTokenFromRequest(request);

        if ((accessToken != null && tokenService.validateAccessToken(accessToken))) {
            Claims claims = tokenService.getAccessClaimsFromToken(accessToken);
            AuthInfo authInfo = tokenService.mapClaimsToAuthInfo(claims);
            authInfo.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(authInfo);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
