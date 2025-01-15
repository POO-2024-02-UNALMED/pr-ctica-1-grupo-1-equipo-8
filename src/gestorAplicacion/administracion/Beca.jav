package gestorAplicacion.administracion;
import java.util.Arraylist;
import java.io.Serializable;


public class Beca implements Serializable{
    private static final long serialversionUID=1L;
    private int cupos;
    private String convenio;
    private int creditosInscritosRequeridos;
    private int ayudaEconomica;
    private int estratoMinimo;
    private double promedioRequerido;
    private double avanceRequerido;
    private boolean necesitaRecomendacion;



    //constructor
    public Beca(int cupos, int creditosInscritosRequeridos, int ayudaEconomica, int estratoMinimo, 
            String convenio, double promedioRequerido, double avanceRequerido, boolean necesitaRecomendacion){
        this.cupos= cupos;
        this.creditosInscritosRequeridos= creditosInscritosRequeridos;
        this.ayudaEconomica= ayudaEconomica;
        this.estratoMinimo= estratoMinimo;
        this.convenio= convenio;
        this.promedioRequerido= promedioRequerido;
        this.avanceRequerido=avanceRequerido;
        this.necesitaRecomendacion=necesitaRecomendacion;
    }   

    //set y get
     
    public int getCupos(){
        return cupos;   
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

    public String getConvenio(){
        return convenio;
    }

    public void setConvenio(String convenio){
        this.convenio=convenio;
    }   

    public double getPromedioRequerido(){
        return promedioRequerido;
    } 
    
    public void setPromedioRequerido(double promedioRequerido){
        this.promedioRequerido=promedioRequerido;
    }

    public double getAvanceRequerido(){
        return avanceRequerido;
    }

    public void setAvanceRequerido(double avanceRequerido){
        this.avanceRequerido=avanceRequerido;
    }    

    public boolean getNecesitaRecomendacion(){
        return necesitaRecomendacion;
    }
    
    public void setNecesitaRecomendacion(boolean necesitaRecomendacion){
        this.necesitaRecomendacion=necesitaRecomendacion;
    }    
}