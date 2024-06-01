package pe.edu.upc.bkfinanzas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.bkfinanzas.model.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
}
