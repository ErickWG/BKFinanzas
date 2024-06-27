package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.bkfinanzas.model.*;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        List<HistMovimientoDTO> histMovimientosDTO = histMovimientoService.getHistMovimientosDTOByUsername(username);

        return ResponseEntity.ok(histMovimientosDTO);
    }

    @PutMapping
    public ResponseEntity<HistMovimiento> edita(@RequestBody HistMovimiento histmovi) throws Exception {
        return new ResponseEntity<>(histMovimientoService.modifica(histmovi), HttpStatus.OK);
    }
}
