package com.majumundur.Security.Config;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private JwtAuthFilter authFilter;

    public SecurityConfig(JwtAuthFilter authFilter){
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                //Allow Akses Api Docs & Swagger UI
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/api/swagger/**").permitAll()
                                //Allow akses untuk Login & Register
                                .requestMatchers("/auth/**").permitAll()
                                //GetAll() di RewardsController Tidak Perlu Authorized / Bisa Secara Anonymous
                                .requestMatchers(HttpMethod.GET,"/api/rewards").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/merchants/{merchantId}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/merchants/{merchantId}/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception{
        AuthenticationManager manager = auth.getAuthenticationManager();
        return manager;
    }
}
