package pe.edu.upc.bkfinanzas.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String apellidos;
    String nombres;
    String password;
    String dni;
    String direccion;
    Integer telefono;
}
