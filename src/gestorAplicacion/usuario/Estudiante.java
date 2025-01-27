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

    public void eliminarMateria(Materia materia){
        this.materias.remove(materia);
        this.creditos -= materia.getCreditos();
    }

    public void eliminarGrupo(Grupo grupo){
        this.grupos.remove(grupo);
        this.eliminarMateria(grupo.getMateria());
    }

    public boolean pagarMatricula(){
        if (this.sueldo >= this.valorMatricula){
            this.sueldo -= this.valorMatricula;
            this.matriculaPagada = true;
            return true;
        }
        
        this.matriculaPagada = false;
        return false;
    }

    private void calcularPromedio(){
        double promedio = 0;

        for (double nota: this.notas){
            promedio += nota;
        }

        promedio = promedio / ((double) this. notas.size());
        this.promedio = promedio;
    }

    public void calcularAvance(){
        
        double creditosVistos = 0;

        for (Grupo pGrupo: gruposVistos){
            creditosVistos += pGrupo.getMateria().getCreditos(); 
        }

        this.avance = (creditosVistos *100.0)/creditosParaGraduarse;
    }

    public void agregarNota(double nota){
        this.notas.add(nota);
        this.calcularPromedio();
    }

    public static String mostrarEstudiantes(){
        String estudiantes ="";
        int i = 1;
        for (Estudiante estudiante: Estudiante.estudiantes){
            estudiantes += "\n" + (i++) + ". " + estudiante;
        }
        return estudiantes.substring(1, estudiantes.length());
    }

    public Materia buscarMateriaPorNombre(String nombre){
        for (Materia materia : this.materias){
            if (materia.getNombre().equals(nombre)){
                return materia;
            }
        }
        return null;
    }

    public boolean buscarMateriaEnInscritas(String nombre, int codigo){
        for (Materia materia : this.materias){
            if (materia.getNombre().equals(nombre) && materia.getCodigo()==codigo){
                return true;
            }
        }
        return false;
    }

    // Todas las materias
    public void desmatricularMaterias(){
        ArrayList<Grupo> gruposEliminar = new ArrayList<>();
        this.setMaterias(new ArrayList<Materia>());
        for(Grupo grupoE: this.grupos){
            Grupo grupo = Grupo.buscarGrupo(grupoE.getMateria(), grupoE);
            grupo.getMateria().setCupos(grupo.getMateria().getCupos()+1);
            this.setCreditos(this.getCreditos()-grupo.getMateria().getCreditos());
            gruposEliminar.add(grupo);
        }
        int num = gruposEliminar.size();
        for (int i = 0; i < num; i++){
            gruposEliminar.get(i).eliminarEstudiante(this);
        }
    }

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