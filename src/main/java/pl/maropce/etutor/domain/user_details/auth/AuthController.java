package pl.maropce.etutor.domain.user_details.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.config.jwt.JwtUtil;
import pl.maropce.etutor.domain.user.dto.AppUserDTO;
import pl.maropce.etutor.domain.user.dto.AppUserMapper;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${app.front-end.url}")
    private String frontEndUrl;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final AppUserMapper appUserMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    ));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwt);
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDetails> registerUser(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {

        return ResponseEntity.ok(authService.register(registerRequest));

    }

    @GetMapping("/me")
    public ResponseEntity<AppUserDTO> getCurrentUser(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        if (appUserDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(appUserMapper.toDTO(appUserDetails.getAppUser()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        authService.changePassword(appUserDetails.getUsername(), request);
        return ResponseEntity.ok("Password changed successfully.");
    }

    @GetMapping("/activate")
    public ResponseEntity<Void> activateAccount(@RequestParam String token) {


        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(frontEndUrl))
                .build();

    }
}