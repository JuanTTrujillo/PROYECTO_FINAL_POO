package co.edu.uniquindio;

import java.time.LocalDate;

public abstract class Empleado extends Persona implements Autenticable, Desactivable {

    private String documento;
    private String telefono;
    private String correo;
    private String usuario;
    private String contrasena;
    private boolean activo;       // false = desvinculado

    public Empleado(String nombre, LocalDate fechaNacimiento, String documento,
                    String telefono, String correo, String usuario, String contrasena) {
        super(nombre, fechaNacimiento);
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.activo = true;
    }

    public void actualizarDatos(String nombre, String telefono, String correo) {
        setNombre(nombre);
        this.telefono = telefono;
        this.correo = correo;
    }

    @Override
    public void desactivar() {
        activo = false; }      // desvincular = marcar inactivo

    @Override
    public boolean isActivo() {
        return activo; }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        if (!activo) {
            return false;
        }
        if (!this.usuario.equals(usuario)) {
            return false;
        }
        if (!this.contrasena.equals(contrasena)) {
            return false;
        }
        return true;
    }

    public String getDocumento() {
        return documento;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getCorreo() {
        return correo;
    }
    public String getUsuario() {
        return usuario;
    }
}