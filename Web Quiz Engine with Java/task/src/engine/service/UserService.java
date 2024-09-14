package engine.service;

import engine.domain.RegisterUserRequest;
import engine.domain.User;
import engine.exception.UserEmailAlreadyExist;
import engine.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void create(final RegisterUserRequest userRequest) {
        Optional
                .of(userRequest)
                .filter(userRequest1 -> !userRepository.existsByEmail(userRequest1.getEmail()))
                .map(userRequest1 -> new User(userRequest1.getEmail(), bCryptPasswordEncoder.encode(userRequest.getPassword())))
                .map(userRepository::save)
                .orElseThrow(() -> new UserEmailAlreadyExist("User with that email already exist"));
    }
}
