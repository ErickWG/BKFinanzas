package pe.edu.upc.bkfinanzas.service;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.bkfinanzas.model.Cliente;
import pe.edu.upc.bkfinanzas.model.User;
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
    //editar
    public Cliente modifica(Cliente cliente) throws Exception {
        Cliente t = clienteRepo.findById(cliente.getId()).orElseThrow(() -> new OpenApiResourceNotFoundException("No se encontro el ID " + cliente.getId()));
        // PARA QUE NO SE CREE OTRO REGISTRO YA QUE ESTAS MODIFICANDO EN BASE AL ID
        t.setLimite_credito(cliente.getLimite_credito());
        t.setFecha_pago_mensual(cliente.getFecha_pago_mensual());

        // Actualizar los datos del User asociado
        if (cliente.getUser() != null) {
            User user = t.getUser();
            User updatedUser = cliente.getUser();
            if (user != null) {
                user.setNombres(updatedUser.getNombres());
                user.setApellidos(updatedUser.getApellidos());
                user.setTelefono(updatedUser.getTelefono());
                user.setDireccion(updatedUser.getDireccion());
                user.setDni(updatedUser.getDni());
            } else {
                t.setUser(updatedUser); // Si el User es null, asigna el nuevo User
            }
        }

        return clienteRepo.save(t); // Asegúrate de guardar el Cliente y no la Empresa
    }

}
