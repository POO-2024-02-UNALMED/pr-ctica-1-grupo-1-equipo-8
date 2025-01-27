package baseDatos;

import gestorAplicacion.administracion.*;
import gestorAplicacion.usuario.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializador{

    private static void serializar(ArrayList<? extends Object> lista, String archivo){

        File file = new File("");

        try{
            File path = new File(file.getAbsolutePath()+"/src/baseDatos/temp/"+archivo+".txt");

            FileOutputStream f = new FileOutputStream(path);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado");
        }
        catch(IOException e){
            System.out.println("Error de IO");
        }
    }

    public static void serializarListas(){

        serializar(Estudiante.getEstudiantes(), "Estudiantes");
        serializar(Grupo.getGruposTotales(), "Grupos");
        serializar(Horario.getHorariosTotales(), "Horarios");
        serializar(Materia.materiasTotales, "Materias");
        serializar(Coordinador.getCoordinadoresTotales(), "Coordinadores");
        serializar(Salon.salones, "Salones");
        serializar(Beca.getBecas(), "Becas");
        serializar(Profesor.getProfesores(), "Profesores");
        serializar(Usuario.getUsuariosTotales(), "Usuarios");

    }
}