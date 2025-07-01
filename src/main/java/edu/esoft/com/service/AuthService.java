package edu.esoft.com.service;

import edu.esoft.com.dto.auth.LoginRequest;
import edu.esoft.com.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse signIn(LoginRequest request);
}
