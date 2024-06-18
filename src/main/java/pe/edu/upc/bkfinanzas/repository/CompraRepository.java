package pe.edu.upc.bkfinanzas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.bkfinanzas.model.Compra;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer> {

    @Query(value = "SELECT CONCAT(u.nombres, ' ', u.apellidos) AS nombrecompleto, c.fecha, STRING_AGG(pr.descripcion, ', ') AS descripcion, SUM(dc.subtotal) AS subtotal, tp.tasa_text, tp.tasa_num, tp.cuotas, tp.capitalizacion " +
            "FROM compra c " +
            "INNER JOIN cliente cl ON c.cliente_id = cl.id " +
            "INNER JOIN userfn u ON cl.user_id = u.id " +
            "INNER JOIN tipo_credito tp ON c.tipo_credito_id = tp.id " +
            "INNER JOIN detalle_compra dc ON c.id = dc.compra_id " +
            "INNER JOIN producto pr ON dc.producto_id = pr.id " +
            "GROUP BY nombrecompleto, c.fecha, tp.tasa_text, tp.tasa_num, tp.cuotas, tp.capitalizacion", nativeQuery = true)
    List<Object[]> getReporteCompra();
}
