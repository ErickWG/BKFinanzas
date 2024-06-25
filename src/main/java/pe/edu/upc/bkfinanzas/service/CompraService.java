package pe.edu.upc.bkfinanzas.service;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.*;
import pe.edu.upc.bkfinanzas.repository.*;

import java.io.Console;
import java.time.temporal.ChronoUnit;

import java.sql.Date;
import java.time.LocalDate;
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
        }

        compra.setDetallesCompra(detallesCompra);
        compra.setMonto_total(montoTotal);

        // Restar el monto total al crédito del cliente
        if (cliente.getLimite_credito() < montoTotal) {
            throw new RuntimeException("Crédito insuficiente para realizar la compra");
        }
        cliente.setLimite_credito(cliente.getLimite_credito() - montoTotal);
        clienteRepo.save(cliente); // Guardar los cambios en el cliente

        return compraRepo.save(compra);
    }


    public List<HistMovimientoDTO> consultaReporteCompra() {
        List<Object[]> getReporteCompra = compraRepo.getReporteCompra();
        List<HistMovimientoDTO> histMovimientoDTOs = new ArrayList<>();

        for (Object[] result : getReporteCompra) {
            HistMovimientoDTO dto = new HistMovimientoDTO();
            dto.setNombrecompleto((String) result[0]);
            dto.setFecha(((Date) result[1]).toLocalDate()); // Convertir java.sql.Date a java.time.LocalDate
            dto.setDescripcion((String) result[2]);
            dto.setSubtotal((Double) result[3]);
            dto.setTasa_text((String) result[4]);
            dto.setTasa_num((Double) result[5]);
            dto.setCuotas((Integer) result[6]);
            dto.setCapitalizacion((Integer) result[7]);
            histMovimientoDTOs.add(dto);
        }

        return histMovimientoDTOs;
    }




    private double calcularValorFuturo(double cuota, double tasa, int diasTrasladar, String tipoTasa, int capitalizacion) {
        if (tipoTasa.equals("Efectiva")) {
            return cuota * Math.pow((1 + tasa), (double) diasTrasladar / 30);
        } else if (tipoTasa.equals("Nominal")) {
            return cuota * Math.pow((1 + (tasa/(30/capitalizacion))), (double) diasTrasladar/capitalizacion);
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

    public List<HistMovimientoDTO> consultaReporteCompraPorCliente(Integer clienteId) {
        List<Object[]> getReporteCompra = compraRepo.getReporteCompraPorCliente(clienteId);
        List<HistMovimientoDTO> histMovimientoDTOs = new ArrayList<>();

        Cliente cliente = clienteRepo.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        int fechaPagoMensual = cliente.getFecha_pago_mensual();

        for (Object[] result : getReporteCompra) {
            HistMovimientoDTO dto = new HistMovimientoDTO();
            dto.setNombrecompleto((String) result[0]);
            LocalDate fechaCompra = ((Date) result[1]).toLocalDate();
            dto.setFecha(fechaCompra);
            dto.setDescripcion((String) result[2]);
            double subtotal = ((Number) result[3]).doubleValue();
            double tasa_num = ((Number) result[5]).doubleValue();
            int cuotas = ((Number) result[6]).intValue();
            int capitalizacion = ((Number) result[7]).intValue();
            dto.setSubtotal(subtotal);
            dto.setTasa_text((String) result[4]);
            dto.setTasa_num(tasa_num);
            dto.setCuotas(cuotas);
            dto.setCapitalizacion(capitalizacion);

            // Calcular el "Nº días trasladar"
            int diasTrasladar = calcularDiasTrasladar(fechaCompra, fechaPagoMensual);
            dto.setDiasTrasladar(diasTrasladar);

            // Inicializar valorFuturo
            double valorFuturo = 0.0;

            // Calcular el valor futuro dependiendo de la tasa y cuotas
            if (cuotas == 1) {
                valorFuturo = calcularValorFuturo(subtotal, tasa_num / 100, diasTrasladar, dto.getTasa_text(), capitalizacion);
                dto.setValorFuturo(valorFuturo);
            } else {
                double tep;
                if (dto.getTasa_text().equals("Nominal")) {
                    // Convertir tasa nominal a tasa efectiva usando capitalización
                    tep = calcularTEP(tasa_num / 100, 30/capitalizacion);
                } else {
                    tep = tasa_num / 100;
                }
                double renta = calcularRenta(subtotal, tep, cuotas);
                dto.setRenta(renta);
                double totalAPagar = renta * cuotas;
                dto.setTotalAPagar(totalAPagar);
                valorFuturo = totalAPagar; // Usar total a pagar como valor futuro
            }

            // Calcular el interés solo si valorFuturo ha sido establecido
            if (valorFuturo != 0.0) {
                double interes = valorFuturo - subtotal;
                dto.setInteres(interes);
            }

            histMovimientoDTOs.add(dto);
        }

        return histMovimientoDTOs;
    }
    public List<HistMovimientoDTO> consultaReporteCompraTodosClientes() {
        List<Object[]> getReporteCompra = compraRepo.getReporteCompra();
        List<HistMovimientoDTO> histMovimientoDTOs = new ArrayList<>();

        for (Object[] result : getReporteCompra) {
            HistMovimientoDTO dto = new HistMovimientoDTO();
            dto.setNombrecompleto((String) result[0]);
            LocalDate fechaCompra = ((Date) result[1]).toLocalDate();
            dto.setFecha(fechaCompra);
            dto.setDescripcion((String) result[2]);
            double subtotal = ((Number) result[3]).doubleValue();
            double tasa_num = ((Number) result[5]).doubleValue();
            int cuotas = ((Number) result[6]).intValue();
            int capitalizacion = ((Number) result[7]).intValue();
            dto.setSubtotal(subtotal);
            dto.setTasa_text((String) result[4]);
            dto.setTasa_num(tasa_num);
            dto.setCuotas(cuotas);
            dto.setCapitalizacion(capitalizacion);

            // Calcular el "Nº días trasladar"
            // Aquí no tenemos el pago mensual del cliente, así que se necesita una forma estándar de calcularlo.
            int diasTrasladar = calcularDiasTrasladar(fechaCompra, 30); // Ejemplo de pago mensual fijo en el día 30
            dto.setDiasTrasladar(diasTrasladar);

            // Inicializar valorFuturo
            double valorFuturo = 0.0;

            // Calcular el valor futuro dependiendo de la tasa y cuotas
            if (cuotas == 1) {
                valorFuturo = calcularValorFuturo(subtotal, tasa_num / 100, diasTrasladar, dto.getTasa_text(), capitalizacion);
                dto.setValorFuturo(valorFuturo);
            } else {
                double tep;
                if (dto.getTasa_text().equals("Nominal")) {
                    // Convertir tasa nominal a tasa efectiva usando capitalización
                    tep = calcularTEP(tasa_num / 100, 30 / capitalizacion);
                } else {
                    tep = tasa_num / 100;
                }
                double renta = calcularRenta(subtotal, tep, cuotas);
                dto.setRenta(renta);
                double totalAPagar = renta * cuotas;
                dto.setTotalAPagar(totalAPagar);
                valorFuturo = totalAPagar; // Usar total a pagar como valor futuro
            }

            // Calcular el interés solo si valorFuturo ha sido establecido
            if (valorFuturo != 0.0) {
                double interes = valorFuturo - subtotal;
                dto.setInteres(interes);
            }

            histMovimientoDTOs.add(dto);
        }

        return histMovimientoDTOs;
    }


}
