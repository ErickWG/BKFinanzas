package pe.edu.upc.bkfinanzas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.Role;
import pe.edu.upc.bkfinanzas.model.User;
import pe.edu.upc.bkfinanzas.repository.UserRepository;
import pe.edu.upc.bkfinanzas.security.model.AuthResponse;
import pe.edu.upc.bkfinanzas.security.model.LoginRequest;
import pe.edu.upc.bkfinanzas.security.model.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .dni(request.getDni())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

}
