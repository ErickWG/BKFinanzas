package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.bkfinanzas.model.Compra;
import pe.edu.upc.bkfinanzas.model.Producto;
import pe.edu.upc.bkfinanzas.service.CompraService;
import pe.edu.upc.bkfinanzas.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/compra")
public class CompraController {
    @Autowired
    public final CompraService compraService;

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






}
