package pl.maropce.etutor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import pl.maropce.etutor.config.jwt.JwtAuthenticationFilter;
import pl.maropce.etutor.domain.user_details.Role;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${app.front-end.url}")
    private String frontEndUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {

        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration corsConfig = new CorsConfiguration();
                            corsConfig.setAllowedOrigins(
                                    List.of(frontEndUrl)
                            );
                            corsConfig.setAllowedMethods(
                                    List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                            );
                            corsConfig.setAllowedHeaders(List.of("*"));
                            corsConfig.setAllowCredentials(true);
                            return corsConfig;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/invitation/generate-code").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers("/api/invitation/join/*").authenticated()
                        .requestMatchers("/api/invitation/my-codes").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/lessons").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/lessons").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/lessons/*").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/lessons/*").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST, "/api/quizzes").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/quizzes/*").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/quizzes").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/*").hasAnyAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/*").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/reviews/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/reviews/user/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/author/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/reviewer/*").authenticated()

                        .requestMatchers(HttpMethod.POST, "api/solved-quizzes").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/solved-quizzes").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/solved-quizzes/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/solved-quizzes/user/*").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())



                        .requestMatchers(HttpMethod.PATCH, "/api/users/*/quizzes/*").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/users/*/quizzes").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/users/contacts").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/users/contacts/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/contacts/delete/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/*").hasAuthority(Role.ADMIN.name())



                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/activate").permitAll()

                        .requestMatchers("/h2-console/**", "/docs", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
