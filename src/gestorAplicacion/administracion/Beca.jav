package gestorAplicacion.administracion;
import java.util.Arraylist;

//constructor
public Beca(int cupos, int creditosInscritosRequeridos, int ayudaEconomica, int estratoMinimo)
this.cupos= cupos;
this.creditosInscritosRequeridos= creditosInscritosRequeridos;
this.ayudaEconomica= ayudaEconomica;
this.estratoMinimo= estratoMinimo;

//set y get
public int getCupos() {
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