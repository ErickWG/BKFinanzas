package pe.edu.upc.bkfinanzas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.User;
import pe.edu.upc.bkfinanzas.repository.UserRepository;

import java.util.List;
import java.util.Optional;
@Service

public class UserService {
    @Autowired
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> listarUsuariosUSER(){return userRepository.findAll();}

    public Optional<User> buscarPorUsername(String username){return userRepository.findByUsername(username);}


    public boolean checkDniExists(String dni) {
        return userRepository.existsByDni(dni);
    }

}
