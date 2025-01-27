//Lina


package gestorAplicacion.administracion;

import java.util.ArrayList;
import java.io.Serializable;


public class Beca implements Serializable{
    
    //atributo que permite la serializacion de objetos de esta clase
    private static final long serialversionUID=1L;
    //atributos de la clase
    private int cupos;
    private String convenio;
    private int creditosInscritosRequeridos;
    private int ayudaEconomica;
    private int estratoMinimo;
    private double promedioRequerido;
    private double avanceRequerido;
    private boolean necesitaRecomendacion;
    private static ArrayList<Beca> becas= new ArrayList<Beca>();
    //lista objetos tipo Beca
    


    //constructor

    public Beca(int cupos, String convenio, double promedioRequerido, double avanceRequerido, int estratoMinimo,
    int creditosInscritosRequeridos, int ayudaEconomica, boolean necesitaRecomendacion){
        this.cupos= cupos;
        this.creditosInscritosRequeridos= creditosInscritosRequeridos;
        this.ayudaEconomica= ayudaEconomica;
        this.estratoMinimo= estratoMinimo;
        this.convenio= convenio;
        this.promedioRequerido= promedioRequerido;
        this.avanceRequerido=avanceRequerido;
        this.necesitaRecomendacion=necesitaRecomendacion;
        becas.add(this);
        //agrega el objeto a becas
    }   

    //metodos
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

    public void setEstratoMinimo(int estratoMinimo){
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

    //metodo para obtener copia de la lista de becas
    public static ArrayList<Beca> getBecas(){
        return new ArrayList<>(becas);
    }    
    //copia de la lista Beca

    //metodo para eliminar una beca de la lista
    public static void eliminarBeca(Beca beca){
            becas.remove(beca);
       
    }
    //elimina objeto beca pasado como argumento si se encuentra en la lista
}