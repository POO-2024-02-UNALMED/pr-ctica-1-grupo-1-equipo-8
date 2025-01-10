package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import gestorAplicacion.administracion.Grupo;
import gestorAplicacion.administracion.Horario;
import gestorAplicacion.administracion.Materia;
import gestorAplicacion.administracion.Salon;
import gestorAplicacion.administracion.Beca;
import gestorAplicacion.usuario.Coordinador;
import gestorAplicacion.usuario.Estudiante;
import gestorAplicacion.usuario.Profesor;
import gestorAplicacion.usuario.Usuario;

public class Deserializador{

    private static <T> void deserializar(ArrayList<T> lista, String archivo){

        File file = new File("");
        FileInputStream fis;
        ObjectInputStream ois;

        try{
            File path = new File(file.getAbsolutePath() + "/src/baseDatos/temp/"+archivo+".txt");
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            lista.addAll((ArrayList<T>) ois.readObject());
            ois.close();
            fis.close();
        } catch (FileNotFoundException e){
         e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void deserializarListas(){

        //deserializar Estudiantes
        //deserializar Grupos
        //deserializar Horarios
        //deserialziar Materias
        //deserializar Profesores
        //deserializar Salones
        //deserializar Becas
        //deserializar Coordinadores
        //deserializar Usuarios

    }
}