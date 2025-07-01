// src/main/java/edu/esoft/com/service/impl/AuthServiceImpl.java
package edu.esoft.com.service.impl;

import edu.esoft.com.dto.auth.LoginRequest;
import edu.esoft.com.dto.auth.LoginResponse;
import edu.esoft.com.entity.User;
import edu.esoft.com.repository.UserRepository;
import edu.esoft.com.security.JwtUtils;
import edu.esoft.com.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse signIn(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String role = user.getRole().getRoleName();       // ADMIN / RECEPTIONIST
        String token = jwtUtils.generateToken(
                String.valueOf(user.getId()),
                user.getEmail(),
                role
        );

        return new LoginResponse(token, role);
    }
}
