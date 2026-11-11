package co.edu.uniquindio;

public interface Autenticable {
    boolean autenticar(String usuario, String contrasena);
    Rol getRol();

}
