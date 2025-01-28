//Sergio 
package gestorAplicacion.usuario;

import java.util.ArrayList;
import gestorAplicacion.administracion.*;
import java.io.Serializable;

public class Coordinador extends Usuario implements Serializable {
    private final static int limitesCreditos = 20;
    private static final long serialVersionUID = 1L;
    private static ArrayList<Coordinador> coordinadoresTotales = new ArrayList<Coordinador>();
    private static String[] facultades = { "Facultad de arquitectura", "Facultad de ciencias",
            "Facultad de ciencias agrarias", "Facultad de ciencias humanas y economicas", "Facultad de minas", "Sede" };

    // Constructor de la clase Coordinador
    public Coordinador(String facultad, long id, String nombre, String pw) {
        super(id, nombre, pw, facultad);
        super.setTipo("Coordinador");
        Coordinador.coordinadoresTotales.add(this);
    }

    // Desmatricula a el estudiante de un grupo
    public String desmatricular(Estudiante estudiante, Grupo grupo) {

        boolean estaMatriculado = grupo.existenciaEstudiante(estudiante);

        if (estaMatriculado) {
            grupo.eliminarEstudiante(estudiante);
            return "El estudiante ha sido desmatriculado de la materia y su respectivo grupo";
        } else {
            return "El estudiante no estaba matriculado";
        }
    }

    // Restaura la materia a su estado original
    public void resturarMateria(Materia materia) {
        for (int i = 0; i < materia.getGrupos().size(); i++) {
            Grupo puntero_Grupo = materia.getGrupos().get(i);
            puntero_Grupo.getProfesor().desvincularGrupo(puntero_Grupo);
            //
            for (int j = 0; j < puntero_Grupo.getEstudiantes().size(); j++) {
                Estudiante puntero_Estudiante = puntero_Grupo.getEstudiantes().get(j);
                this.desmatricular(puntero_Estudiante, puntero_Grupo);

            }
        }
    }

    // Desmatricula a el estudiante del sistema
    public void desmatricularDelSistema(Usuario estudiante) {
        Estudiante e1 = null;
        for (Estudiante e : Estudiante.getEstudiantes()) {
            if (e == (Estudiante) estudiante) { // Se hace casting del parametro de tipo Usuario a tipo Estudiante
                e1 = e; // Se guarda el estudiante en una variable auxiliar
            } // Caso de especializacion de la clase Usuario
        }
        if (e1 != null) {
            Estudiante.getEstudiantes().remove(e1);
            // Valida si se encontro al estudiante en la lista y lo remueve
        }
        for (Usuario usuario : Usuario.getUsuariosTotales()) {
            if (usuario instanceof Estudiante) {
                if (((Estudiante) usuario).equals(estudiante)) { // Especializacion del parametro de tipo Usuario
                    Usuario.getUsuariosTotales().remove(usuario);
                    break;
                    // desmatricula al estudiante del sistema
                }
            }
        }
    }

    public static Object[] crearHorario(ArrayList<Materia> materias) {
        // Recibe un arraylist de materias sobre las cuales se quiere crear un horario
        // Retorna un array de 3 elementos el cual contiene: un booleano que indica si
        // se pudo crear el horario, el horario creado y la materia que impide la
        // creacion del horario

        Object[] resultado = new Object[3];

        Horario horario = new Horario();
        boolean ok = true;
        Materia materiaObstaculo = null;

        int[] gPosible = new int[materias.size()];
        int[] mPosibles = new int[materias.size()];
        int i = 0; // indice de materias

        while (true) {
            ArrayList<String> pClases = materias.get(i).getGrupos().get(gPosible[i]).getHorario();
            if (horario.comprobarDisponibilidad(pClases)) {

                horario.ocuparHorario(materias.get(i).getGrupos().get(gPosible[i]));
                mPosibles[i] = 1;
                i++;
                if (i == materias.size()) {
                    break;
                }
                // Se verifica si se puede ocupar el horario de la materia
            } else {
                gPosible[i]++;

                if (gPosible[i] == materias.get(i).getGrupos().size()) {
                    i--;
                    horario.liberarHorario(materias.get(i).getGrupos().get(gPosible[i]).getHorario());
                    gPosible[i]++;
                    gPosible[i + 1] = 0;

                    if (gPosible[i] == materias.get(i).getGrupos().size()) {
                        int m = 0;
                        for (int k : mPosibles) {
                            if (k == 0) {
                                materiaObstaculo = materias.get(m);
                                ok = false;
                            } else {
                                m++;
                            }
                        } // Se verifica si se puede ocupar el horario de la materia
                        break;
                    }
                }

            }
        }
        resultado[0] = ok; // Indica si se pudo crear el horario
        resultado[1] = horario; // Horario creado
        resultado[2] = materiaObstaculo; // Materia que impide la creacion del horario

        return resultado;
    }

    // En caso de existir, eliminara la materia y desmatriculara a los estudiantes
    // de la misma, y a los profesores de los grupos
    public void eliminarMateria(Materia materia) {
        if (Materia.getMateriasTotales().contains(materia)) {
            Materia.getMateriasTotales().remove(materia);
            resturarMateria(materia);
        }

    }

    // Recibe los datos de una materia. En caso de no existir previamente en la
    // lista de materias totales la agrega a esta.
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

    // Recibe un estudiante y un tipo de beca. Retorna un booleano que indica si el
    // estudiante es candidato a la beca o no
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

    public static String mostrarFacultades() { // Metodo que muestra las facultades
        String retorno = "";
        int i = 1;
        for (String facultad : facultades) {
            retorno += (i++) + ". " + facultad + "\n";
        }
        return retorno;
    }

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
