package com.htv.authentication.services.authentication;

import com.htv.authentication.model.dto.authentication.UserAuthentication;
import com.htv.authentication.model.entity.UserEntity;
import com.htv.authentication.model.request.authentication.LoginRequest;
import com.htv.authentication.model.request.register.RegisterRequest;
import com.htv.authentication.model.response.authentication.TokenResponse;
import com.htv.authentication.repository.UserRepository;
import com.htv.authentication.utils.jwt.TokenProvider;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email()) || userRepository.existsByUsername(registerRequest.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tài khoản người dùng hoặc email của bạn đã tồn tại. Vui lòng đăng nhập hoặc sử dụng tài khoản khác");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerRequest.email());
        userEntity.setUsername(registerRequest.username());
        userEntity.setFullName(registerRequest.fullName());
        userEntity.setPassword(passwordEncoder.encode(registerRequest.password()));
        userRepository.save(userEntity);
        try {
            return buildToken(userEntity);
        } catch (JOSEException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Xảy ra lỗi trong quá trình đăng ký, vui lòng liên hệ admin: 0943-561-685");
        }
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByUsernameOrEmail(loginRequest.username(), loginRequest.email()).orElse(null);
        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản mật khẩu chưa tồn tại");
        }
        if (!passwordEncoder.matches(loginRequest.password(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tài khoản hoặc mật khẩu chưa đúng");
        }
        try {
            return buildToken(userEntity);
        } catch (JOSEException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi đăng nhập!");
        }
    }

    private TokenResponse buildToken(UserEntity userEntity) throws JOSEException {
        String token = tokenProvider.createToken(new UserAuthentication(
                userEntity.getId().toString(),
                        userEntity.getUsername(),
                        userEntity.getEmail(), null), false );
        return new TokenResponse(token);
    }
}
