//Sergio 
package gestorAplicacion.usuario;

import java.util.ArrayList;
import gestorAplicacion.administracion.*;
import java.io.Serializable;
public class Coordinador extends Usuario implements Serializable{
    private final static int limitesCreditos=20;
    private static final long serialVersionUID = 1L;
    private static ArrayList<Coordinador> coordinadoresTotales = new ArrayList<Coordinador>();
    private static String[] facultades = {"Facultad de arquitectura", "Facultad de ciencias", "Facultad de ciencias agrarias" , "Facultad de ciencias humanas y economicas", "Facultad de minas", "Sede"};

    public Coordinador(String facultad,long id, String nombre, String pw) {
        super(id, nombre,pw, facultad);
        super.setTipo("Coordinador");
        Coordinador.coordinadoresTotales.add(this);
    }

    // Desmatricula a el estudiante de un grupo
    public String desmatricular(Estudiante estudiante, Grupo grupo){

        boolean estaMatriculado = grupo.existenciaEstudiante(estudiante);

        if (estaMatriculado){
            grupo.eliminarEstudiante(estudiante);
            return "El estudiante ha sido desmatriculado de la materia y su respectivo grupo";
        }
        else{
            return "El estudiante no estaba matriculado";
        }
    }
    public void resturarMateria(Materia materia){
        for (int i=0;i<materia.getGrupos().size();i++){
            Grupo puntero_Grupo = materia.getGrupos().get(i);
            puntero_Grupo.getProfesor().desvincularGrupo(puntero_Grupo);

            for (int j=0;j<puntero_Grupo.getEstudiantes().size();j++){
                Estudiante puntero_Estudiante = puntero_Grupo.getEstudiantes().get(j);
                this.desmatricular(puntero_Estudiante, puntero_Grupo);
                // Dentro de este metodo se llama al metodo desmatricular
            }   
        }
    }
    public void desmatricularDelSistema(Usuario estudiante){
        Estudiante e1 = null;
        for (Estudiante e: Estudiante.getEstudiantes()){
            if (e ==(Estudiante)estudiante){
                e1 = e;
            }
        }
        if (e1 != null){
            Estudiante.getEstudiantes().remove(e1);
        }
        for (Usuario usuario: Usuario.getUsuariosTotales()){
            if (usuario instanceof Estudiante){
                if (((Estudiante) usuario).equals(estudiante)){
                    Usuario.getUsuariosTotales().remove(usuario);
                    break;
                }
            }
        }
    }
    public static Object[] crearHorario(ArrayList<Materia> materias){
        Object[] resultado = new Object[3];
                
        Horario horario =  new Horario();
        boolean ok = true;
        Materia materiaObstaculo = null;

        int[] gPosible = new int[materias.size()];   
        int[] mPosibles = new int[materias.size()];    
        int i =0; // indice de materias
        
        while (true){
            ArrayList<String> pClases = materias.get(i).getGrupos().get(gPosible[i]).getHorario();
            if (horario.comprobarDisponibilidad(pClases)){
                
                horario.ocuparHorario(materias.get(i).getGrupos().get(gPosible[i]));
                mPosibles[i] =1;
                i++;
                if (i==materias.size()){
                    break;
                }

            }else{
                gPosible[i]++;

                if (gPosible[i]==materias.get(i).getGrupos().size()){
                    i--;
                    horario.liberarHorario(materias.get(i).getGrupos().get(gPosible[i]).getHorario());
                    gPosible[i]++;
                    gPosible[i+1]=0;

                    if (gPosible[i]==materias.get(i).getGrupos().size()){
                        int m =0;
                        for (int k:mPosibles){
                            if (k==0){
                                materiaObstaculo = materias.get(m);
                                ok = false;
                            }else{
                                m++;
                            }
                        }
                        break;
                    }
                }

            }
        }
        resultado[0] = ok;
        resultado[1] = horario;
        resultado[2] = materiaObstaculo;

        return resultado;
    }
}
