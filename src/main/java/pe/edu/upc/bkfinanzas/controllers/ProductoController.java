package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.bkfinanzas.model.Producto;
import pe.edu.upc.bkfinanzas.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/producto")

public class ProductoController {
    @Autowired
    public final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
        public ResponseEntity<Producto> insert(@RequestBody Producto producto){
        return new ResponseEntity<>(productoService.insert(producto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> lsProducto(){
        return new ResponseEntity<>(productoService.lsProducto(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> elimina (@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(productoService.eliminar(id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Producto> edita(@RequestBody Producto producto) throws Exception {
        return new ResponseEntity<>(productoService.modifica(producto), HttpStatus.OK);
    }

}
