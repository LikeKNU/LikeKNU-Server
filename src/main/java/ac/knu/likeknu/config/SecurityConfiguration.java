package ac.knu.likeknu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector handlerMappingIntrospector)
            throws Exception {
        RequestMatcher requestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, "/api/**");
        return http.authorizeHttpRequests(registry -> registry.requestMatchers(requestMatcher).permitAll()
                        .anyRequest().authenticated())
                .formLogin(security -> security.loginPage("/admin/login")
                        .defaultSuccessUrl("/admin/messages")
                        .failureUrl("/admin/login?error=1")
                        .permitAll())
                .logout(security -> security.logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login"))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails admin = User.withUsername(username)
                .password(password)
                .passwordEncoder(encoder::encode)
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
