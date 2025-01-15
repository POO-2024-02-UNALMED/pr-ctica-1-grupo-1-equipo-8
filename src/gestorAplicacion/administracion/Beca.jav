package gestorAplicacion.administracion;
import java.util.Arraylist;
import java.io.Serializable;


public class Beca implements Serializable;
    private static final long serialversionUID=1L;
    private int cupos;
    private String convenio;
    private int creditosInscritosRequeridos;
    private int ayudaEconomica;
    private int estratoMinimo;
    private double promedioRequerido;




//constructor
public Beca(int cupos, int creditosInscritosRequeridos, int ayudaEconomica, int estratoMinimo, String convenio, promedioRequerido,){
this.cupos= cupos;
this.creditosInscritosRequeridos= creditosInscritosRequeridos;
this.ayudaEconomica= ayudaEconomica;
this.estratoMinimo= estratoMinimo;
this.convenio= convenio;
this.promedioRequerido= promedioRequerido;
}   

//set y get
public String getConevnio(){
    return convenio;
}

public void setCupos(int cupos){
    this.cupos=cupos;
}    

public int getCreditosInscritosRequeridos() {
    return creditosInscritosRequeridos;
}

public void setCreditosInscritosRequeridos(int creditosInscritosRequeridos){
    this.creditosInscritosRequeridos=creditosInscritosRequeridos;
}    

public int getAyudaEconomica(){
    return ayudaEconomica;
}

public void setAyudaEconomica(int ayudaEconomica){
    this.ayudaEconomica=ayudaEconomica;
}   

public int getEstratoMinimo(){
    return estratoMinimo;
}

public void setEstratoMinimo(){
    this.estratoMinimo=estratoMinimo;
}