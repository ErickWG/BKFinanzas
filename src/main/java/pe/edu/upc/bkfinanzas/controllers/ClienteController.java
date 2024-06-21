package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.bkfinanzas.model.Cliente;
import pe.edu.upc.bkfinanzas.model.User;
import pe.edu.upc.bkfinanzas.repository.ClienteRepository;
import pe.edu.upc.bkfinanzas.repository.UserRepository;
import pe.edu.upc.bkfinanzas.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/cliente")

public class ClienteController {
    @Autowired
    public final ClienteService clienteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClienteRepository clienteRepository;



    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> insert(@RequestBody Cliente cliente){
        return new ResponseEntity<>(clienteService.insert(cliente), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Cliente>> lsCliente(){
        return new ResponseEntity<>(clienteService.lsCliente(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> elimina (@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(clienteService.eliminar(id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Cliente> edita(@RequestBody Cliente cliente) throws Exception {
        return new ResponseEntity<>(clienteService.modifica(cliente), HttpStatus.OK);
    }

}
