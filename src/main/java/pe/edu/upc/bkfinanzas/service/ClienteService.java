package pe.edu.upc.bkfinanzas.service;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.Cliente;
import pe.edu.upc.bkfinanzas.repository.ClienteRepository;

import java.util.List;
@Service
public class ClienteService {
    @Autowired
    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    //Insertar
    public Cliente insert (Cliente cliente){ return clienteRepo.save(cliente);}

    //Listar
    public List<Cliente> lsCliente(){ return clienteRepo.findAll();}

    //Eliminar
    public Cliente eliminar(Integer id) throws Exception{
        Cliente v=clienteRepo.findById(id).orElseThrow(()->new OpenApiResourceNotFoundException("No se encontro el ID" + id));
        clienteRepo.delete(v);
        return v;
    }
}
