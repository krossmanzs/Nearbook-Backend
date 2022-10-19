package com.perpus.go.config;

import com.perpus.go.model.user.Role;
import com.perpus.go.model.user.ktp.Agama;
import com.perpus.go.model.user.ktp.Kawin;
import com.perpus.go.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DummyData {
    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));

            userService.saveKawin(new Kawin(null,1,"Belum Kawin"));
            userService.saveKawin(new Kawin(null,2,"Kawin"));
            userService.saveKawin(new Kawin(null,3,"Cerai Hidup"));
            userService.saveKawin(new Kawin(null,4,"Cerai Mati"));

            userService.saveAgama(new Agama(null, "Islam"));
            userService.saveAgama(new Agama(null, "Kristen"));
            userService.saveAgama(new Agama(null, "Katolik"));
            userService.saveAgama(new Agama(null, "Budha"));
            userService.saveAgama(new Agama(null, "Hindu"));
            userService.saveAgama(new Agama(null, "Konghucu"));
        };
    }
}
