package Controlador;
import Exceptions.InvalidUserAlreadyExists;
import Exceptions.InvalidWrongPasswordFormat;
import Exceptions.InvalidWrongUserFormat;
import Exceptions.Validaciones;
import Modelo.Config;
import Modelo.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ControladorRegistroUsuarios {

    public ControladorRegistroUsuarios() {

        if(ControladorArchivoUsuarios.cargarRepositorioDesdeArchivo() == null) ControladorArchivoUsuarios.crearArchivoUsuariosDummy();
        ControladorArchivoUsuarios.mostrarArchivo();
    }

    public void registrarUsuario(String nombreUsuario, String contraseña) throws InvalidWrongUserFormat, InvalidUserAlreadyExists, InvalidWrongPasswordFormat {

        HashMap<String, Usuario> repositorio = ControladorArchivoUsuarios.cargarRepositorioDesdeArchivo();

        try {
            Validaciones.invalidWrongUserFormat(nombreUsuario);
            Validaciones.invalidUserAlreadyExists(nombreUsuario, repositorio);
            Validaciones.invalidWrongPasswordFormat(contraseña);

            Usuario nuevoUsuario = new Usuario(nombreUsuario, contraseña);

            JSONObject nuevoUsuarioJSON = nuevoUsuario.toJSON();
            JSONArray usuariosJSONArray = new JSONArray(JSONUtilities.downloadJSON(Config.getCarpetaRaiz() + "usuariosRegistrados.json"));
            usuariosJSONArray.put(nuevoUsuarioJSON);
            JSONUtilities.uploadJSON(usuariosJSONArray, Config.getCarpetaRaiz() + "usuariosRegistrados.json");
        }
        catch(InvalidWrongUserFormat | InvalidUserAlreadyExists | InvalidWrongPasswordFormat exception) {
            throw exception;
        }
    }

    public void testRegistrarUsuarios() {

        try {
            registrarUsuario("juancito", "lalala"); }
        catch(InvalidWrongUserFormat | InvalidUserAlreadyExists | InvalidWrongPasswordFormat exception) {
            System.out.println("\nError: " + exception.getMessage()); }

        try {
            registrarUsuario("Rouse02", "Rouse025%"); }
        catch(InvalidWrongUserFormat | InvalidUserAlreadyExists | InvalidWrongPasswordFormat exception) {
            System.out.println("\nError: " + exception.getMessage()); }

        try {
            registrarUsuario("Rouse02", "Rouse025#"); }
        catch(InvalidWrongUserFormat | InvalidUserAlreadyExists | InvalidWrongPasswordFormat exception) {
            System.out.println("\nError: " + exception.getMessage()); }
    }
}
