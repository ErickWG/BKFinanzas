package pe.edu.upc.bkfinanzas.model;

import java.util.List;

public class CompraDTO {
    private Integer clienteId;
    private Integer tipoCreditoId;
    private List<DetalleCompraDTO> detallesCompra;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getTipoCreditoId() {
        return tipoCreditoId;
    }

    public void setTipoCreditoId(Integer tipoCreditoId) {
        this.tipoCreditoId = tipoCreditoId;
    }

    public List<DetalleCompraDTO> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompraDTO> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }
}
