package pe.edu.upc.bkfinanzas.service;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.Producto;
import pe.edu.upc.bkfinanzas.repository.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private final ProductoRepository productoRepo;

    public ProductoService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    //Insertar
    public Producto insert (Producto producto){ return productoRepo.save(producto);}

    //Listar
    public List<Producto> lsProducto(){ return productoRepo.findAll();}

    //Eliminar
    public Producto eliminar(Integer id) throws Exception{
        Producto m=productoRepo.findById(id).orElseThrow(()->new OpenApiResourceNotFoundException("No se encontro el ID" + id));
        productoRepo.delete(m);
        return m;
    }

    //modificar
    public Producto modifica (Producto producto) throws Exception{
        Producto prod= productoRepo.findById(producto.getId())
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Id del producto no existe"));
        return productoRepo.save(producto);
    }


}
