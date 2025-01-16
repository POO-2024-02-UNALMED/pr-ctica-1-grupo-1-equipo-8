//Jhoan Alexis
package gestorAplicacion.usuario;

import java.util.ArrayList;
import java.io.Serializable;
import gestorAplicacion.administracion.*;

public class Estudiante extends Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    private String programa;
    private int semestre;
    private int creditos;
    private ArrayList<Materia> materias;
    private ArrayList<Grupo> grupos;
    private Horario horario;
    private int estrato;
    private int sueldo;
    private int valorMatricula;
    private boolean matriculaPagada;
    private double promedio;
    private double avance;
    private Beca beca;
    private ArrayList<Double> notas = new ArrayList<Double>();
    private ArrayList<Grupo> gruposVistos = new ArrayList<Grupo>();
    private static ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
    private final static int creditosParaGraduarse = 120;

    public Estudiante(long id, String nombre, String programa, int semestre, String facultad, int estrato, int sueldo){
        super(id, nombre, facultad);
        super.setTipo("Estudiante");
        this.programa = programa;
        this.semestre = semestre;
        this.materias = new ArrayList<Materia>();
        this.grupos = new ArrayList<Grupo>();
        this.estrato = estrato;
        this.sueldo = sueldo;
        this.valorMatricula = 1234567 * estrato;
        this.horario = new Horario();
        Estudiante.estudiantes.add(this);

    }

    public Estudiante(long id, String nombre, String programa, int semestre, String facultad, int estrato, int sueldo, ArrayList<Materia> materias, ArrayList<Grupo> gruposVistos){
        this(id, nombre, programa, semestre, facultad, estrato, sueldo);
        this.materias = materias;
        this.gruposVistos = gruposVistos;
    }


    public String toString(){
        return "Nombre: " + this.nombre + " Documento: " + this.id;
    }

    public String mostrarMaterias(){
        String retorno = "";
        int posicion = 1;
        for (Grupo grupo: grupos){
            retorno += (posicion++) + "- " + grupo.getMateria().getNombre() + " | Grupo " + grupo.getNumero() + "\n";
        }
        return retorno;
    }

    public static int buscarEstudiante(String nombre, long id){
        for (int i = 0; i < estudiantes.size() ; i++){
            if(estudiantes.get(i).getNombre().equals(nombre) && estudiantes.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

}