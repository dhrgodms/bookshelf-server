package bookshelf.renewal.common.config;

import bookshelf.renewal.common.auth.JwtTokenFilter;
import bookshelf.renewal.common.auth.JwtTokenProvider;
import bookshelf.renewal.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expiration}")
    private int expiration;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secretKey, expiration);
    }

    @Bean
    public PasswordEncoder makePassword() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(MemberRepository memberRepository) {
        return new JwtTokenFilter(memberRepository);
    }

    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity, JwtTokenFilter jwtTokenFilter) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a.
                        requestMatchers( "/swagger-ui/**","/v3/api-docs/**", "/join", "/login", "/logout" ,"/api/v1/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:9000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
