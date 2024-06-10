package pe.edu.upc.bkfinanzas.service;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.Producto;
import pe.edu.upc.bkfinanzas.model.TipoCredito;
import pe.edu.upc.bkfinanzas.repository.ProductoRepository;
import pe.edu.upc.bkfinanzas.repository.TipoCreditoRepository;

import java.util.List;

@Service
public class TipoCreditoService {
    @Autowired
    private final TipoCreditoRepository tipoCreditoRepo;

    public TipoCreditoService(TipoCreditoRepository tipoCreditoRepo) {
        this.tipoCreditoRepo = tipoCreditoRepo;
    }

    //Insertar
    public TipoCredito insert (TipoCredito tipoCredito){ return tipoCreditoRepo.save(tipoCredito);}

    //Listar
    public List<TipoCredito> lsTipoCredito(){ return tipoCreditoRepo.findAll();}

    //Eliminar
    public TipoCredito eliminar(Integer id) throws Exception{
        TipoCredito m=tipoCreditoRepo.findById(id).orElseThrow(()->new OpenApiResourceNotFoundException("No se encontro el ID" + id));
        tipoCreditoRepo.delete(m);
        return m;
    }

    //modificar
    public TipoCredito modifica (TipoCredito tipoCredito) throws Exception{
        TipoCredito tipocred= tipoCreditoRepo.findById(tipoCredito.getId())
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Id del tipo de credito no existe"));
        return tipoCreditoRepo.save(tipoCredito);
    }

}
