package pe.edu.upc.bkfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.bkfinanzas.model.Producto;
import pe.edu.upc.bkfinanzas.model.TipoCredito;
import pe.edu.upc.bkfinanzas.service.ProductoService;
import pe.edu.upc.bkfinanzas.service.TipoCreditoService;

import java.util.List;

@RestController
@RequestMapping("/tipocredito")
public class TipoCreditoController {

    @Autowired
    public final TipoCreditoService tipoCreditoService;

    public TipoCreditoController(TipoCreditoService tipoCreditoService) {
        this.tipoCreditoService = tipoCreditoService;
    }

    @PostMapping
    public ResponseEntity<TipoCredito> insert(@RequestBody TipoCredito tipoCredito){
        return new ResponseEntity<>(tipoCreditoService.insert(tipoCredito), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TipoCredito>> lsTipoCredito(){
        return new ResponseEntity<>(tipoCreditoService.lsTipoCredito(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoCredito> elimina (@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(tipoCreditoService.eliminar(id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<TipoCredito> edita(@RequestBody TipoCredito tipoCredito) throws Exception {
        return new ResponseEntity<>(tipoCreditoService.modifica(tipoCredito), HttpStatus.OK);
    }

}
