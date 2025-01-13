package gestorAplicacion.administracion;
import java.util.ArrayList;
import gestorAplicacion.administracion.*;
import java.io.Serializable;
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
        this.facultad = facultad;
        this.prerrequisitos = new ArrayList<Materia>();
        this.grupos = new ArrayList<Grupo>();
        this.hacerAbreviatura(nombre);
        materiasTotales.add(this);
        }
    public Materia(String nombre, int codigo, String descripcion, int creditos, String facultad, ArrayList<Materia> prerrequisitos) {
        this(nombre, codigo, descripcion, creditos, facultad);
        this.prerrequisitos = prerrequisitos;
        }

    public Materia(String nombre, int codigo, String descripcion,int creditos, String facultad, ArrayList<Materia> prerrequisitos, ArrayList<Grupo> grupos) {
        this(nombre, codigo, descripcion, creditos, facultad, prerrequisitos);
        this.grupos = grupos;
        }
        
    public int cantidadCupos(){
        int cantidad = 0;
        for (Grupo pGrupo:getGrupos()){
            cantidad+=pGrupo.getCupos();
            }
            return cantidad;
        }
    
    public Grupo crearGrupo(int numero, Profesor profesor, ArrayList<String> horario, int cupos, Salon salon){
        Grupo grupo = new Grupo(this, numero, profesor, horario, cupos, salon);
        cantidadCupos();
        this.grupos.add(grupo);
        return grupo;
        }
    public String mostrarContenidos(){
        String contenido =  "Materia: " + this.nombre +
                            "\nCodigo: " + this.codigo +
                            "\nCreditos: " + this.creditos +
                            "\nFacultad: " + this.facultad +
                            "\nDescripcion:\n" + this.descripcion;
                            return contenido;
                                
        }
    public boolean existenciaGrupo(Grupo grupoBuscado){
        if (!grupos.isEmpty()){
            for (Grupo grupo: grupos){
                if (grupo.equals(grupoBuscado)){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }
    public void eliminarGrupo(int numero){
        Grupo grupo = this.grupos.get(numero-1);
        grupo.getProfesor().desvincularGrupo(grupo);
        grupo.getSalon().getHorario().liberarHorario(grupo.getHorario());
        this.grupos.remove(grupo);
        this.cupos -= grupo.getCupos();
        for(int i=numero-1;i<this.grupos.size();i++) {
            Grupo grupoCamb = this.grupos.get(i);
            int nGrupoAnt = grupoCamb.getNumero();
            grupoCamb.setNumero(nGrupoAnt-1);
        }
    }
    public void agregarGrupo(int numero, Profesor profesor, ArrayList<String> horario, int cupos, Salon salon) {
    	boolean dispSalon = true;
    	boolean dispProfesor = true;
    	boolean daMateria = profesor.daMateria(this.nombre);
    	
    	for(String hor:horario) {
    		dispProfesor = profesor.getHorario().comprobarDisponibilidad(hor);
    		dispSalon = salon.getHorario().comprobarDisponibilidad(hor);
    		
    		if(!dispProfesor||!dispSalon) {
    			break;
    		}
    	}
        if(dispProfesor&&dispSalon&&daMateria) {
    		Grupo nGrupo = crearGrupo(numero,profesor,horario,cupos,salon);
    		this.cupos += cupos;
    		salon.getHorario().ocuparHorario(horario, nGrupo);
    		profesor.vincularGrupo(nGrupo);}
        }
    public Grupo buscarGrupoDeEstudiante(Estudiante estudiante){
          for (Grupo grupo: this.grupos){
            for (Estudiante e: grupo.getEstudiantes()){
                 if (e == estudiante){
                     return grupo;
                     }
                    }
                }
                return null;
        }
    
;}