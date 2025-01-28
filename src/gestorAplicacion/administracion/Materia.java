/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 El módulo trabaja el concepto de materia, maneja la lógica necesaria para crear un grupo vinculado a una materia,
 admitir o no un estudiante en un grupo y realizar búsquedas de materias, estudiantes y grupos.
 */

package gestorAplicacion.administracion;
import java.util.ArrayList;
import gestorAplicacion.usuario.*;
import java.io.Serializable;

/*
 Es la clase para representar las materias que por un lado dictan los profesores y por otro, cursan los alumnos.
 Se incluyen datos como la facultad de la misma, sus cupos, prerrequisitos, código y grupos.
 */
public class Materia implements Serializable {
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

    //Constructor de la clase Materia
    public Materia( String nombre, int codigo, String descripcion,int creditos,String facultad){
        this.nombre=nombre;
        this.codigo=codigo;
        this.descripcion=descripcion;
        this.creditos=creditos;
        this.facultad = facultad;
        this.prerrequisitos = new ArrayList<Materia>(); //Se nicializa el campo prerrequisitos con un ArrayList de tipo Materia vacío.
        this.grupos = new ArrayList<Grupo>(); //Se inicializa el campo grupos con un ArrayList de tipo Grupo vacío.
        this.hacerAbreviatura(nombre);
        materiasTotales.add(this); //Agregar el objeto Materia a la lista de objetos Materia creados.
        }

        //Constructor sobrecargado que recibe la lista de prerrequisitos de la materia.
    public Materia(String nombre, int codigo, String descripcion, int creditos, String facultad, ArrayList<Materia> prerrequisitos) {
        this(nombre, codigo, descripcion, creditos, facultad);
        this.prerrequisitos = prerrequisitos;
        }

        //Constructor sobrecargado que además de recibir la lista de prerrequisitos, también admite la lista de grupos de la materia.
    public Materia(String nombre, int codigo, String descripcion,int creditos, String facultad, ArrayList<Materia> prerrequisitos, ArrayList<Grupo> grupos) {
        this(nombre, codigo, descripcion, creditos, facultad, prerrequisitos);
        this.grupos = grupos;
        }
    
    //Retorna el número de cupos disponibles que suman todos los grupos de la materia.    
    public int cantidadCupos(){
        int cantidad = 0;
        for (Grupo pGrupo:getGrupos()){
            cantidad+=pGrupo.getCupos();
            }
            return cantidad;
        }
    
        //Este método crea un grupo para la materia, con el número, profesor, horario, cupos y salón indicados. Agrega el grupo a la lista de grupos y retorna el grupo.
    public Grupo crearGrupo(int numero, Profesor profesor, ArrayList<String> horario, int cupos, Salon salon){
        Grupo grupo = new Grupo(this, numero, profesor, horario, cupos, salon);
        cantidadCupos();
        this.grupos.add(grupo);
        return grupo;
        }

        //Método que retorna un String con información relevante sobre la materia.
    public String mostrarContenidos(){
        String contenido =  "Materia: " + this.nombre +
                            "\nCodigo: " + this.codigo +
                            "\nCreditos: " + this.creditos +
                            "\nFacultad: " + this.facultad +
                            "\nDescripcion:\n" + this.descripcion;
                            return contenido;
                                
        }
    
