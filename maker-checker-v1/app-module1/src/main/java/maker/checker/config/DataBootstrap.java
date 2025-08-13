package maker.checker.config;

import java.util.Set;
import maker.checker.data.AppUser;
import maker.checker.data.RoleName;
import maker.checker.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataBootstrap {

  @Bean
  CommandLineRunner seedUsers(AppUserRepository repo, PasswordEncoder enc) {
    return args -> {
      if(!repo.existsByUsername("maker")){
        AppUser u = new AppUser();
        u.setUsername("maker");
        u.setPassword(enc.encode("password"));
        u.setRoles(Set.of(RoleName.ROLE_MAKER));
        repo.save(u);
      }
      if(!repo.existsByUsername("checker")){
        AppUser u = new AppUser();
        u.setUsername("checker");
        u.setPassword(enc.encode("password"));
        u.setRoles(Set.of(RoleName.ROLE_CHECKER));
        repo.save(u);
      }
      if(!repo.existsByUsername("name1")){
        AppUser u = new AppUser();
        u.setUsername("name1");
        u.setPassword(enc.encode("password"));
        u.setRoles(Set.of(RoleName.ROLE_MAKER, RoleName.ROLE_CHECKER));
        repo.save(u);
      }
    };
  }
}
