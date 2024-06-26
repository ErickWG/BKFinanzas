package pe.edu.upc.bkfinanzas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.bkfinanzas.model.HistMovimiento;

import java.util.List;

@Repository
public interface HistMovimientoRepository extends JpaRepository<HistMovimiento, Integer> {
    List<HistMovimiento> findByClienteUserUsername(String username);

    List<HistMovimiento> findByClienteId(Integer id);
}

