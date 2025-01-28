/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 En este módulo se incluyen los métodos que involucran el proceso de un coordinador académico, como matricular
 y desmatricular alumnos del sistema, crear horarios, evaluar posibles becados y agregar o eliminar materias.

 */
package gestorAplicacion.usuario;

import java.util.ArrayList;
import gestorAplicacion.administracion.*;
import java.io.Serializable;

//Es la clase encargada de gestionar los procesos académicos tanto de estudiantes como de profesores.
public class Coordinador extends Usuario implements Serializable {
    private final static int limitesCreditos = 20;
    private static final long serialVersionUID = 1L;
    private static ArrayList<Coordinador> coordinadoresTotales = new ArrayList<Coordinador>();
    private static String[] facultades = { "Facultad de arquitectura", "Facultad de ciencias",
            "Facultad de ciencias agrarias", "Facultad de ciencias humanas y economicas", "Facultad de minas", "Sede" };

    //Constructor de la clase Coordinador
    public Coordinador(String facultad, long id, String nombre, String pw) {
        super(id, nombre, pw, facultad); //Constructor clase padre.
        super.setTipo("Coordinador");
        Coordinador.coordinadoresTotales.add(this); //Agregar el Coordinador a la lista de Coordinadores creados.
    }

    // Desmatricula al estudiante del grupo, pero antes verifica si de verdad el estudiante está en el grupo.
    //Retorna un mensaje si desmatricula al alumno, o si no lo hace.
    public String desmatricular(Estudiante estudiante, Grupo grupo) {

        boolean estaMatriculado = grupo.existenciaEstudiante(estudiante);

        if (estaMatriculado) {
            grupo.eliminarEstudiante(estudiante);
            return "El estudiante ha sido desmatriculado de la materia y su respectivo grupo";
        } else {
            return "El estudiante no estaba matriculado";
        }
    }

    //Este método va por grupos, desvincula a todos los profesores y desmatricula a todos los estudiantes.
    public void resturarMateria(Materia materia) {
        for (int i = 0; i < materia.getGrupos().size(); i++) {
            Grupo puntero_Grupo = materia.getGrupos().get(i);
            puntero_Grupo.getProfesor().desvincularGrupo(puntero_Grupo);

            for (int j = 0; j < puntero_Grupo.getEstudiantes().size(); j++) {
                Estudiante puntero_Estudiante = puntero_Grupo.getEstudiantes().get(j);
                this.desmatricular(puntero_Estudiante, puntero_Grupo);
                // Dentro de este metodo se llama al metodo desmatricular
            }
        }
    }

     //Este método elimina a un estudiante de la lista de estudiantes y usuarios.
    public void desmatricularDelSistema(Usuario estudiante) {
        Estudiante e1 = null;
        for (Estudiante e : Estudiante.getEstudiantes()) {
            if (e == (Estudiante) estudiante) {
                e1 = e;
            }
        }
        if (e1 != null) {
            Estudiante.getEstudiantes().remove(e1);
        }
        for (Usuario usuario : Usuario.getUsuariosTotales()) {
            if (usuario instanceof Estudiante) {
                if (((Estudiante) usuario).equals(estudiante)) {
                    Usuario.getUsuariosTotales().remove(usuario);
                    break;
                }
            }
        }
    }

