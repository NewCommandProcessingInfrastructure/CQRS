package maker.checker.config;

import maker.checker.repository.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  PasswordEncoder encoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService service(AppUserRepository users){
    return username -> users.findByUsername(username)
            .map(u -> User.withUsername(u.getUsername())
                    .password(u.getPassword())
                    .disabled(!u.isEnabled())
                    .authorities(u.getRoles().stream()
                            .map(Enum::name)
                            .map(SimpleGrantedAuthority::new)
                            .toList())
                    .build())
            .orElseThrow(() -> new UsernameNotFoundException("Username Not Found!"));
  }

  @Bean
  DaoAuthenticationProvider authProvider(UserDetailsService uds, PasswordEncoder enc){
    DaoAuthenticationProvider p = new DaoAuthenticationProvider(uds);
    p.setPasswordEncoder(enc);
    return p;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers("/api/public/**")
                            .permitAll().anyRequest().authenticated()
            ).httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
