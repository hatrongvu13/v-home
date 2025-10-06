package com.htv.authentication.services.authentication;

import com.htv.authentication.model.request.authentication.LoginRequest;
import com.htv.authentication.model.request.register.RegisterRequest;
import com.htv.authentication.model.response.authentication.TokenResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    TokenResponse register(RegisterRequest registerRequest) throws JOSEException;
    TokenResponse login(LoginRequest loginRequest) throws JOSEException;
}
