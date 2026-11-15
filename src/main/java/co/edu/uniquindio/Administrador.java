package co.edu.uniquindio;
import java.time.LocalDate;

public class Administrador extends Empleado {

    public Administrador(String nombre, LocalDate fechaNacimiento,
                         String documento, String telefono,
                         String correo, String usuario, String contrasena) {

        super(nombre, fechaNacimiento, documento, telefono,
                correo, usuario, contrasena);
    }

    @Override
    public Rol getRol() {
        return Rol.ADMINISTRADOR;
    }
}
