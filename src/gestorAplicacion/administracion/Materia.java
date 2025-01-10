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

    public Materia( String nombre, int codigo, String descripcion,int creditos,String facultad);{
        this.nombre=nombre;
        this.codigo=codigo;
        this.descripcion=descripcion;
        this.creditos=creditos;
        

    }
;}
