package pe.edu.upc.bkfinanzas.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public String descripcion;
    public double precioventa;
    public double preciocosto;
    public Integer stock;
    public String categoria;
    public String proveedor;
    public String marca;

}
