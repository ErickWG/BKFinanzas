package pe.edu.upc.bkfinanzas.service;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.*;
import pe.edu.upc.bkfinanzas.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {
    @Autowired
    private final CompraRepository compraRepo;

    @Autowired
    private final ProductoRepository productoRepo;

    @Autowired
    private final DetalleCompraRepository detalleCompraRepo;

    @Autowired
    private final ClienteRepository clienteRepo;

    @Autowired
    private TipoCreditoRepository tipoCreditoRepo;

    public CompraService(CompraRepository compraRepo, ProductoRepository productoRepo, DetalleCompraRepository detalleCompraRepo, ClienteRepository clienteRepo, TipoCreditoRepository tipoCreditoRepo) {
        this.compraRepo = compraRepo;
        this.productoRepo = productoRepo;
        this.detalleCompraRepo = detalleCompraRepo;
        this.clienteRepo = clienteRepo;
        this.tipoCreditoRepo = tipoCreditoRepo;
    }

    // Insertar
    public Compra insert(Compra compra) {
        return compraRepo.save(compra);
    }

    // Listar
    public List<Compra> lsCompra() {
        return compraRepo.findAll();
    }

    // Eliminar
    public Compra eliminar(Integer id) throws Exception {
        Compra m = compraRepo.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("No se encontró el ID " + id));
        compraRepo.delete(m);
        return m;
    }

    // Modificar
    public Compra modifica(Compra compra) throws Exception {
        Compra com = compraRepo.findById(compra.getId())
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Id de la compra no existe"));
        return compraRepo.save(compra);
    }


    public Compra registrarCompra(Cliente cliente, TipoCredito tipoCredito, List<DetalleCompraDTO> detallesCompraDTO) {
        Compra compra = new Compra();
        compra.setFecha(LocalDate.now());
        compra.setCliente(cliente);
        compra.setTipocredito(tipoCredito);

        List<DetalleCompra> detallesCompra = new ArrayList<>();
        double montoTotal = 0.0;

        for (DetalleCompraDTO detalleDTO : detallesCompraDTO) {
            Producto producto = productoRepo.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleCompra detalleCompra = new DetalleCompra();
            detalleCompra.setProducto(producto);
            detalleCompra.setCantidad(detalleDTO.getCantidad());
            detalleCompra.setPrecioUnitario(producto.getPrecioventa());
            detalleCompra.setSubtotal(detalleDTO.getCantidad() * producto.getPrecioventa());
            detalleCompra.setCompra(compra); // Establecer la referencia a la compra

            detallesCompra.add(detalleCompra);
            montoTotal += detalleCompra.getSubtotal();
        }

        compra.setDetallesCompra(detallesCompra); // Establecer los detalles de compra en la compra
        compra.setMonto_total(montoTotal);

        return compraRepo.save(compra);
    }
}