    //Método que crea un horario con las materias pasadas.
    public static Object[] crearHorario(ArrayList<Materia> materias) {
        Object[] resultado = new Object[3];

        Horario horario = new Horario();
        boolean ok = true;
        Materia materiaObstaculo = null;

        int[] gPosible = new int[materias.size()];
        int[] mPosibles = new int[materias.size()];
        int i = 0; // indice de materias

        while (true) {//Recorremos cada materia y por grupo, verificamos que sus clases se acoplen al horario.
            ArrayList<String> pClases = materias.get(i).getGrupos().get(gPosible[i]).getHorario();
            if (horario.comprobarDisponibilidad(pClases)) {
                //Si se acoplan las clases, ocupamos el horario.
                horario.ocuparHorario(materias.get(i).getGrupos().get(gPosible[i]));
                mPosibles[i] = 1;
                i++;
                if (i == materias.size()) {//Salimos si ya indagamos en todas las materias.
                    break;
                }

            } else {//Si el horario no es apto:
                gPosible[i]++; //Pasamos al siguiente grupo

                if (gPosible[i] == materias.get(i).getGrupos().size()) { //Si el grupo anterior era el último
                    i--;
                    horario.liberarHorario(materias.get(i).getGrupos().get(gPosible[i]).getHorario());//Liberamos el horario del grupo de la amteria anterior
                    gPosible[i]++; //el grupo posible pasa a ser otro
                    gPosible[i + 1] = 0; //El grupo de la materia que sigue vuelve a ser el 0.

                    if (gPosible[i] == materias.get(i).getGrupos().size()) {//Si no hay otro grupo que verificar
                        int m = 0;
                        for (int k : mPosibles) {
                            if (k == 0) { //Aquí se obtiene la materia que provoca el conflicto
                                materiaObstaculo = materias.get(m);
                                ok = false;
                            } else {
                                m++; //Siguiente materia.
                            }
                        }
                        break; //Salir del bucle.
                    }
                }

            }
        }
        resultado[0] = ok; //Resultado operación
        resultado[1] = horario; //Horario generado.
        resultado[2] = materiaObstaculo; //Materia que provoca conflicto.

        return resultado;
    }

    //Método que elimina una materia.
    public void eliminarMateria(Materia materia) {
        if (Materia.getMateriasTotales().contains(materia)) {
            Materia.getMateriasTotales().remove(materia);
            resturarMateria(materia);
        }

    }

    //Método que agrega una materia, siempre y cuando no exista otra materia con el mismo nombre.
    public void agregarMateria(String nombre, int codigo, String descripcion, int creditos, String facultad,
            ArrayList<Materia> prerrequisitos) {
        ArrayList<String> nombreMaterias = new ArrayList<String>();
        for (Materia materia : Materia.getMateriasTotales()) {
            nombreMaterias.add(materia.getNombre());
        }
        if (nombreMaterias.contains(nombre) == false) {
            Materia nMateria = new Materia(nombre, codigo, descripcion, creditos, facultad, prerrequisitos);
        }
    }

    //Método que verifica que un estudiante sea candidato a una beca. true si lo es, false de lo contrario.
    public boolean candidatoABeca(Estudiante estudiante, Beca tipoDeBeca) {
        if (tipoDeBeca.getCupos() > 0) {
            if ((estudiante.getPromedio() >= tipoDeBeca.getPromedioRequerido())
                    && (estudiante.getAvance() >= tipoDeBeca.getAvanceRequerido()) &&
                    (estudiante.getCreditos() >= tipoDeBeca.getCreditosInscritosRequeridos())
                    && (estudiante.getEstrato() <= tipoDeBeca.getEstratoMinimo())) {
                if (tipoDeBeca.getNecesitaRecomendacion()) {
                    if (Profesor.recomendarEstudiante(estudiante)) {
                        return true;
                    } else
                        return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //String que muestra todas las facultades.
    public static String mostrarFacultades() {
        String retorno = "";
        int i = 1;
        for (String facultad : facultades) {
            retorno += (i++) + ". " + facultad + "\n";
        }
        return retorno;
    }
    //Método que define lo mostrado cuando se imprime un objeto Coordinador.
    public String toString() {
        return "Nombre: " + getNombre() + "\nDocumento: " + getId();
    }

    // Getters y Setters

    public static int getLimitesCreditos() {
        return limitesCreditos;
    }

    public static ArrayList<Coordinador> getCoordinadoresTotales() {
        return coordinadoresTotales;
    }

    public static String[] getFacultades() {
        return facultades;
    }

    public static void setFacultades(String[] facultades) {
        Coordinador.facultades = facultades;
    }

}
