package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Role;
import de.lm.mybriefmarkensammlung.domain.model.User;
import de.lm.mybriefmarkensammlung.dto.request.RegistrationRequest;
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
        Optional<User> optUser = userRepository.findByUsername(username);

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        User user = optUser.get();
        Role role = roleRepository.findById(user.getRoleId()).orElseThrow();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(role)
                .build();
    }

    public void registerUser(RegistrationRequest registrationRequest) {
        Optional<Role> optRole = roleRepository.findByAuthority("ROLE_USER");

        Role role = null;
        if (optRole.isPresent()) {
            role = optRole.get();
        }
        else {
            role = initRole("ROLE_USER");
        }

        User user = new User(registrationRequest.getUsername(), passwordEncoder.encode(registrationRequest.getPassword()), role.getId());
        userRepository.save(user);
    }

    public Long userIdByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow().getId();
    }

    public String usernameByUserId(Long id) {
        return userRepository.findById(id).orElseThrow().getUsername();
    }

    private Role initRole(String authority) {
        return roleRepository.save(new Role(authority));
    }
}
