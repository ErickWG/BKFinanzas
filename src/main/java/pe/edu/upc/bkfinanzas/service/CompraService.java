package pe.edu.upc.bkfinanzas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.*;
import pe.edu.upc.bkfinanzas.repository.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private final HistMovimientoRepository histMovimientoRepo;

    public CompraService(CompraRepository compraRepo, ProductoRepository productoRepo, DetalleCompraRepository detalleCompraRepo, ClienteRepository clienteRepo, TipoCreditoRepository tipoCreditoRepo, HistMovimientoRepository histMovimientoRepo) {
        this.compraRepo = compraRepo;
        this.productoRepo = productoRepo;
        this.detalleCompraRepo = detalleCompraRepo;
        this.clienteRepo = clienteRepo;
        this.tipoCreditoRepo = tipoCreditoRepo;
        this.histMovimientoRepo = histMovimientoRepo;
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
        Compra m = compraRepo.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el ID " + id));
        compraRepo.delete(m);
        return m;
    }

    // Modificar
    public Compra modifica(Compra compra) throws Exception {
        Compra com = compraRepo.findById(compra.getId())
                .orElseThrow(() -> new RuntimeException("Id de la compra no existe"));
        return compraRepo.save(compra);
    }

    // Registrar compra y guardar historial de movimientos
    public Compra registrarCompra(Cliente cliente, TipoCreditoDTO tipoCreditoDTO, List<DetalleCompraDTO> detallesCompraDTO) {
        // Crear un nuevo TipoCredito a partir del DTO
        TipoCredito tipoCredito = new TipoCredito();
        tipoCredito.setTasaNum(tipoCreditoDTO.getTasaNum());
        tipoCredito.setTasaText(tipoCreditoDTO.getTasaText());
        tipoCredito.setCuotas(tipoCreditoDTO.getCuotas());
        tipoCredito.setCapitalizacion(tipoCreditoDTO.getCapitalizacion());
        tipoCreditoRepo.save(tipoCredito);

        // Crear la compra
        Compra compra = new Compra();
        compra.setFecha(LocalDate.now());
        compra.setCliente(cliente);
        compra.setTipocredito(tipoCredito);

        List<DetalleCompra> detallesCompra = new ArrayList<>();
        double montoTotal = 0.0;
        List<String> descripciones = new ArrayList<>();

        for (DetalleCompraDTO detalleDTO : detallesCompraDTO) {
            Producto producto = productoRepo.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleCompra detalleCompra = new DetalleCompra();
            detalleCompra.setProducto(producto);
            detalleCompra.setCantidad(detalleDTO.getCantidad());
            detalleCompra.setPrecioUnitario(producto.getPrecioventa());
            detalleCompra.setSubtotal(detalleDTO.getCantidad() * producto.getPrecioventa());
            detalleCompra.setCompra(compra);

            detallesCompra.add(detalleCompra);
            montoTotal += detalleCompra.getSubtotal();

            // Añadir la descripción del producto al historial
            descripciones.add(detalleDTO.getCantidad() + " " + producto.getDescripcion());
        }

        compra.setDetallesCompra(detallesCompra);
        compra.setMonto_total(montoTotal);

        // Restar el monto total al crédito del cliente
        if (cliente.getLimite_credito() < montoTotal) {
            throw new RuntimeException("Crédito insuficiente para realizar la compra");
        }
        cliente.setLimite_credito(cliente.getLimite_credito() - montoTotal);
        clienteRepo.save(cliente); // Guardar los cambios en el cliente

        // Guardar la compra
        compraRepo.save(compra);

        // Calcular los valores adicionales
        int diasTrasladar = calcularDiasTrasladar(compra.getFecha(), cliente.getFecha_pago_mensual());
        Double renta = null;
        Double totalAPagar = null;
        Double valorFuturo = null;
        Double interes = null;

        if (tipoCredito.getCuotas() == 1) {
            valorFuturo = calcularValorFuturo(montoTotal, tipoCredito.getTasaNum() / 100, diasTrasladar, tipoCredito.getTasaText(), tipoCredito.getCapitalizacion());
            interes = valorFuturo - montoTotal;
        } else {
            double tep = tipoCredito.getTasaText().equals("Nominal") ? calcularTEP(tipoCredito.getTasaNum() / 100, 30 / tipoCredito.getCapitalizacion()) : tipoCredito.getTasaNum() / 100;
            renta = calcularRenta(montoTotal, tep, tipoCredito.getCuotas());
            totalAPagar = renta * tipoCredito.getCuotas();
            valorFuturo = totalAPagar;
            interes = valorFuturo - montoTotal;
        }

        // Crear y guardar el historial de movimientos
        HistMovimiento histMovimiento = new HistMovimiento();
        histMovimiento.setNombrecompleto(cliente.getUser().getUsername());
        histMovimiento.setFecha(compra.getFecha());
        histMovimiento.setDescripcion(String.join(", ", descripciones));
        histMovimiento.setSubtotal(montoTotal);
        histMovimiento.setTasa_text(tipoCredito.getTasaText());
        histMovimiento.setTasa_num(tipoCredito.getTasaNum());
        histMovimiento.setCuotas(tipoCredito.getCuotas());
        histMovimiento.setCapitalizacion(tipoCredito.getCapitalizacion());
        histMovimiento.setCliente(cliente);
        histMovimiento.setCompra(compra);
        histMovimiento.setEstado("Pendiente");
        histMovimiento.setEstadopago("Pendiente"); // Añadir estado de pago
        histMovimiento.setDiasTrasladar(diasTrasladar);
        histMovimiento.setRenta(renta);
        histMovimiento.setTotalAPagar(totalAPagar);
        histMovimiento.setValorFuturo(valorFuturo);
        histMovimiento.setInteres(interes);

        histMovimientoRepo.save(histMovimiento);

        return compra;
    }

    private double calcularValorFuturo(double cuota, double tasa, int diasTrasladar, String tipoTasa, int capitalizacion) {
        if (tipoTasa.equals("Efectiva")) {
            return cuota * Math.pow((1 + tasa), (double) diasTrasladar / 30);
        } else if (tipoTasa.equals("Nominal")) {
            return cuota * Math.pow((1 + (tasa / (30 / capitalizacion))), (double) diasTrasladar / capitalizacion);
        }
        return cuota; // Default caso no reconocido
    }

    private int calcularDiasTrasladar(LocalDate fechaCompra, int fechaPagoMensual) {
        LocalDate fechaPago = fechaCompra.withDayOfMonth(fechaPagoMensual);
        if (fechaCompra.isAfter(fechaPago) || fechaCompra.isEqual(fechaPago)) {
            fechaPago = fechaPago.plusMonths(1);
        }
        return (int) ChronoUnit.DAYS.between(fechaCompra, fechaPago);
    }

    private double calcularRenta(double cuota, double tasa, int cuotas) {
        double tep = tasa;
        double factor = Math.pow(1 + tep, cuotas);
        return cuota * ((tep * factor) / (factor - 1));
    }

    private double calcularTEP(double tasaNominal, int m) {
        return Math.pow(1 + tasaNominal / m, m) - 1;
    }
}
