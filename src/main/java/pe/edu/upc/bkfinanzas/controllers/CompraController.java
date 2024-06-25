package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.bkfinanzas.model.*;
import pe.edu.upc.bkfinanzas.repository.ClienteRepository;
import pe.edu.upc.bkfinanzas.repository.ProductoRepository;
import pe.edu.upc.bkfinanzas.repository.TipoCreditoRepository;
import pe.edu.upc.bkfinanzas.service.CompraService;
import pe.edu.upc.bkfinanzas.service.ProductoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/compra")
public class CompraController {
    @Autowired
    public final CompraService compraService;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private TipoCreditoRepository tipoCreditoRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Compra> insert(@RequestBody Compra compra){
        return new ResponseEntity<>(compraService.insert(compra), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> lsCompra(){
        return new ResponseEntity<>(compraService.lsCompra(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Compra> elimina (@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(compraService.eliminar(id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Compra> edita(@RequestBody Compra compra) throws Exception {
        return new ResponseEntity<>(compraService.modifica(compra), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Compra> registrarCompra(@RequestBody CompraDTO compraDTO) {
        Cliente cliente = clienteRepo.findById(compraDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Compra comp = compraService.registrarCompra(cliente, compraDTO.getTipoCredito(), compraDTO.getDetallesCompra());

        return ResponseEntity.ok(comp);
    }


    @GetMapping("/reporte")
    public ResponseEntity<List<HistMovimientoDTO>> consultaReporteCompra() {
        List<HistMovimientoDTO> reporte = compraService.consultaReporteCompra();
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

    @GetMapping("/reporte/{clienteId}")
    public ResponseEntity<List<HistMovimientoDTO>> consultaReporteCompraPorCliente(@PathVariable Integer clienteId) {
        List<HistMovimientoDTO> reporte = compraService.consultaReporteCompraPorCliente(clienteId);
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }


    @GetMapping("/reporte/clientes")
    public ResponseEntity<List<HistMovimientoDTO>> consultaReporteCompraTodosClientes() {
        List<HistMovimientoDTO> reporte = compraService.consultaReporteCompraTodosClientes();
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }
}
