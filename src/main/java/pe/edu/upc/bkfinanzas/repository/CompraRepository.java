package pe.edu.upc.bkfinanzas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.bkfinanzas.model.Compra;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer> {


    //FALTA AGREGAR EL DTO PARA ESTA QUERY, AQUI ME QUEDE "CAMILO"
    @Query(value = "SELECT c.id, CONCAT(u.nombres, ' ', u.apellidos) AS nombrecompleto, c.fecha, STRING_AGG(pr.descripcion, ', ') AS descripcion, SUM(dc.subtotal) AS subtotal, tp.tasa_text, tp.tasa_num, tp.cuotas, tp.capitalizacion\n" +
            "FROM compra c\n" +
            "INNER JOIN cliente cl ON c.cliente_id = cl.id\n" +
            "INNER JOIN userfn u ON cl.user_id = u.id\n" +
            "INNER JOIN tipo_credito tp ON c.tipo_credito_id = tp.id\n" +
            "INNER JOIN detalle_compra dc ON c.id = dc.compra_id\n" +
            "INNER JOIN producto pr ON dc.producto_id = pr.id\n" +
            "GROUP BY c.id, nombrecompleto, c.fecha, tp.tasa_text, tp.tasa_num, tp.cuotas, tp.capitalizacion;", nativeQuery = true)
    List<String[]> getReporteCompra();
}
