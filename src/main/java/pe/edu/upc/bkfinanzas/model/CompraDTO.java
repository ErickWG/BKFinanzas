package pe.edu.upc.bkfinanzas.model;

import java.util.List;

public class CompraDTO {
    private Integer clienteId;
    private TipoCreditoDTO tipoCredito;
    private List<DetalleCompraDTO> detallesCompra;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public TipoCreditoDTO getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(TipoCreditoDTO tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public List<DetalleCompraDTO> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompraDTO> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }
}
