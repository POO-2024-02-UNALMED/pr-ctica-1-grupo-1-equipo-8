package uiMain;

import java.util.ArrayList;
import java.util.Scanner;
import gestorAplicacion.usuario.*;
import gestorAplicacion.administracion.*;
import java.util.Random;

public interface Interfaz{

    //Tomas Velasquez
    public static ArrayList<Materia> mostrarMateriasConFiltro(int opcionFiltro, String filtro){
        ArrayList<Materia> listaFiltrada = new ArrayList<Materia>();
        // por facultad
        if (opcionFiltro==1){
            for (Materia pMateria: Materia.getMateriasTotales()){
                if(pMateria.getFacultad().equalsIgnoreCase(filtro)){
                    listaFiltrada.add(pMateria);
                }
            }
        }

        // por creditos
        else if (opcionFiltro==2){
            for (Materia pMateria: Materia.getMateriasTotales()){
                if(pMateria.getCreditos()==Integer.parseInt(filtro)){
                    listaFiltrada.add(pMateria);
                }
            }
        }

        return listaFiltrada
    }
    //Tomas Velasquez
    public static void imprimirListaPorConsola(ArrayList<Materia> listaAMostrar){
        int con = 1;
        System.out.println("%-3s %-60s %-45s %-10s%n", "Num", "Nombre", "Facultad", "Codigo");

        for (Materia pMateria: listaAMostrar){
            System.out.println("%-3s %-60s %-45s %-10s%n", con, pMateria.getNombre(), pMateria.getFacultad(), pMateria.getCodigo());
            con++;
        }
    }

    public static void imprimirHorarioGenerado(ArrayList<Materia> listaAGenerar){

    }

    public static void fusionImpresiones(ArrayList<Materia> listaObjetivo){

    }

    public static void asignacionDeHorarioGenerado(Horario horario){

    }

    public static void mostrarBecas(){

    }

    public static void matricularMateria(){

        //Seleccionar estudiante

    }

    public static void matricularMateriaParte2(Estudiante estudiante){

        //selecciona materia

    }

    public static void matricularMateriaParte3(Estudiante estudiante,Materia materia){

        //seleccionar grupo

    }

    public static void matricularMateriaParte4(Estudiante estudiante,Grupo grupo){
        
        //matricularle al estudiante un grupo especifico

    }
    
    public static long generarId(){

        //un numero random entre 100000 y 999999

    }

    public static boolean existenciaUsuario(String nombre){

        //verificar si el usuario existe

    }

    public static boolean existenciaId(long id){

        //verificar si el id existe

    }

    public static Usuario encontrarUsuario(long id){

        //encontrar el usuario

    }

    public static boolean verificarPw(Usuario usuario, String pw){

        //verificar si la contraseña es correcta

    }

    public static boolean formatoHorario(String horario){
        boolean formato = false;
        if (horario.length()==7){
            String hi = horario.substring(2, 4);
            String hf = horario.substring(5, 7);
            String diaS = horario.substring(0, 1);

            if(hi.matches("\\d+")&&hf.matches("\\d+")&&diaS.matches("\\d+")){
                int horI = Integer.parseInt(hi);
                int horF = Integer.parseInt(hf);
                int dia = Integer.parseInt(diaS);

                if(dia >= 0 && dia <= 7 && horario.substring(1,2).equals("-") && hi.matches("\\d+") && horI >= 0 && horI <= 23){
                    if(horI>=0&&horI<=23&&horario.substring(4,5).equals("-")&&hi.matches("\\d+")&&horF>horI&&horF>0&&horF<=23){
                        formato = true;
                    }  
                }
            }
        }
        return formato;
    }
}