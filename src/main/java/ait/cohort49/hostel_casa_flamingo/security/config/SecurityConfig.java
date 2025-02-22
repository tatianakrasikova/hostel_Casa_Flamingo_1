package ait.cohort49.hostel_casa_flamingo.security.config;


import ait.cohort49.hostel_casa_flamingo.security.filter.TokenFilter;
import ait.cohort49.hostel_casa_flamingo.security.handler.AuthAccessDeniedHandler;
import ait.cohort49.hostel_casa_flamingo.security.handler.AuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final TokenFilter tokenFilter;

    public SecurityConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/users"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth-test/no-auth").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/auth/login",
                                "/auth/refresh",
                                "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/beds/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/rooms/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(
                        exception -> exception
                                .defaultAuthenticationEntryPointFor(
                                        new AuthenticationEntryPointHandler(),
                                        new AntPathRequestMatcher("/**")
                                )
                                .accessDeniedHandler(new AuthAccessDeniedHandler()));
        return http.build();
    }
}
