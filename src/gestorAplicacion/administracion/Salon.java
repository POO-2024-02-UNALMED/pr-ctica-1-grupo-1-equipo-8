
package gestorAplicacion.administracion;

import java.util.ArrayList;


public class Salon{
	private String lugar;
	private int aforo;
	public static ArrayList<Salon> salones = new ArrayList<Salon>();
	
	
	public Salon(String lugar, int aforo){
		this.lugar = lugar;
		this.aforo = aforo;
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
			retorno += (posicion++) + salon.lugar + "\n";
		}
		return retorno;
	}
	
	
}