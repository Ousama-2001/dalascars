package com.example.dalascars.config;

import com.example.dalascars.entity.Brand;
import com.example.dalascars.entity.CarModel;
import com.example.dalascars.entity.Role;
import com.example.dalascars.entity.User;
import com.example.dalascars.repository.BrandRepository;
import com.example.dalascars.repository.CarModelRepository;
import com.example.dalascars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final CarModelRepository carModelRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Admin
        if (userRepository.findByEmail("zcontrol@outlook.fr").isEmpty()) {
            User admin = User.builder()
                    .firstName("Dalas")
                    .lastName("Admin")
                    .email("zcontrol@outlook.fr")
                    .password(passwordEncoder.encode("amir"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Seeder — Admin créé !");
        }

        if (brandRepository.count() > 0) return;

        // BMW
        Brand bmw = brandRepository.save(Brand.builder().name("BMW").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("Série 1").brand(bmw).build(),
                CarModel.builder().name("Série 3").brand(bmw).build(),
                CarModel.builder().name("Série 5").brand(bmw).build(),
                CarModel.builder().name("X3").brand(bmw).build(),
                CarModel.builder().name("X5").brand(bmw).build()
        ));

        // Mercedes
        Brand mercedes = brandRepository.save(Brand.builder().name("Mercedes").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("Classe A").brand(mercedes).build(),
                CarModel.builder().name("Classe C").brand(mercedes).build(),
                CarModel.builder().name("Classe E").brand(mercedes).build(),
                CarModel.builder().name("GLC").brand(mercedes).build(),
                CarModel.builder().name("GLE").brand(mercedes).build()
        ));

        // Audi
        Brand audi = brandRepository.save(Brand.builder().name("Audi").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("A1").brand(audi).build(),
                CarModel.builder().name("A3").brand(audi).build(),
                CarModel.builder().name("A4").brand(audi).build(),
                CarModel.builder().name("Q3").brand(audi).build(),
                CarModel.builder().name("Q5").brand(audi).build()
        ));

        // Volkswagen
        Brand vw = brandRepository.save(Brand.builder().name("Volkswagen").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("Golf").brand(vw).build(),
                CarModel.builder().name("Polo").brand(vw).build(),
                CarModel.builder().name("Passat").brand(vw).build(),
                CarModel.builder().name("Tiguan").brand(vw).build(),
                CarModel.builder().name("T-Roc").brand(vw).build()
        ));

        // Toyota
        Brand toyota = brandRepository.save(Brand.builder().name("Toyota").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("Yaris").brand(toyota).build(),
                CarModel.builder().name("Corolla").brand(toyota).build(),
                CarModel.builder().name("C-HR").brand(toyota).build(),
                CarModel.builder().name("RAV4").brand(toyota).build(),
                CarModel.builder().name("Prius").brand(toyota).build()
        ));

        // Renault
        Brand renault = brandRepository.save(Brand.builder().name("Renault").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("Clio").brand(renault).build(),
                CarModel.builder().name("Megane").brand(renault).build(),
                CarModel.builder().name("Captur").brand(renault).build(),
                CarModel.builder().name("Kadjar").brand(renault).build(),
                CarModel.builder().name("Zoe").brand(renault).build()
        ));

        // Peugeot
        Brand peugeot = brandRepository.save(Brand.builder().name("Peugeot").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("208").brand(peugeot).build(),
                CarModel.builder().name("308").brand(peugeot).build(),
                CarModel.builder().name("3008").brand(peugeot).build(),
                CarModel.builder().name("5008").brand(peugeot).build(),
                CarModel.builder().name("2008").brand(peugeot).build()
        ));

        // Ford
        Brand ford = brandRepository.save(Brand.builder().name("Ford").build());
        carModelRepository.saveAll(List.of(
                CarModel.builder().name("Fiesta").brand(ford).build(),
                CarModel.builder().name("Focus").brand(ford).build(),
                CarModel.builder().name("Kuga").brand(ford).build(),
                CarModel.builder().name("Puma").brand(ford).build(),
                CarModel.builder().name("Mustang").brand(ford).build()
        ));

        System.out.println("✅ Seeder — Marques et modèles insérés !");
    }
}