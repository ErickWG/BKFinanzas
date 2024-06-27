package pe.edu.upc.bkfinanzas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

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
    public String SKU;

    @PrePersist
    private void generateSku() {
        this.SKU = generateRandomSku(10);
    }

    private String generateRandomSku(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sku = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sku.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sku.toString();
    }
}
