package com.thecodealchemist.service;

import com.thecodealchemist.entity.Role;
import com.thecodealchemist.entity.User;
import com.thecodealchemist.model.UserLoginRequest;
import com.thecodealchemist.model.UserRegistrationRequest;
import com.thecodealchemist.repository.UserRepository;
import com.thecodealchemist.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    public boolean registerUser(UserRegistrationRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setName(userRequest.getName());
        user.setRole(Role.USER);

        userRepository.save(user);
        return true;
    }

    public String loginUser(UserLoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail());
        }
        return null;
    }
}
