package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.bkfinanzas.security.model.AuthResponse;
import pe.edu.upc.bkfinanzas.security.model.LoginRequest;
import pe.edu.upc.bkfinanzas.security.model.RegisterRequest;
import pe.edu.upc.bkfinanzas.service.AuthService;
import pe.edu.upc.bkfinanzas.service.UserService;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    // Generan un token
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));

    }
    // Endpoints para probar el token
    @GetMapping("/api/v1/show")
    public ResponseEntity<String> showDemo () {
        return new ResponseEntity<>("El token funciona correctamente uwu", HttpStatus.OK);
    }

    @GetMapping("/auth/check-dni/{dni}")
    public ResponseEntity<Boolean> checkDniExists(@PathVariable String dni) {
        boolean exists = userService.checkDniExists(dni);
        return ResponseEntity.ok(exists);
    }
}
