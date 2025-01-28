/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 El módulo trabaja con el concepto de profesor. Su labor dentro del sistema académico está presente en que
 sin su presencia, los grupos de las materias no podrían llevarse a cabo. Cumplen demás funciones como recomendar
 estudiantes para acceder a becas.
 */

package gestorAplicacion.usuario;
import java.util.ArrayList;
import java.io.Serializable;
import gestorAplicacion.administracion.*;

//En la clase profesor moldeamos a las personas a cargo de la educación de los alumnos.
//Un profesor puede trabajar con una o varias materias, de la misma forma sucede con los grupos.
//El horario también se debe respetar para una correcta organización de los grupos.
public class Profesor implements Serializable{
    private String nombre;
    private String facultad;
    private Horario horario;
    private ArrayList<Materia> materiasDadas= new ArrayList<Materia>(10);
    private ArrayList<Grupo> grupos= new ArrayList<Grupo>();
    private static ArrayList<Profesor> profesores= new ArrayList<Profesor>();
    private static final long serialversionUID= 1L;

    //Constructor de la clase Profesor
    public Profesor(String nombre, String facultad, Horario horario, ArrayList<Materia> materiasDadas, ArrayList<Grupo> grupos){
        this.nombre=nombre;
        this.facultad=facultad;
        this.horario=horario;
        this.materiasDadas=materiasDadas;
        Profesor.profesores.add(this); //Añadir el profesor a la lista de profesores creados.
    }

    //Constructor sobrecargado, el profesor no recibe los grupos a su mando.
    public Profesor(String nombre, String facultad, Horario horario, ArrayList<Materia> materiasDadas){
        this.nombre=nombre;
        this.facultad=facultad;
        this.horario=horario;
        this.materiasDadas=materiasDadas;
        Profesor.profesores.add(this);
    }

    //Constructor sobrecargado, no recibe ni grupos ni horario.
    public Profesor(String nombre, String facultad, ArrayList<Materia> materiasDadas){
        this.nombre=nombre;
        this.facultad=facultad;
        this.horario=new Horario();
        this.materiasDadas=materiasDadas;
        Profesor.profesores.add(this);
    }

    //El grupo pasado se le vincula al profesor, esto es, ocupar su horario con las clases del grupo.
    public void vincularGrupo(Grupo g){
        this.grupos.add(g);
        this.horario.ocuparHorario(g.getHorario(), g);
    }

    //Método que desvincula un grupo al profesor, se desocupa el horario y se elimina el grupo de la lista de grupos.
    public void desvincularGrupo(Grupo g){
        if (grupos.contains(g)){
            int indice= grupos.indexOf(g);
            ArrayList<String> horLibre= grupos.get(indice).getHorario();
            this.horario.liberarHorario(horLibre);
            grupos.remove(indice);
        }
    }

    //Retorna true si existe una materia dentro de la lista de materias dadas
    //cuyo atributo nombre de tipo String sea igual al String pasado. False si no es el caso.
    public boolean daMateria(String nombre){
        for(Materia materia:this.getMateriasDadas()){
            if(materia.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;

    }

    //Método de clase que define si existen chances de que un profesor recomiende a un estudiante
    //para que sea becado. Las chances aumentan si ha coincidido con varios profesores y si comparte
    //facultad con el profesor.
    public static boolean recomendarEstudiante(Estudiante estudiante){
        boolean bool=false;
        for(Profesor profesor:Profesor.getProfesores()){
            int chance=0;
            int suerte=(int)(Math.random()*10+1);

            for(Grupo grupo:estudiante.getGruposVistos()){
                if (grupo.getProfesor().getNombre().equals(profesor.getNombre())==true){
                    chance +=5;
                    break;
                }
            }
            
            if(estudiante.getFacultad().equals(profesor.getFacultad())==true){
                chance+=3; 
            }
            if (chance >= suerte){
                bool= true;
            }  
    }
    return bool;
}

//Retorna String con el nombre de cada profesor y las materias que imparten.
public static String mostrarProfesores() {
    String r = "";
    int i = 1;
    for(Profesor profesor:Profesor.profesores) {
        r += (i++)+". "+profesor.getNombre()+". Materias: ";
        for (Materia materia:profesor.getMateriasDadas()) {
            if (profesor.getMateriasDadas().indexOf(materia) == profesor.getMateriasDadas().size()-1) {
                r += materia.getNombre()+".\n";
            }
            else {
                r += materia.getNombre()+", ";
            }
        }
    }
    return r;
}

//Devuelve una lista con todos los profesores que dan una materia. La materia se ubica con su nombre.
public static ArrayList<Profesor> profesoresDeMateria(String nombre) {
    ArrayList<Profesor> profes = new ArrayList<Profesor>();
    for (Profesor profesor:Profesor.getProfesores()) {
        if(profesor.daMateria(nombre)&&!profes.contains(profesor)) {
            profes.add(profesor);
        }
    }
    return profes;
}


//Devuelve un String con todos los profesores que dan una materia. La materia se ubica con su nombre.
public static String mostrarProfesMateria(String nombre) {
    String r = "";
    int i = 1;
    ArrayList<Profesor> profes = profesoresDeMateria(nombre);
    for (Profesor profesor:profes) {
        r += (i++)+". "+profesor.getNombre()+".\n"; 
    }
    return r;
}


//Métodos getters y setters.
public String getNombre() {
    return nombre;
}
public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getFacultad() {
    return facultad;
}
public void setFacultad(String facultad) {
    this.facultad = facultad;
}

public ArrayList<Materia> getMateriasDadas() {
    return materiasDadas;
}
public void setMateriasDadas(ArrayList<Materia> materiasDadas) {
    this.materiasDadas = materiasDadas;
}

public ArrayList<Grupo> getGrupos() {
    return grupos;
}
public void setGrupos(ArrayList<Grupo> grupos) {
    this.grupos = grupos;
}

public static ArrayList<Profesor> getProfesores() {
    return profesores;
}
public static void setProfesores(ArrayList<Profesor> profesores) {
    Profesor.profesores = profesores;
}

public void setHorario(Horario horario) {
    this.horario = horario;
}

public Horario getHorario() {
    return this.horario;
}

}
        
