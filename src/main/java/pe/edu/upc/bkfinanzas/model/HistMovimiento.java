package pe.edu.upc.bkfinanzas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombrecompleto;
    private LocalDate fecha;
    private String descripcion;
    private Double subtotal;
    private String tasa_text;
    private Double tasa_num;
    private Integer cuotas;
    private Integer capitalizacion;

    //estado de pago ()
    private String estadopago = "Pendiente";



    @Column(nullable = true)
    private Double renta;

    @Column(nullable = true)
    private Double totalAPagar;

    @Column(nullable = true)
    private Integer diasTrasladar;

    @Column(nullable = true)
    private Double valorFuturo;

    @Column(nullable = true)
    private Double interes;

    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "compra_id")
    private Compra compra;
}
