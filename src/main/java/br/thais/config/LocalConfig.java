package br.thais.config;

import br.thais.domain.User;
import br.thais.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        User user = new User(null, "Fulanito", "fulanito@com", "123");
        User user2 = new User(null, "Ciclanito", "ciclanito@com", "123");

        repository.saveAll(List.of(user, user2));
    }
}
