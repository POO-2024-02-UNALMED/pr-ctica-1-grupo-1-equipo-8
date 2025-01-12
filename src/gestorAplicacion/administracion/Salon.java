//Jhoan Alexis
//Tomas Velasquez
package gestorAplicacion.administracion;

import java.util.ArrayList;
import java.io.Serializable;


public class Salon implements Serializable{
	private static final long serialVersionUID = 1L;
	private String lugar;
	private int aforo;
	private Horario horario;
	public static ArrayList<Salon> salones = new ArrayList<Salon>();
	
	
	public Salon(String lugar, int aforo){
		this.lugar = lugar;
		this.aforo = aforo;
		this.horario = new Horario();
		salones.add(this);
		
	}
	
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
	
	public static String mostrarSalones(){
		String retorno = "";
		int posicion = 1;
		for(Salon salon:salones){
			retorno += (posicion++)+". " +salon.lugar + "\n";
		}
		return retorno;
	}
	
	
}