package pe.edu.upc.bkfinanzas.service;


import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.*;
import pe.edu.upc.bkfinanzas.repository.CompraRepository;
import pe.edu.upc.bkfinanzas.repository.DetalleCompraRepository;
import pe.edu.upc.bkfinanzas.repository.ProductoRepository;

import java.util.List;

@Service
public class CompraService {
    @Autowired
    private final CompraRepository compraRepo;

    //@Autowired
    //private final ProductoRepository productoRepo;
//
    //@Autowired
    //private final DetalleCompraRepository detalleCompraRepo;

    public CompraService(CompraRepository compraRepo) {
        this.compraRepo = compraRepo;

    }

    //PRUEBAAAAAAA
    //public CompraService(CompraRepository compraRepo, ProductoRepository productoRepo, DetalleCompraRepository detalleCompraRepo) {
    //    this.compraRepo = compraRepo;
    //    this.productoRepo = productoRepo;
    //    this.detalleCompraRepo = detalleCompraRepo;
    //}

    //Insertar
    public Compra insert (Compra compra){ return compraRepo.save(compra);}

    //Listar
    public List<Compra> lsCompra(){ return compraRepo.findAll();}

    //Eliminar
    public Compra eliminar(Integer id) throws Exception{
        Compra m=compraRepo.findById(id).orElseThrow(()->new OpenApiResourceNotFoundException("No se encontro el ID" + id));
        compraRepo.delete(m);
        return m;
    }

    //modificar
    public Compra modifica (Compra compra) throws Exception{
        Compra com= compraRepo.findById(compra.getId())
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Id de la compra no existe"));
        return compraRepo.save(compra);
    }


}
