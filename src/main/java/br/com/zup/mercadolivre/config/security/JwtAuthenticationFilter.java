package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenManager tokenManager;
    private UserService userService;

    public JwtAuthenticationFilter(TokenManager tokenManager, UserService userService) {
        this.tokenManager = tokenManager;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Optional<String> possibleToken = getTokenFromRequest(request);

        if (possibleToken.isPresent() && tokenManager.isValid(possibleToken.get())) {

            String userName = tokenManager.getUserName(possibleToken.get().split(" ")[1]);
            UserDetails userDetails = userService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");

        return Optional.ofNullable(authToken);
    }
}

