package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.bkfinanzas.model.Cliente;
import pe.edu.upc.bkfinanzas.model.User;
import pe.edu.upc.bkfinanzas.service.UserService;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> lsUsers(){
        return new ResponseEntity<>(userService.listarUsuariosUSER(), HttpStatus.OK);
    }
}
