package com.dailydreams.dailydreams.data;

import com.dailydreams.dailydreams.model.Role;
import com.dailydreams.dailydreams.model.User;
import com.dailydreams.dailydreams.repository.RoleRepository;
import com.dailydreams.dailydreams.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> roles = Set.of("ROLE_USER", "ROLE_ADMIN");
         createDefaultUserIfNotExist();
         createRoleIfNotExist(roles);
         createDefaultAdminIfNotExist();


    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
    private void createDefaultUserIfNotExist() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();

        for (int i= 1;i<=5;i++){

            String defaultEmail = "user"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default yet user  "+i+" created successfully");
        }

    }
    private void createDefaultAdminIfNotExist() {

        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();


        for (int i= 1;i<=2;i++){
            String defaultEmail = "admin"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default Admin user  "+i+" created successfully");
        }

    }


    private void createRoleIfNotExist(Set<String> roles) {

            roles.stream()
                    .filter(role -> roleRepository.findByName(role).isEmpty()) // Ensure role doesn't exist
                    .map(Role::new) // Create a new Role object
                    .forEach(roleRepository::save); // Save the new Role
        }
    }


