package pl.projekt.alekino.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.projekt.alekino.domain.user.AppUser;
import pl.projekt.alekino.domain.user.AppUserService;
import pl.projekt.alekino.domain.user.Role;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public TokenDto register(RegisterDto request) {
        AppUser user = AppUser.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authorities(Collections.singleton(Role.ROLE_USER))
                .build();
        userService.save(user);
        String jwtToken = tokenService.generateToken(user);
        return TokenDto.builder()
                .token(jwtToken)
                .build();
    }

    public TokenDto authenticate(LoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        AppUser user = userService.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = tokenService.generateToken(user);
        return TokenDto.builder()
                .token(jwtToken)
                .build();
    }
}