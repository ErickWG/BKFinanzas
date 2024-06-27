package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.bkfinanzas.model.Cliente;
import pe.edu.upc.bkfinanzas.model.Compra;
import pe.edu.upc.bkfinanzas.model.HistMovimiento;
import pe.edu.upc.bkfinanzas.model.HistMovimientoDTO;
import pe.edu.upc.bkfinanzas.service.HistMovimientoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hist-movimiento")
public class HistMovimientoController {

    private final HistMovimientoService histMovimientoService;

    @Autowired
    public HistMovimientoController(HistMovimientoService histMovimientoService) {
        this.histMovimientoService = histMovimientoService;
    }
    @GetMapping
    public ResponseEntity<List<HistMovimiento>> lsCompra(){
        return new ResponseEntity<>(histMovimientoService.lsHistorial(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<HistMovimiento>> buscarPorUsername(@PathVariable Integer id) {
        return new ResponseEntity<>(histMovimientoService.lsHistorialPorId(id), HttpStatus.OK);
    }
    @GetMapping("/listar")
    public ResponseEntity<List<HistMovimientoDTO>> listarHistMovimientos() {
        // Obtén el usuario autenticado desde el contexto de seguridad
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Llama al servicio para obtener los movimientos históricos del usuario
        List<HistMovimientoDTO> histMovimientosDTO = histMovimientoService.getHistMovimientosDTOByUsername(username);

        // Devuelve la lista de movimientos históricos como respuesta
        return ResponseEntity.ok(histMovimientosDTO);
    }
}

