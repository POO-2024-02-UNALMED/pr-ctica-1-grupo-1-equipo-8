package gestorAplicacion.administracion;
import java.util.ArrayList;
public class Materia {
    private static final long serialVersionUID=1L;
    private String nombre;
    private final int codigo;
    private String descripcion;
    private int creditos;
    private String facultad;
    private int cupos;
    private ArrayList<Materia> prerrequisitos;
    private ArrayList<Grupo> grupos;
    public static ArrayList<Materia> materiasTotales = new ArrayList<Materia>();
    private String abreviatura;

    public Materia();
;}