        //Método que verifica si el grupo que se pasa hace parte de la lista de grupos de la materia. True si ocurre, false si no ocurre.
    public boolean existenciaGrupo(Grupo grupoBuscado){
        if (!grupos.isEmpty()){ //Si no hay grupos, devuelve innmediatamente false.
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

    //Este método se encarga de eliminar un grupo, en el proceso desvincula al profesor, libera el
    //salón para ese horario y reduce los cupos de la materia. Por último el grupo siguiente toma el número
    //del grupo anterior.
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
    //Este método crea un grupo, siempre y cuando se cumplan varias condiciones con el profesor y el salón.
    public void agregarGrupo(int numero, Profesor profesor, ArrayList<String> horario, int cupos, Salon salon) {
    	boolean dispSalon = true;
    	boolean dispProfesor = true;
    	boolean daMateria = profesor.daMateria(this.nombre); //Verifica que el profesor de verdad dicte la materia.
    	
        //En este bucle se comprueba que el profesor tenga el horario libre para dictar la materia
        //y que el salón también se encuentre libre
    	for(String hor:horario) {
    		dispProfesor = profesor.getHorario().comprobarDisponibilidad(hor);
    		dispSalon = salon.getHorario().comprobarDisponibilidad(hor);
    		
    		if(!dispProfesor||!dispSalon) { //Si no hay disponibilidad con profesor y/o salón se termina el proceso.
    			break;
    		}
    	}
        if(dispProfesor&&dispSalon&&daMateria) { //Si todo está en orden, se procede a crear el grupo.
    		Grupo nGrupo = crearGrupo(numero,profesor,horario,cupos,salon);
    		this.cupos += cupos; //Se suman los cupos a la materia.
    		salon.getHorario().ocuparHorario(horario, nGrupo); //Se ocupa el horario del salón
    		profesor.vincularGrupo(nGrupo);} //Se le vincula el grupo al profesor.
        }

        //Este método devuelve el grupo al que pertenece cierto estudiante pasado como parámetro.
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

    //Busca una materia por nombre y código en la lista de materias creadas, devolviendo su posición. -1 si no encuentra la materia.
    public static int buscarMateria(String nombre, int codigo){
         for (int i = 0; i < materiasTotales.size(); i++){
            if (materiasTotales.get(i).getNombre().equals(nombre) && materiasTotales.get(i).getCodigo() == codigo){
                return i;
            }
        }
        return -1;
    }
    

    //Este método evalúa si un estudiante puede pertenecer al grupo de una materia.
    //Comprobando créditos del estudiante y la materia, disponibilidad en el horario, cupos del grupo
    //y que el estudiante haya superado los prerrquesitos. Retorna true si puede, false si no es posible.
    public static boolean puedeVerMateria(Estudiante estudiante,Grupo grupo){
        if (!(estudiante.getCreditos()+grupo.getMateria().getCreditos()<=Coordinador.getLimitesCreditos())){
            return false;
        }
        if (!estudiante.getHorario().comprobarDisponibilidad(grupo.getHorario())){
            return false;
        }
        if (grupo.getCupos()==0){
            return false;
        }
        if (!comprobarPrerrequisitos(estudiante,grupo.getMateria())){
                return false;
            }
            return true;
        } 
    
        //Método que comprueba que un estudiante haya aprobado los prerrequisitos de una materia, retorna true si los supero, false de lo contrario.
        public static boolean comprobarPrerrequisitos(Estudiante estudiante,Materia materia){
        
            ArrayList<Materia> materiasVistas = new ArrayList<Materia>();
            
            for (Grupo pGrupo:estudiante.getGruposVistos()){ //Obtiene las materias aprobadas por el estudiante.
                materiasVistas.add(pGrupo.getMateria());
            }
    
            for (Materia pMateria:materia.getPrerrequisitos()){ //Por cada prerrequisito, verifica que haga parte de la lista de materias vistas por el estudiante.
                boolean flag = false;
                for(Materia pVistas:materiasVistas){
                    if (pMateria.getCodigo()==pVistas.getCodigo()){
                        flag =true;
                        break;
                    }
                }
                if(!flag){ //Si la variable se mantiene false durante un prerrequisito, retorna false inmediatamente.
                    return false;
                }
            }
            return true;
        }
    
        //Este método determina el atributo abreviatura de la materia.
        public void hacerAbreviatura(String nombre){
            String abreviatura = "";
    
            if (nombre.length() <= 13){
                this.abreviatura = nombre;
            }
            else{
                String[] palabras = nombre.split("\\s");
                for (String palabra : palabras){
                    if (palabra.length() >= 3){
                        abreviatura += palabra.substring(0, 3) + " ";
                    }
                    else{
                        abreviatura += palabra.substring(0, palabra.length()) + " ";
                    }
                }
                if (abreviatura.length() <= 13){
                    this.abreviatura = abreviatura;
                }
                else{
                    this.abreviatura = abreviatura.substring(0, 13);
                }
            }
        }
        
        //Retorna un String con información sobre los grupos de la materia.
        public String mostrarGrupos() {
            String retorno = "";
            int i = 1;
            for(Grupo grupo:this.grupos) {
                retorno += (i++)+". "+grupo.getNumero()+".\n";
            }
            return retorno;
        }
        
        //Busca y retorna una materia solo con el criterio del nombre.
        public static Materia encontrarMateria(String nombre) {
            Materia mater = null;
            for(Materia materia:Materia.getMateriasTotales()) {
                if(materia.getNombre().equals(nombre)) {
                    mater = materia;
                }
            }
            return mater;
        }
        
        //Retorna un String con el nombre de todas las materias creadas.
        public static String mostrarMaterias() {
            String retorno = "";
            int i = 1;
            for(Materia materia:Materia.materiasTotales) {
                retorno += (i++)+". "+materia.nombre+".\n";
            }
            return retorno;
        }


        //Métodos getters y setters.
    
        public String getDescripcion() {
            return descripcion;
        }
    
        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    
        public String getNombre() {
            return this.nombre;
        }
    
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public int getCodigo() {
            return this.codigo;
        }
        
        public int getCreditos() {
            return this.creditos;
        }
    
        public void setCreditos(int creditos) {
            this.creditos = creditos;
        }
    
        public String getFacultad() {
            return this.facultad;
        }
    
        public void setFacultad(String facultad) {
            this.facultad = facultad;
        }
    
        public int getCupos() {
            return this.cupos;
        }
    
        public void setCupos(int cupos) {
            this.cupos = cupos;
        }
    
        public ArrayList<Materia> getPrerrequisitos() {
            return this.prerrequisitos;
        }
    
        public void setPrerrequisitos(ArrayList<Materia> prerrequisitos) {
            this.prerrequisitos = prerrequisitos;
        }
    
        public ArrayList<Grupo> getGrupos() {
            return this.grupos;
        }
    
        public void setGrupos(ArrayList<Grupo> grupos) {
            this.grupos = grupos;
        }
    
        public static ArrayList<Materia> getMateriasTotales(){
            return materiasTotales;
        }
    
        public String getAbreviatura() {
            return abreviatura;
        }
    
        public void setAbreviatura(String abreviatura) {
            this.abreviatura = abreviatura;
        }
    }