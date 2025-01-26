package UiMain;


import gestorAplicacion.administracion.*;
import gestorAplicacion.usuario.*;

import java.util.ArrayList;
import java.util.Arrays;

import baseDatos.Serializador;
import baseDatos.Deserializador;

public class cargarDatosIniciales {

    public static void cargarDatos() {
        // Crear materias
        //Materia( String nombre, int codigo, String descripcion,int creditos,String facultad)
        Materia mat1 = new Materia("Cálculo Diferencial", 1000004, "Estudio de límites, derivadas e integrales", 4, "Ciencias");
        Materia mat2 = new Materia("Geometría Vectorial y Analítica", 1000008, "Estudio de vectores y matrices", 4, "Ciencias");
        Materia mat3 = new Materia("Introducción a la Ingeniería de Sistemas e Informática", 3010438, "Estudio de la ingeniería de sistemas", 3, "Minas");

        //Materia(String nombre, int codigo, String descripcion, int creditos, String facultad, ArrayList<Materia> prerrequisitos)
        Materia mat4 = new Materia("Cálculo Integral", 1000005, "Estudio de integrales", 4, "Ciencias", new ArrayList<Materia>(Arrays.asList(mat1)));
        Materia mat5 = new Materia("Álgebra Lineal", 1000003, "Estudio de matrices", 4, "Ciencias", new ArrayList<Materia>(Arrays.asList(mat2)));
        Materia mat6 = new Materia("Matemáticas Discretas", 3006906, "Estudio de matemáticas discretas", 3, "Ciencias", new ArrayList<Materia>(Arrays.asList(mat1)));
        Materia mat7 = new Materia("Estadística I", 3010651, "Estudio de estadística", 3, "Ciencias", new ArrayList<Materia>(Arrays.asList(mat1, mat4)));
        Materia mat8 = new Materia("Fundamentos de Programación", 3010435, "Estudio de programación", 3, "Minas", new ArrayList<Materia>(Arrays.asList(mat2,mat5)));
    
        //Materia(String nombre, int codigo, String descripcion,int creditos, String facultad, ArrayList<Materia> prerrequisitos, ArrayList<Grupo> grupos)
        Materia mat9 = new Materia("Cálculo Diferencial", 1000004, "Estudio de límites, derivadas e integrales", 4, "Ciencias", new ArrayList<Materia>(), new ArrayList<Grupo>());

        // Crear coordinadores
        //Coordinador(String facultad, long id, String nombre, String pw)
        Coordinador coord1 = new Coordinador("Ciencias", 1000000001, "Juan Perez", "1234");
        Coordinador coord2 = new Coordinador("Minas", 1000000002, "Maria Rodriguez", "1234");
        Coordinador coord3 = new Coordinador("Arquitectura", 1000000003, "Pedro Gomez", "1234");
        Coordinador coord4 = new Coordinador("Ciencias Humanas y Economicas", 1000000004, "Luisa Martinez", "1234");
        Coordinador coord5 = new Coordinador("Ciencias Agrarias", 1000000005, "Ana Garcia", "1234");

        // Crear Estudiantes
        //Estudiante(long id, String nombre, String programa, int semestre, String facultad, int estrato, int sueldo, ArrayList<Materia> materias, ArrayList<Grupo> gruposVistos)
        Estudiante est1 = new Estudiante(1000000006, "Carlos", "Ingeniería de Sistemas", 1, "Minas", 2, 1000000, new ArrayList<Materia>(Arrays.asList(mat1, mat4)), new ArrayList<Grupo>());
        Estudiante est2 = new Estudiante(1000000007, "Andres", "Ingeniería de Sistemas", 1, "Minas", 2, 1000000, new ArrayList<Materia>(Arrays.asList(mat2, mat5)), new ArrayList<Grupo>());
        Estudiante est3 = new Estudiante(1000000008, "Camila", "Ingeniería de Sistemas", 1, "Minas", 2, 1000000, new ArrayList<Materia>(Arrays.asList(mat3, mat6)), new ArrayList<Grupo>());
        Estudiante est4 = new Estudiante(1000000009, "Sofia", "Ingeniería Administrativa", 1, "Minas", 2, 1000000, new ArrayList<Materia>(Arrays.asList(mat1, mat4)), new ArrayList<Grupo>());

        // Crear Horarios
        


    }
}