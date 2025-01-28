/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

En este módulo se define la clase Salon, que es de vital importancia para trabajar en el sistema académico
para la programación de las materias, la distribución de profesores y alumnos.
 */
package gestorAplicacion.administracion;

import java.util.ArrayList;
import java.io.Serializable;

//Esta clase Salon representa los lugares dispuestos para impartir clases, se tiene en consideración
//la capacidad del salón y el horario que sigue.
public class Salon implements Serializable{
	private static final long serialVersionUID = 1L;
	private String lugar;
	private int aforo; 
	private Horario horario; //Horario en el que el salón está libre u ocupado.
	public static ArrayList<Salon> salones = new ArrayList<Salon>();
	
	
	//Constructor clase Salon.
	public Salon(String lugar, int aforo){
		this.lugar = lugar;
		this.aforo = aforo;
		this.horario = new Horario();
		salones.add(this); //Agregar el objeto Salon a la lista de objetos Salon creados.
		
	}

	//Métodos getters y setters.
	public String getLugar(){
		return lugar;
	}
	
	public void setLugar(String lugar){
		this.lugar = lugar;
	}
	
	public int getAforo(){
		return aforo;
	}

	
	public void setAforo(int aforo){
		this.aforo = aforo;
	}

	public Horario getHorario(){
		return horario;
	}
	
	public void setHorario(Horario horario){
		this.horario = horario;
	}

	public static ArrayList<Salon> getSalones(){
		return salones;
	}
	
	public static void setSalones(ArrayList<Salon> salones){
		Salon.salones = salones;	
	}
	
	//Retorna un String con información de todos los salones creados.
	public static String mostrarSalones(){
		String retorno = "";
		int posicion = 1;
		for(Salon salon:salones){
			retorno += (posicion++)+". " +salon.lugar + "\n";
		}
		return retorno;
	}
	
	
}