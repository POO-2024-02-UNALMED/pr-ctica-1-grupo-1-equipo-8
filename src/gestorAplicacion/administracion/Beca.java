/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 En este módulo se define la clase Beca, incluye los atributos y métodos que permitan crear y gestionar
 una beca dentro del sistema académico.

 */

package gestorAplicacion.administracion;
import java.util.ArrayList;
import java.io.Serializable;

/* 
Esta clase representa las becas que se encuentran activas para auxiliar económicamente a un determinado número
de estudiantes, para cada una de estas becas los postulados deben cumplir con varios requisitos: entre ellos 
créditos inscritos, estrato mínímo, promedio, avance y si es necesario, una recomendación.*/
public class Beca implements Serializable{
    
    //Atributo que permite la serialización de objetos de esta clase
    private static final long serialversionUID = 1L;
    //Atributos de instancia de la clase
    private int cupos;
    private String convenio;
    private int creditosInscritosRequeridos;
    private int ayudaEconomica;
    private int estratoMinimo;
    private double promedioRequerido;
    private double avanceRequerido;
    private boolean necesitaRecomendacion;
    //Lista objetos tipo Beca
    private static ArrayList<Beca> becas= new ArrayList<Beca>();
    


    //Constructor de la clase beca
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
        //Agrega el objeto a becas
    }   

    //Métodos
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

    //Método para obtener copia de la lista de becas
    public static ArrayList<Beca> getBecas(){
        return new ArrayList<>(becas);
    }    
    //Copia de la lista Beca

    //Método para eliminar una beca de la lista
    public static void eliminarBeca(Beca beca){
            becas.remove(beca);
       
    }
    //Elimina el objeto beca pasado como argumento si se encuentra en la lista
}