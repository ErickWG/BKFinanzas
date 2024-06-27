package pe.edu.upc.bkfinanzas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.bkfinanzas.model.Cliente;
import pe.edu.upc.bkfinanzas.model.User;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    Optional<Cliente> findClienteByUser_Username (String username);
    
}
