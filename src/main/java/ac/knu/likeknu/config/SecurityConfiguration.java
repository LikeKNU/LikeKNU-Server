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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**", "/actuator/health").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/admin/login")
                        .defaultSuccessUrl("/admin/messages")
                        .failureUrl("/admin/login?error=1")
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/admin/logout")
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
