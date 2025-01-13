// Tomas Velasquez

package ges torAplicacion.administracion;

import java.io.Serializable;
import java.util.ArrayList;

public class Horario implements Serializable{
    private static final long serialVersionUID = 1L;
    private Grupo[][] horario = new Grupo[7][24];//7 dias de la semana y 24 horas del dia
    private Arraylist<Grupo> grupoContenidos = new ArrayList<Grupo>()
    private static Arraylist<Horario> horariosTotales = new ArrayList<Horario>();

    public Horario() {  //Constructor de la clase Horario
        Horario.horariosTotales.add(this);
    }
    //Constructor de la clase Horario
    public Horario(int diaSemana, int horaInicio, int horaFinal, Grupo grupo){
        grupoContenidos.add(grupo);
        for (int hora = horaInicio; hora < horaFinal; hora ++){
            this.horario[diaSemana][hora] = grupo;
        }
        
    }
    //Constructor de la clase Horario
    public Horario(ArrayList<String> horario,Grupo grupo){
        grupoContenidos.add(grupo);
        for (int i = 0; i < horario.size(); i++){

            String clase = horario.get(i);
            int dia = Integer.parseInt(clase.substring(0, 1))-1;
            int horaInicio = Integer.parseInt(clase.substring(2, 4));
            int horaFinal = Integer.parseInt(clase.substring(5, 7));

            for (int hora =horaInicio; hora < horaFinal; hora++){
                this.horario[dia][hora] = grupo;
            }
        }
    }
    //Metodo que ocupa un horario
    public void ocuparHorario(ArrayList<String> horario, Grupo grupo){
        grupoContenidos.add(grupo);

        for (int i = 0; i < horario.size(); i++){

            String clase = horario.get(i);
            int dia = Integer.parseInt(clase.substring(0, 1))-1;
            int horaInicio = Integer.parseInt(clase.substring(2, 4));
            int horaFinal = Integer.parseInt(clase.substring(5, 7));

            for (int hora = horaInicio; hora < horaFinal; hora++){
                this.horario[dia][hora] = grupo;
            }
        }
    }
    //Metodo que ocupa un horario
    public void ocuparHorario(Grupo grupo){
        grupoContenidos.add(grupo);

        ArrayList<String> horario = grupo.getHorario();
        for (int i = 0; i < horario.size(); i++){

            String clase = horario.get(i);
            int dia = Integer.parseInt(clase.substring(0, 1))-1;
            int horaInicio = Integer.parseInt(clase.substring(2, 4));
            int horaFinal = Integer.parseInt(clase.substring(5, 7));

            for (int hora = horaInicio; hora < horaFinal; hora++){
                this.horario[dia][hora] = grupo;
            }
        }
    }
    //Metodo que libera un horario
    public void liberarHorario(ArrayList<String> horario){
        for (int i = 0; i < horario.size(); i++){

            String clase = horario.get(i);
            int dia = Integer.parseInt(clase.substring(0, 1))-1;
            int horaInicio = Integer.parseInt(clase.substring(2, 4));
            int horaFinal = Integer.parseInt(clase.substring(5, 7));

            Grupo grupoEliminado=null

            for (int hora = horaInicio; hora < horaFinal; hora++){
                grupoEliminado = this.horario[dia][hora];
                this.horario[dia][hora] = null;
            }
            grupoContenidos.remove(grupoEliminado);
        }
    }
    //Metodo que libera un horario
    public void vaciarHorario(ArrayList<Grupo> grupos){
        for (Grupo grupo : grupos){
            liberarHorario(grupo.getHorario());
        }
    }

    public boolean comprobarDisponibilidad(String clase){
        int dia = Integer.parseInt(clase.substring(0, 1))-1;
        int horaInicio = Integer.parseInt(clase.substring(2, 4));
        int horaFinal = Integer.parseInt(clase.substring(5, 7));

        for (int hora = horaInicio; hora < horaFinal; hora++){
            if (this.horario[dia][hora] != null){
                return false;
            }
        }

        return true;
    }

    public boolean comprobarDisponibilidad(ArrayList<String> clases){
        boolean ok = true;

        for (String clase : clases){
            if (!comprobarDisponibilidad(clase)){
                ok = false;
            }
        }

        return ok;
    }

    public String mostrarHorario(){
        enum DiaSemana{
            LUNES(0,5), MARTE(1,6), MIERCOLES(2,9), JUEVES(3,6), VIERNES(4,7), SABADO(5,6), DOMINGO(6,7);

            private final int dia;
            private int length;

            DiaSemana(int dia, int length){
                this.dia = dia;
                this.length = length;
            }

            public static DiaSemana getDiaPorIndice(int dia){
                for (DiaSemana d : DiaSemana.values()){
                    if (d.dia == dia){
                        return d;
                    }
                }
                return null;
            }
        }

        String horario = "HORA        LUNES        MARTES        MIERCOLES        JUEVES        VIERNES        SABADO        DOMINGO\n";

        for (int i = 0; i <24; i++){

            if (i < 9){
                String horaConCero = "0" + i;
                String horaSiguienteConCero = 0 + (i+1);
                horario += "" + horaConCero + "-" + horaSiguienteConCero + "       ";
            }

            else if (i == 9){
                horario += "09-10" + "       "
            }

            else{
                horario += "" + i + "-" + (i+1) + "       ";
            }

            for (int j=0; j<7; j++){
                String materia;
                if (this.horario[j][i] == null){
                    materia = "";
                }
                else{
                    materia = this.horario[j][i].getMateria().getAbreviatura();
                }
                int cantidadEspacios = ((DiaSemana.getDiaPorIndice(j).length + 8) - materia.length());
                String espacios = new String(new char[cantidadEspacios]).replace("\0", " ");
                horario += materia + espacios;
            }
            horario += "\n";
        }

        return horario;
    }

    public ArrayList<Grupo> getGrupoContenidos(){
        return grupoContenidos;
    }

    public void setGrupoContenidos(ArrayList<Grupo> grupoContenidos){
        this.grupoContenidos = grupoContenidos;
    }

    public static ArrayList<Horario> getHorariosTotales(){
        return horariosTotales;
    }
}