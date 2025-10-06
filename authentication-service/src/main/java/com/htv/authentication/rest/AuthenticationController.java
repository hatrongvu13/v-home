package com.htv.authentication.rest;

import com.htv.authentication.model.request.authentication.LoginRequest;
import com.htv.authentication.model.request.register.RegisterRequest;
import com.htv.authentication.model.response.authentication.TokenResponse;
import com.htv.authentication.services.authentication.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) throws JOSEException {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest registerRequest) throws JOSEException {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
}
