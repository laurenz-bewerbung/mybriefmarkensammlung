package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Role;
import de.lm.mybriefmarkensammlung.domain.model.User;
import de.lm.mybriefmarkensammlung.dto.request.RegistrationRequest;
import de.lm.mybriefmarkensammlung.exception.NoSuchRoleException;
import de.lm.mybriefmarkensammlung.exception.NoSuchUserException;
import de.lm.mybriefmarkensammlung.exception.UserAlreadyExistsException;
import de.lm.mybriefmarkensammlung.repository.RoleRepository;
import de.lm.mybriefmarkensammlung.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchUserException(username));

        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new NoSuchRoleException(user.getRoleId()));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(role)
                .build();
    }

    public void registerUser(RegistrationRequest registrationRequest) {
        // exception if username not available
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(registrationRequest.getUsername());
        }

        // assign ROLE_USER to new User -> create this role if not present
        Role role = roleRepository.findByAuthority("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        
        // parse registerRequest to User and save to db
        User user = new User(registrationRequest.getUsername(), passwordEncoder.encode(registrationRequest.getPassword()), role.getId());
        userRepository.save(user);
    }

    public Long userIdByUsername(String username, boolean throwException) {
        Optional<Long> optId = userRepository.findIdByUsername(username);

        if (throwException) {
            return optId.orElseThrow(() -> new NoSuchUserException(username));
        }
        return optId.orElse(null);
    }

    public String usernameByUserId(Long id, boolean throwException) {
        Optional<String> optUsername = userRepository.findUsernameById(id);

        if (throwException) {
            return optUsername.orElseThrow(() -> new NoSuchUserException(id));
        }
        return optUsername.orElse(null);
    }
}
