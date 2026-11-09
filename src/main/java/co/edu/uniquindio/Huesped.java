import co.edu.uniquindio.Autenticable;
import co.edu.uniquindio.Persona;
import co.edu.uniquindio.Rol;

import java.time.LocalDate;

public class Huesped extends Persona implements Autenticable {

    private String documento;
    private String telefono;
    private String correo;
    private String rutaFoto;      //
    private String usuario;
    private String contrasena;

    public Huesped(String nombre, LocalDate fechaNacimiento, String documento,
                   String telefono, String correo, String usuario, String contrasena) {
        super(nombre, fechaNacimiento);
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rutaFoto = null;
    }


    public void actualizarPerfil(String nombre, String telefono, String correo, String rutaFoto) {
        setNombre(nombre);
        this.telefono = telefono;
        this.correo = correo;
        this.rutaFoto = rutaFoto;
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        if (!this.usuario.equals(usuario)) {
            return false;
        }
        if (!this.contrasena.equals(contrasena)) {
            return false;
        }
        return true;
    }

    @Override
    public Rol getRol() {
        return Rol.HUESPED; }

    public String getDocumento() {
        return documento;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getCorreo() {
        return correo;
    }
    public String getRutaFoto() {
        return rutaFoto;
    }
    public String getUsuario() {
        return usuario;
    }
}