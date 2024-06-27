package pe.edu.upc.bkfinanzas.model;

import java.time.LocalDate;

public class HistMovimientoDTO {

    public String nombrecompleto;
    public LocalDate fecha;
    public String descripcion;
    public Double subtotal;
    public String tasa_text;
    public Double tasa_num;
    public Integer cuotas;
    public Integer capitalizacion;


    //AGREGADO PARA SOLUCIONAR UN TEMITA (2:30AM)
    private Integer clienteId;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getCompraId() {
        return compraId;
    }

    public void setCompraId(Integer compraId) {
        this.compraId = compraId;
    }

    private Integer compraId;

    //AGREGADO PARA SOLUCIONAR UN TEMITA (2:30AM)

    public String getEstadopago() {
        return estadopago;
    }

    public void setEstadopago(String estadopago) {
        this.estadopago = estadopago;
    }

    public String estadopago; // Nuevo campo agregado

    public Double getRenta() {
        return renta;
    }

    public void setRenta(Double renta) {
        this.renta = renta;
    }

    public Double getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(Double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    private Double renta;
    private Double totalAPagar;

    public Integer getDiasTrasladar() {
        return diasTrasladar;
    }

    public void setDiasTrasladar(Integer diasTrasladar) {
        this.diasTrasladar = diasTrasladar;
    }

    public Double getValorFuturo() {
        return valorFuturo;
    }

    public void setValorFuturo(Double valorFuturo) {
        this.valorFuturo = valorFuturo;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    private Integer diasTrasladar; // Nuevo campo
    private Double valorFuturo; // Nuevo campo
    private Double interes; // Nuevo campo

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getTasa_text() {
        return tasa_text;
    }

    public void setTasa_text(String tasa_text) {
        this.tasa_text = tasa_text;
    }

    public Double getTasa_num() {
        return tasa_num;
    }

    public void setTasa_num(Double tasa_num) {
        this.tasa_num = tasa_num;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public Integer getCapitalizacion() {
        return capitalizacion;
    }

    public void setCapitalizacion(Integer capitalizacion) {
        this.capitalizacion = capitalizacion;
    }



    public HistMovimientoDTO(String nombrecompleto, LocalDate fecha, String descripcion, Double subtotal, String tasa_text, Double tasa_num, Integer cuotas, Integer capitalizacion) {
        this.nombrecompleto = nombrecompleto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.subtotal = subtotal;
        this.tasa_text = tasa_text;
        this.tasa_num = tasa_num;
        this.cuotas = cuotas;
        this.capitalizacion = capitalizacion;
    }

    public HistMovimientoDTO() {
    }


}
