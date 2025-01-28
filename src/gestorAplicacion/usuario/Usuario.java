/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 El módulo agrupa toda la información para tratar tanto a estudiantes, profesores y coordinadores
 como usuarios del sistema.
 */

package gestorAplicacion.usuario;

import java.util.ArrayList;
import java.io.Serializable;

import gestorAplicacion.administracion.Materia;


//Es la clase abstracta que representa a los actores que hacen parte del sistema acádemico. Cada actor es:
//estudiante, profesor o coordinador, pertenece a una facultad y cuenta con nombre e id.
public abstract class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    protected long id;
    protected String nombre;
    protected String tipo;
    protected String pw;
    protected String facultad;
    protected static ArrayList<Usuario> usuariosTotales = new ArrayList<Usuario>();

    //Constructor clase Usuario.
    public Usuario (long id, String nombre, String facultad){
        this.id = id;
        this.nombre = nombre;
        this.facultad = facultad;
        usuariosTotales.add(this);
    }

    //Constructor sobrecargado.
    public Usuario (long id, String nombre, String pw, String facultad){
        this.id = id;
        this.nombre = nombre;
        this.pw = pw;
        this.facultad = facultad;
        usuariosTotales.add(this); //Agregar el objeto usuario a la lista de usuarios creados.
    }

    public abstract String toString(); //Método abstracto.


    //Retorna un String con todos los usuarios creados, indicando nombre e id.
    public static String mostrarUsuarios(){
        String retorno = "";
    	int i = 1;
    	for(Usuario usuario:usuariosTotales) {
    		retorno += (i++)+". "+usuario.nombre+", id: "+usuario.id+"\n";
    	}
    	return retorno;
    }


    //Método que comprueba que el usuario y otro usuario pasado como parámetro, compartan facultad.
    //True si ocurre esto, false si no ocurre.
    public boolean comprobacionFacultad(Usuario usuario){
        String facultad1=this.getFacultad().toLowerCase();
        String facultad2=usuario.getFacultad().toLowerCase();
        if (facultad1.equals(facultad2)){
            return true;
        }
        return false;
    }


    //Método que desmatricula a un usuario del sistema.
    public void desmatricularDelSistema(Usuario usuario){

        for (Usuario u: Usuario.getUsuariosTotales()){

            if (usuario.equals(u)){
                Usuario.getUsuariosTotales().remove(usuario);
            }
        }
    }

    //Método que elimina una materia de las materias totales.
    public void eliminarMateria(Materia materia){
        Materia.getMateriasTotales().remove(materia);
    }


    //Método que agrega una nueva materia.
    public void agregarMateria(String nombre, int codigo, String descripcion, int creditos, String facultad, ArrayList<Materia> prerrequisitos){
        Materia nuevaMateria = new Materia(nombre,codigo,descripcion,creditos,facultad,prerrequisitos);
        Materia.getMateriasTotales().add(nuevaMateria);
    }  
    
    
    //Métodos getters y setters.

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getPw(){
        return pw;
    }

    public void setPw(String pw){
        this.pw = pw;
    }

    public String getFacultad(){
        return facultad;
    }

    public void setFacultad(String facultad){
        this.facultad = facultad;
    }

    public static ArrayList<Usuario> getUsuariosTotales(){
        return Usuario.usuariosTotales;
    }

    public static void setUsuariosTotales(ArrayList<Usuario> usuariosTotales){
        Usuario.usuariosTotales = usuariosTotales;
    }
}