// Sergio
package gestorAplicacion.administracion;
import java.io.Serializable;
import java.util.*;
import gestorAplicacion.usuario.*;

public class Grupo implements Serializable{
	// Atributos de la clase
	private int cupos;
	private Materia materia;
	private Profesor profesor;
	private int numero; // Del grupo 
	private Salon salon;
	private Arraylist<String> horario;
	private Arraylist<Estudiante> estudiantes;
	
	// ***Constructores***

	// Primer constructor	
	public Grupo (Materia materia, int numero, Profesor profesor, Arraylist<String> horario, int cupos, Salon salon ){
		this.materia = materia;
		this.numero = numero;
		this.profesor = profesor;
		this.horario = horario;
		this.cupos = cupos;
		this.salon = salon;
		this.estudiantes = new Arraylist<Estudiante>();
	}
	// Segundo constructor
	public Grupo(Materia materia, int numero, Profesor profesor){
		this.materia = materia;
		this.numero=numero;
		this.profesor=profesor;
		Grupo.gruposTotales.add(this);
	  } 	
	// Metodos

	public String mostrarInformacionGrupo(){
		String retorno = "Numero del grupo: "+this.numero+", Profesor: "+this.profesor+ ", Horario: "+this.horario+", Cupos: "+this.cupos+"Salon: "+this.salon;
		return retorno;
	}
	public String mostrarEstudiantes(){
		String nombres = "";
		for (Estudiante estudiante : estudiantes){
			nombres += estudiante +" \n";
		}
		return nombres;
	}

	public boolean existenciaEstudiante(Estudiante estudiante){
		for( int i = 0; i < this.estudiantes.size(); i++){
			if (this.estudiantes.get(j)) //Hacen falta metodos de la clase estudiante para completarlo
		}
	}
	public void eliminarEstudiante(Estudiante estudiante){
		for (i = 0; i < this.estudiantes.size(); i++){
			// Completar metodos de la clase estudiante
		}
	}
	public static Grupo buscarGrupo(Materia materia, Grupo grupo){
		int indicei = 0;
		int indicej = 0;
		
	}
}