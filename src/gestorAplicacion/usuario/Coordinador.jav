//Sergio 
package gestorAplicacion.usuario;

import java.util.ArrayList;
import gestorAplicacion.administracion.*;
import java.io.Serializable;
public class Coordinador extends Usuario implements Serializable{
    private final static int limitesCreditos=20;
    private static final long serialVersionUID = 1L;
    private static ArrayList<Coordinador> coordinadoresTotales = new ArrayList<Coordinador>();
    private static String[] facultades = {"Facultad de arquitectura", "Facultad de ciencias", "Facultad de ciencias agrarias" , "Facultad de ciencias humanas y economicas", "Facultad de minas", "Sede"};

    public Coordinador(String facultad,long id, String nombre, String pw) {
        super(id, nombre,pw, facultad);
        super.setTipo("Coordinador");
        Coordinador.coordinadoresTotales.add(this);
    }

    // Desmatricula a el estudiante de un grupo
    