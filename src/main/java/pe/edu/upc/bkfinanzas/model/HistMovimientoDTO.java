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
