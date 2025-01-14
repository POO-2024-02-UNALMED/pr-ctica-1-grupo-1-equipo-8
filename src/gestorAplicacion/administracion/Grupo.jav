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
	
}