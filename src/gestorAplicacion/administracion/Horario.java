/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

En este módulo se definen las estructuras de datos y métodos para agregar clases de un grupo en un horario
y confirmar que la persona que tenga el horario tenga o no, libre uno o varios momentos del día.


 */
package gestorAplicacion.administracion;

import java.io.Serializable;
import java.util.ArrayList;

/*
 Esta clase representa el horario que manejan tanto estudiantes como profesores, se incluyen los grupos
 a los que pertenecen y una matriz que simula la semana.
 */
public class Horario implements Serializable{
    private static final long serialVersionUID = 1L;
    private Grupo[][] horario = new Grupo[7][24];//7 dias de la semana y 24 horas del día
    private ArrayList<Grupo> grupoContenidos = new ArrayList<Grupo>();
    private static ArrayList<Horario> horariosTotales = new ArrayList<Horario>();

    public Horario() {  //Constructor de la clase Horario sin parámetros
        Horario.horariosTotales.add(this); //Agrega el objeto Horario a la lista de objetos Horario creados
    }
    //Constructor de la clase Horario, recibe el día, y la hora de inicio y final de clase con el grupo.
    public Horario(int diaSemana, int horaInicio, int horaFinal, Grupo grupo){
        grupoContenidos.add(grupo); //Agrega el grupo pasado a lista de grupos que conforman el horario.
        for (int hora = horaInicio; hora < horaFinal; hora ++){
            this.horario[diaSemana][hora] = grupo; //Ocupa los espacios de un día con un grupo, desde una hora de inicio hasta una hora final.
        }
        
    }
    //Constructor de la clase Horario sobrecargado, en este caso recibe varias clases con un grupo, y las 
    //introduce en el horario. El formato para la clase es d-hi-hf
    public Horario(ArrayList<String> horario,Grupo grupo){
        grupoContenidos.add(grupo); //Agrega el grupo pasado a lista de grupos que conforman el horario.
        for (int i = 0; i < horario.size(); i++){

            String clase = horario.get(i);

            //Hace las conversiones a enteros para el día, hora de inicio y hora final de cada String.
            int dia = Integer.parseInt(clase.substring(0, 1))-1;
            int horaInicio = Integer.parseInt(clase.substring(2, 4));
            int horaFinal = Integer.parseInt(clase.substring(5, 7));

            for (int hora =horaInicio; hora < horaFinal; hora++){
                this.horario[dia][hora] = grupo;
            }
        }
    }
    //Método que ocupa un horario con las clases y el grupo pasados como parámetros.
    public void ocuparHorario(ArrayList<String> horario, Grupo grupo){
        grupoContenidos.add(grupo); //Agrega el grupo pasado a lista de grupos que conforman el horario.

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

    //Metodo que ocupa un horario, en este caso toma el horario que tiene el grupo pasado como argumento.
    public void ocuparHorario(Grupo grupo){
        grupoContenidos.add(grupo); //Agrega el grupo pasado a lista de grupos que conforman el horario.

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
    //Método que libera el horario de las clases que se pasan como argumento.
    public void liberarHorario(ArrayList<String> horario){
        for (int i = 0; i < horario.size(); i++){

            String clase = horario.get(i);
            int dia = Integer.parseInt(clase.substring(0, 1))-1;
            int horaInicio = Integer.parseInt(clase.substring(2, 4));
            int horaFinal = Integer.parseInt(clase.substring(5, 7));

            Grupo grupoEliminado=null;

            for (int hora = horaInicio; hora < horaFinal; hora++){
                grupoEliminado = this.horario[dia][hora];
                this.horario[dia][hora] = null;
            }
            grupoContenidos.remove(grupoEliminado); //Elimina el grupo que hacía parte del horario.
        }
    }
    //Metodo que libera un horario de los grupos pasados como argumento.
    public void vaciarHorario(ArrayList<Grupo> grupos){
        for (Grupo grupo : grupos){
            liberarHorario(grupo.getHorario()); //Usamos el método anterior y le pasamos el horario del grupo en cuestión.
        }
    }

    //Este método comprueba que el horario esté libre en un intervalo de tiempo, que no haya ningún
    //grupo ocupando esas posiciones. Retorna true si hay espacio, false si no está libre.
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

    //Este método comprueba que el horario esté libre en varios momentos del día. Retorna true si ocurre
    //esto, false de lo contrario.
    public boolean comprobarDisponibilidad(ArrayList<String> clases){
        boolean ok = true;

        for (String clase : clases){
            if (!comprobarDisponibilidad(clase)){ //Usamos el método anterior para cada clase.
                ok = false; //Si un momento no está libre, cambiamos el valor de la variable a false.
            }
        }

        return ok;
    }


    //Este método devuelve un String que muestra el horario en cuestión, por día y en cada hora.
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
                String horaSiguienteConCero = "0" + (i+1);
                horario += "" + horaConCero + "-" + horaSiguienteConCero + "       ";
            }

            else if (i == 9){
                horario += "09-10" + "       ";
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

    //Métodos set y getters.
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