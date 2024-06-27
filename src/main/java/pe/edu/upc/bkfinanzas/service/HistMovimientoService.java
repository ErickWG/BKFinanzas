package pe.edu.upc.bkfinanzas.service;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.Compra;
import pe.edu.upc.bkfinanzas.model.HistMovimiento;
import pe.edu.upc.bkfinanzas.model.HistMovimientoDTO;
import pe.edu.upc.bkfinanzas.model.Producto;
import pe.edu.upc.bkfinanzas.repository.HistMovimientoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistMovimientoService {

    private final HistMovimientoRepository histMovimientoRepository;

    @Autowired
    public HistMovimientoService(HistMovimientoRepository histMovimientoRepository) {
        this.histMovimientoRepository = histMovimientoRepository;
    }

    public List<HistMovimiento> lsHistorial() {
        return histMovimientoRepository.findAll();
    }

    public List<HistMovimiento> lsHistorialPorId(Integer id) {
        return histMovimientoRepository.findByClienteId(id);
    }

    public List<HistMovimientoDTO> getHistMovimientosDTOByUsername(String username) {
        List<HistMovimiento> histMovimientos = histMovimientoRepository.findByClienteUserUsername(username);
        return histMovimientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private HistMovimientoDTO convertToDTO(HistMovimiento histMovimiento) {
        HistMovimientoDTO dto = new HistMovimientoDTO();
        dto.setNombrecompleto(histMovimiento.getNombrecompleto());
        dto.setFecha(histMovimiento.getFecha());
        dto.setDescripcion(histMovimiento.getDescripcion());
        dto.setSubtotal(histMovimiento.getSubtotal());
        dto.setTasa_text(histMovimiento.getTasa_text());
        dto.setTasa_num(histMovimiento.getTasa_num());
        dto.setCuotas(histMovimiento.getCuotas());
        dto.setCapitalizacion(histMovimiento.getCapitalizacion());
        dto.setEstadopago(histMovimiento.getEstadopago()); // Incluyendo el campo estadopago
        dto.setCompraId(histMovimiento.getCompra().getId());
        dto.setClienteId(histMovimiento.getCliente().getId());
        return dto;
    }


    //modificar
    public HistMovimiento modifica (HistMovimiento histMovimiento) throws Exception{
        HistMovimiento histmovi= histMovimientoRepository.findById(histMovimiento.getId())
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Id del producto no existe"));
        return histMovimientoRepository.save(histMovimiento);
    }

}
