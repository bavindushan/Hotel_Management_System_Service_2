// src/main/java/edu/esoft/com/controller/AuthController.java
package edu.esoft.com.controller;

import edu.esoft.com.dto.auth.LoginRequest;
import edu.esoft.com.dto.auth.LoginResponse;
import edu.esoft.com.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.signIn(req));
    }
}
