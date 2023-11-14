package com.blogapp.blogapp.users;

import com.blogapp.blogapp.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest request) {
        UserEntity newUser = modelMapper.map(request, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        return usersRepository.save(newUser);
    }

    public UserEntity getUser(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public Iterable<UserEntity> getAllUsers() {
        return usersRepository.findAll();
    }

    public UserEntity getUser(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserEntity loginUser(String username, String password) {
        var user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        var passmatch = passwordEncoder.matches(password, user.getPassword());
        if(!passmatch) throw new InvalidCredentialsException();

        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username) {
            super("User with username: " + username + " not found");
        }

        public UserNotFoundException(Long userId) {
            super("User with Id: " + userId + " not found");
        }

    }

    public static class InvalidCredentialsException extends IllegalArgumentException {
        public InvalidCredentialsException() {
            super("Invalid Username or Password");
        }
    }
}
