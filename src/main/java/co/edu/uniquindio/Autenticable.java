package co.edu.uniquindio;

public interface Autenticable {
    boolean autenticar(String usuario, String contrasena); // Verifica que los datos sean propios del usuario a registrarse
    Rol getRol();                                          // verifica el ROL de la persona
}
