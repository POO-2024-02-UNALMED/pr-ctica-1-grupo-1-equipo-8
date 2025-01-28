/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 En este módulo se agregan los aspectos pertinentes para trabajar con estudiantes. Se reúnen las
 características necesarias para identificar y adelantar trámites en el sistema académico con el alumno.
 */
package gestorAplicacion.usuario;

import java.util.ArrayList;
import java.io.Serializable;
import gestorAplicacion.administracion.*;

//Esta clase modela a los principales actores del sistema académico, un estudiante hace parte de un programa,
//tiene un progreso académico, un sueldo, un estrato, un valor de matrícula, notas promedio, créditos, materias, grupos, horario y si es
//lo suficientemente apto, una beca.
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

    //Constructor clase Estudiante.
    public Estudiante(long id, String nombre, String programa, int semestre, String facultad, int estrato, int sueldo){
        super(id, nombre, facultad); //Llamada al constructor de la clase Usuario.
        super.setTipo("Estudiante"); //Método heredado.
        this.programa = programa;
        this.semestre = semestre;
        this.materias = new ArrayList<Materia>(); //Lista de materias.
        this.grupos = new ArrayList<Grupo>(); //Lista de grupos.
        this.estrato = estrato;
        this.sueldo = sueldo;
        this.valorMatricula = 1234567 * estrato;
        this.horario = new Horario(); //Objeto Horario para representar el horario del estudiante.
        Estudiante.estudiantes.add(this); //Agregar el estudiante a la lista de estudiantes creados.

    }

    //Constructor sobrecargado que recibe materias y los grupos vistos.
    public Estudiante(long id, String nombre, String programa, int semestre, String facultad, int estrato, int sueldo, ArrayList<Materia> materias, ArrayList<Grupo> gruposVistos){
        this(id, nombre, programa, semestre, facultad, estrato, sueldo);
        this.materias = materias;
        this.gruposVistos = gruposVistos;
    }

    //Método que define lo mostrado cuando se imprime un objeto Estudiante.
    public String toString(){
        return "Nombre: " + this.nombre + " Documento: " + this.id;
    }

    //Retorna String con las materias que cursa el estudiante, con el grupo del que hace parte.
    public String mostrarMaterias(){
        String retorno = "";
        int posicion = 1;
        for (Grupo grupo: grupos){
            retorno += (posicion++) + "- " + grupo.getMateria().getNombre() + " | Grupo " + grupo.getNumero() + "\n";
        }
        return retorno;
    }

    //Método de clase que busca a un estudiante por nombre y código, retorna su posición la lista de
    //estudiantes, -1 si no es encontrado.
    public static int buscarEstudiante(String nombre, long id){
        for (int i = 0; i < estudiantes.size() ; i++){
            if(estudiantes.get(i).getNombre().equals(nombre) && estudiantes.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    //Método que elimina una materia y resta los créditos de la materia al estudiante.
    public void eliminarMateria(Materia materia){
        this.materias.remove(materia);
        this.creditos -= materia.getCreditos();
    }


    //Elimina la materia y el grupo de la materia.
    public void eliminarGrupo(Grupo grupo){
        this.grupos.remove(grupo);
        this.eliminarMateria(grupo.getMateria());
    }

    //Método para que el estudiante pague su matricula desde su sueldo.
    public boolean pagarMatricula(){
        if (this.sueldo >= this.valorMatricula){
            this.sueldo -= this.valorMatricula;
            this.matriculaPagada = true;
            return true;
        }
        
        this.matriculaPagada = false;
        return false;
    }

    //Calcula el promedio del estudiante con su lista de notas y lo asigna al atribuot promedio.
    private void calcularPromedio(){
        double promedio = 0;

        for (double nota: this.notas){
            promedio += nota;
        }

        promedio = promedio / ((double) this. notas.size());
        this.promedio = promedio;
    }

    //Este método calcula el avance del estudiante. Para ello para cada grupo visto suma los créditos
    // y usa el atributo estático creditosParaGraduarse.
    public void calcularAvance(){
        
        double creditosVistos = 0;

        for (Grupo pGrupo: gruposVistos){
            creditosVistos += pGrupo.getMateria().getCreditos(); 
        }

        this.avance = (creditosVistos *100.0)/creditosParaGraduarse;
    }

    //Método que agrega una nota a la lista de notas, luego actualiza el promedio.
    public void agregarNota(double nota){
        this.notas.add(nota);
        this.calcularPromedio();
    }


    //Método estático que devuelve un String con todos los estudiantes creados.
    public static String mostrarEstudiantes(){
        String estudiantes ="";
        int i = 1;
        for (Estudiante estudiante: Estudiante.estudiantes){
            estudiantes += "\n" + (i++) + ". " + estudiante;
        }
        return estudiantes.substring(1, estudiantes.length());
    }

    //Método que busca una materia por nombre y la retorna si la encuentra.
    public Materia buscarMateriaPorNombre(String nombre){
        for (Materia materia : this.materias){
            if (materia.getNombre().equals(nombre)){
                return materia;
            }
        }
        return null;
    }

    //Busca la materia por nombre y código pero en las materias inscritas por el alumno, true si la encuentra
    // y false si no es encontrada.
    public boolean buscarMateriaEnInscritas(String nombre, int codigo){
        for (Materia materia : this.materias){
            if (materia.getNombre().equals(nombre) && materia.getCodigo()==codigo){
                return true;
            }
        }
        return false;
    }

    // Método que desmatricula al estudiante de los grupos y materias que ve en el semestre.
    public void desmatricularMaterias(){
        ArrayList<Grupo> gruposEliminar = new ArrayList<>();
        this.setMaterias(new ArrayList<Materia>());
        for(Grupo grupoE: this.grupos){
            Grupo grupo = Grupo.buscarGrupo(grupoE.getMateria(), grupoE);
            grupo.getMateria().setCupos(grupo.getMateria().getCupos()+1); //Sumar cupo del estudiante
            this.setCreditos(this.getCreditos()-grupo.getMateria().getCreditos()); //Restar créditos al estudiante.
            gruposEliminar.add(grupo);
        }
        int num = gruposEliminar.size();
        for (int i = 0; i < num; i++){
            gruposEliminar.get(i).eliminarEstudiante(this);
        }
    }

    //Métodos getters y setters
    public String getPrograma(){
        return this.programa;
    }

    public void setPrograma(String programa){
        this.programa = programa;
    }

    public int getSemestre(){
        return this.semestre;
    }

    public void setSemestre(int semestre){
        this.semestre = semestre;
    }

    public int getCreditos(){
        return this.creditos;
    }

    public void setCreditos(int creditos){
        this.creditos = creditos;
    }

    public ArrayList<Materia> getMaterias(){
        return this.materias;
    }

    public void setMaterias(ArrayList<Materia> materias){
        this.materias = materias;
    }

    public int getEstrato(){
        return this.estrato;
    }

    public void setEstrato(int estrato){
        this.estrato = estrato;
    }

    public int getSueldo(){
        return this.sueldo;
    }

    public void setSueldo(int sueldo){
        this.sueldo = sueldo;
    }

    public int getValorMatricula(){
        return this.valorMatricula;
    }

    public boolean isMatriculaPagada(){
        return this.matriculaPagada;
    }

    // Agrego metodo getEstudiantes (Sergio)
    public static ArrayList<Estudiante> getEstudiantes() {
        return Estudiante.estudiantes;
    }

    public static void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        Estudiante.estudiantes = estudiantes;
    }

    public ArrayList<Grupo> getGruposVistos(){
        return this.gruposVistos;
    }

    public void setGruposVistos(ArrayList<Grupo> gruposVistos){
        this.gruposVistos = gruposVistos;
    }

    public void setHorario(Horario horario){
        this.horario = horario;
    }

    public ArrayList<Grupo> getGrupos(){
        return grupos;
    }

    public Horario getHorario(){
        return horario;
    }

    public double getPromedio(){
        return promedio;
    }

    public void setPromedio(double promedio){
        this.promedio = promedio;
    }

    public double getAvance(){
        return avance;
    }

    public void setGrupos(ArrayList<Grupo> grupos){
        this.grupos = grupos;
    }

    public void setAvance(double avance){
        this.avance = avance;
    }

    public Beca getBeca(){
        return beca;
    }

    public void setBeca(Beca beca){
        this.beca = beca;
    }



}