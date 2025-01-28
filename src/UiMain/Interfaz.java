/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 El módulo está compuesto de la interfaz Interfaz que contiene métodos para hacer el código más fácil
 de leer y entender.

 */
package UiMain;

import java.util.ArrayList;
import java.util.Scanner;
import gestorAplicacion.usuario.*;
import gestorAplicacion.administracion.*;
import java.util.Random;

/*
 La interfaz cuenta con métodos auxiliares para una lectura más clara del código de las funcionalidades.
 */
public interface Interfaz {

    //Devuelve una lista de materias que cumplan con el filtro ya sea de facultad, créditos o código.
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

        //por código.
        else if(opcionFiltro==3){
            for (Materia pMateria:Materia.getMateriasTotales()){
                if(Integer.toString(pMateria.getCodigo()).contains(filtro)){
                    listaFiltrada.add(pMateria);
                }
            }
        }

        return listaFiltrada;
    }

    //Método que imprime por consola el listado de materias, definiendo un formato.
    public static void imprimirListaPorConsola(ArrayList<Materia> listaAMostrar) {
        int con = 1;
        System.out.printf("%-3s %-60s %-45s %-10s%n", "Num", "Nombre", "Facultad", "Codigo"); //Formateo de lso datos mostrados en la consola.

        for (Materia pMateria : listaAMostrar) {
            System.out.printf("%-3s %-60s %-45s %-10s%n", con, pMateria.getNombre(), pMateria.getFacultad(),
                    pMateria.getCodigo()); //Se muestran todas las materias, con su nombre, facultad y código.
            con++;
        }
    }

    public static void imprimirHorarioGenerado(ArrayList<Materia> listaAGenerar){

        Scanner scanner = new Scanner(System.in);
        ArrayList<Materia> listaMateriasAGenerar = new ArrayList<Materia>();
        //Se piden lon números de las materias a incluir en el horario.
        System.out.println("Indique uno por uno los números de las materias que quiere incluir en su horario, tenga en cuenta que las primeras que envíe tendrán mayor prioridad, evite repetir materias para evitar errores y envíe 0 cuando termine");

        boolean condicion = true;
        while(condicion){
            System.out.print("-> ");
            int opt3 = scanner.nextInt(); //Capturamos.
            scanner.nextLine();
            if (opt3 != 0){
                listaMateriasAGenerar.add(listaAGenerar.get(opt3-1));//Añadimos la materia para trabajar con ella luego.
            }
            else{
                condicion = false; //Salimos del bucle.
            }
        }

        boolean ok = true;
        String materiaVacia = "";

        //Por cada materia de la lista se hace lo siguiente.
        for (Materia pMateria: listaMateriasAGenerar){
            if(pMateria.getGrupos().size() == 0){ //Si la materia no tiene grupos, no podemos seguir adelante en la generación del horario.
                ok = false; //Se marca false al existir una materia sin grupos.
                materiaVacia = pMateria.getNombre();//Útil para mostrarle al usuario cuál materia no tiene grupos.
                break; //Salimos del bucle for.
            }
        }
        if (ok){ //Si todas las materias tienen grupos continuamos.
            Object[] informacion = Coordinador.crearHorario(listaMateriasAGenerar); //Método que retorna una lista de tres elementos: resultado de la operaci{on, horario y materia obstáculo.}

            if ((boolean)informacion[0]){ //Horario creado sin problemas
                Horario pHorario = (Horario)informacion[1];
                System.out.println(pHorario.mostrarHorario());//Se muestra el horario.
                asignacionDeHorarioGenerado(pHorario);//Asignación del horario generado.
            }
            else{//Horario no creado por conflicto con alguna materia.
                System.out.println("No fue posible generar la horario, ya que " + ((Materia)informacion[2]).getNombre() + " es un obstáculo");
            }
        } else{ //Mostramos cuál materia no tiene grupos.
            System.out.println("No se pudo generar el horario, ya que la materia " + materiaVacia + " no tiene ningún grupo registrado");
        }

    }

    //Método encargado de invocar los métodos que muestran las materias por pantalla y que hacen el proceso de generación del horario.
    public static void fusionImpresiones(ArrayList<Materia> listaObjetivo) {
        if (listaObjetivo.size()!=0){
            imprimirListaPorConsola(listaObjetivo);
            imprimirHorarioGenerado(listaObjetivo);
        }
        else{
            System.out.println("Ningun elemento ha sido encontrado con el filtro dado");
        }

    }

    //Método que asigna horario a un estudiante, siempre y cuando las materias de este nuevo horario los prerrequisitos estén al día, y no haya aprobado antes alguna materia.
    public static void asignacionDeHorarioGenerado(Horario horario) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Desea Conservar el horario?\n1. Si\n2. No");
        int opt2=scanner.nextInt();//Capturamos respuesta.
        scanner.nextLine();
        if (opt2==1){//Si se desea conservar el horario, se piede elegir un estudiante que tenga la matricula pagada.
            System.out.println("Escoja un estudiante: ");
            ArrayList<Estudiante> estudiantesDisponibles = new ArrayList<>();
            int con=1;
            for (Estudiante pEstudiante : Estudiante.getEstudiantes()){
                if(pEstudiante.isMatriculaPagada()){
                    estudiantesDisponibles.add(pEstudiante);
                    System.out.printf("%-3d %-40s %-4s %-12d%n",con++,pEstudiante.getNombre(),"ID:",pEstudiante.getId());
                }
            }

            System.out.print("Eleccion: -> ");
            int opt3=scanner.nextInt();//Capturar elección.
            scanner.nextLine();

            Estudiante seleccionEstudiante = estudiantesDisponibles.get(opt3-1);
            Horario tempHorario = seleccionEstudiante.getHorario();//Obtenemos el horario del estudiante selecccionado.
            seleccionEstudiante.setHorario(new Horario());

            boolean flag = true;
            for (Grupo pGrupo:horario.getGrupoContenidos()){
                if (!Materia.puedeVerMateria(seleccionEstudiante, pGrupo)){//Nos aseguramos que el estudiante pueda ver todas las materias.
                    flag = false;
                    break; //Salimos del bucle for.
                } 
            }

            boolean mens=false;
            String nMateria="";
            for (Grupo pGrupo:seleccionEstudiante.getGruposVistos()){
                for (Grupo pGrupo1:horario.getGrupoContenidos()){
                    if (pGrupo.getMateria().getCodigo()==pGrupo1.getMateria().getCodigo()){//Nos aseguramos que el estudiante no haya cursado alguna materia.
                        flag = false;
                        mens = true;
                        nMateria = pGrupo.getMateria().getNombre();//Nombre de la materia que ya vio.
                        break;   
                    } 
                }
            }
                

            if (flag){//Si todo está en orden se procede a modificar el horario del estudiante.
                seleccionEstudiante.setHorario(horario);
                seleccionEstudiante.desmatricularMaterias();
                for (Grupo pGrupo:horario.getGrupoContenidos()){
                    matricularMateriaParte4(seleccionEstudiante, pGrupo);//Por cada materia del nuevo horario, matricula al estudiante en el grupo.
                }//Horario asignado con éxito.
                System.out.println("Horario asignado con exito al estudiante "+seleccionEstudiante.getNombre());}
                else{
                seleccionEstudiante.setHorario(tempHorario);//Se le asigna el horario que tenía de nuevo al estudiante.
                System.out.println("No es posible asignar el horario, el estudiante "+seleccionEstudiante.getNombre()+" no cumple los Pre-requisitos");//Motivo
                if(mens){
                    System.out.println("o ya vio y aprobo una materia, dicha materia puede ser: "+nMateria);//Motivo de haber visto ya una materia.
                }
            }


        }
        else{//Perdemos el horario.
            System.out.println("Horario descartado");}
        }

        //Muestra información de todas las becas activas.
    public static void mostrarBecas() {
        int i = 1;
        for (Beca beca: Beca.getBecas()){
            String a = beca.getConvenio();
            System.out.println(i +". "+ a + ".");
            i += 1;
            System.out.println("    Cupos disponibles: " + beca.getCupos()+".");
            System.out.println("    Estrato maximo para acceder: " + beca.getEstratoMinimo()+".");
            System.out.println("    Creditos inscritos requeridos: " + beca.getCreditosInscritosRequeridos()+".");

        }

    }

    //El método que trabaja la funcionalidad 1. Matricular Materia.
    public static void matricularMateria() {
        // Seleccionar estudiante
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;//Variable de control del bucle

        while (salir == false) { 
            Boolean invalido = false;
            System.out.println("Desea buscar al estudiante mediante una lista o mediante su ID o su nombre?"); //Preguntar si desea buscar al estudiante en una lista, o por nombre e id.
            System.out.println(
                    "Ingrese la opción deseada: \n1- Lista de estudiantes disponibles\2- Buscar al estudiante");
            int opcion = scanner.nextInt(); //Capturar entrada.
            scanner.nextLine();

            if (opcion == 1) { //Si la opción es con la lista, primero se hace un filtro.
                System.out.println("Lista de estudiantes disponibles para matricular: ");
                ArrayList<Estudiante> totalEstudiantes = new ArrayList<Estudiante>();
                for (Estudiante estudiante : Estudiante.getEstudiantes()) {
                    if (estudiante.isMatriculaPagada() == false) { //Por cada estudiante se verifica que su matricula esté pagada.
                        continue;//Pasar al siguiente estudiante.
                    }
                    if (estudiante.getCreditos() == Coordinador.getLimitesCreditos()) { //Que los créditos inscritos no sean iguales al límite(20)
                        continue; //Pasar al siguiente estudiante.
                    }
                    totalEstudiantes.add(estudiante); //Lo añade si no hay problemas
                    System.out.println(totalEstudiantes.size() + " Nombre: " + estudiante.getNombre() +
                            " ID: " + estudiante.getId()); //Imprime cada estudiante que supera el filtro.
                }

                System.out.println("Por favor ingrese el número correspondiente al estudiante que desea seleccionar: ");
                int opcion2 = scanner.nextInt(); //Se captura su decisión de estudiante.
                scanner.nextLine();
                if (opcion2 <= totalEstudiantes.size() && opcion2 >= 1) { 
                    Estudiante seleccionado = totalEstudiantes.get(opcion2 - 1); //Se toma al estudiante.
                    System.out.println("Estudiante seleccionado, nombre: " + seleccionado.getNombre() + " ID: "
                            + seleccionado.getId()); //Mensaje de confirmación
                    matricularMateriaParte2(seleccionado); //Segunda parte.
                    salir = true; //Salir del bucle.
                } else { //Elección no valida.
                    System.out.println("Opción invalida");
                    invalido = true;
                }
            } else if (opcion == 2) { //Opción de buscar por nombre e id.
                System.out.println("Por favor ingrese el nombre del estudiante: ");
                String nombre = scanner.nextLine();
                System.out.println("Por favor ingrese el ID del estudiante");
                long id = 0;
                String idPrueba = scanner.nextLine();
                //Recibimos nombre e id.
                try { //En este bloque de código puede ocurrir un error.
                    int index = Estudiante.buscarEstudiante(nombre, id); //obtenemos índice del estudiante, aquí tenemos -1 si no existe.
                    id = Long.parseLong(idPrueba); //Aquí puede ocurrir un error si el String no tiene el formato correcto.

                    if (index == -1) {
                        System.out.println("Estudiante no encontrado");
                        invalido = true; //No encontramos el estudiante.
                    } else {
                        Estudiante seleccionado = Estudiante.getEstudiantes().get(index); //Estudiante encontrado.
                        if (seleccionado.isMatriculaPagada() == false) { //Que su matricula esté pagada.
                            System.out.println("La matricula del estudiante no está pagada");
                            invalido = true;
                        } else if (seleccionado.getCreditos() >= Coordinador.getLimitesCreditos()) { //Que sus créditos inscritos no sean iguales al límite (20).
                            System.out.println("El estudiante no puede matricular más materias");
                            invalido = true;
                        } else { //Si todo está en orden, seguimos en la segunda parte de la funcionalidad.
                            System.out.println("Estudiante seleccionado, nombre: " + seleccionado.getNombre() +
                                    " ID: " + seleccionado.getId());
                            matricularMateriaParte2(seleccionado); //Parte dos.
                            salir = true;
                        }
                    }
                } catch (NumberFormatException e) { //Atrapa el error, si el String que representa el id no tiene el formato correcto.
                    System.out.println("Opción invalida");
                    invalido = true;
                }
            } else { //Opción no valida.
                System.out.println("opción inválida");
                invalido = true;
            }
            if (invalido) { //En caso de ingresar los datos erróneamente, se pregunta si quiere intentarlo de nuevo o salir
                System.out.println("Desea intentarlo otra vez o desea salir?");
                System.out.println("Ingrese la opción deseada: \n1- Intentarlo otra vez\n2- Salir");
                int opcion3 = scanner.nextInt();
                scanner.nextLine();
                if (opcion3 != 1) {
                    salir = true;//Salimos
                }
            }
        }

    }

    //Segunda parte de la funcionalidad matricular materia, con el estudiante identificado, continuamos con la parte de elecci{on de la materia.}
    public static void matricularMateriaParte2(Estudiante estudiante) {

        Scanner scanner = new Scanner(System.in);
        boolean salir = false; //Variable de control del bucle.
        while (salir == false) {

            Boolean invalido = false;
            System.out.println( 
                    "Como desea buscar la materia?\n1- Mediante una lista de las materias disponibles\n2- Mediante una busqueda manual");//Preguntamos si desea buscar la materia de una lista, o con su nombre y código.
            int opcion = scanner.nextInt();//Capturamos entrada.
            scanner.nextLine();
            ArrayList<Materia> materiasTotales = new ArrayList<Materia>(Materia.getMateriasTotales());//Lista de todas las materias creadas.
            if (opcion == 1) {
                //Si elige mediante lista, debemos filtrar antes las materias de la lista de materias creadas.
                ArrayList<Materia> materiasDisponibles = new ArrayList<Materia>();
                System.out.println("Lista de materias disponibles para matricular: ");
                int limitesCreditos = Coordinador.getLimitesCreditos(); //Límite créditos.
                for (Materia materia : materiasTotales) {
                    if (Materia.comprobarPrerrequisitos(estudiante, materia) == false) { //Que el estudiante haya aprobado los prerrequisitos.
                        continue;//pasamos a la siguiente materia.
                    }
                    if (materia.getCupos() <= 0) { //Que la materia tenga cupos.
                        continue;//pasamos a la siguiente materia.
                    } else if (estudiante.getCreditos() + materia.getCreditos() > limitesCreditos) { //Que si matricula la materia, los créditos inscritos no sobrepasen el límite.
                        continue;//pasamos a la siguiente materia.
                    }
                    if (estudiante.getMaterias().contains(materia)) { //Que el estudiante no se encuentre viéndola en el semestre.
                        continue;//pasamos a la siguiente materia.
                    }
                    materiasDisponibles.add(materia); //La agregamos si cumple.
                    System.out.println(materiasDisponibles.size() + " Nombre: " + materia.getNombre() + " Cupos: "
                            + materia.getCupos()); //La enseñamos.
                }
                System.out.println("Por favor ingrese el numero correspondiente a la materia que desea matricular");
                int eleccion = scanner.nextInt(); //Capturamos decisión de materia a matricular.
                scanner.nextLine();

                if (1 <= eleccion && eleccion <= materiasDisponibles.size()) {
                    //Elección apropiada.
                    Materia seleccionada = materiasDisponibles.get(eleccion - 1);
                    System.out.println("Materia seleccionada " + seleccionada.getNombre());
                    matricularMateriaParte3(estudiante, seleccionada); //Parte tres.
                    salir = true;//Salir del bucle.

                } else {
                    //opción no valida.
                    System.out.println("Opcion invalida");
                    invalido = true;
                }

            } else if (opcion == 2) {

                System.out.println("Por favor ingrese el nombre de la materia deseada: ");
                String nombre = scanner.nextLine();
                System.out.println("Por favor ingrese el codigo de la materia deseada: ");
                String codigoPrueba = scanner.nextLine();
                int codigo;
                //Recibimos nombre y código de la materia.
                try {
                    codigo = Integer.parseInt(codigoPrueba); //Puede generar error si el String no está en el formato correcto.

                    int index = Materia.buscarMateria(nombre, codigo); //Recibimos el índice de la materia.

                    if (index == -1) { //El índice es -1 si no es encontrada la materia.

                        System.out.println("Materia no encontrada");
                        invalido = true;

                    } else {

                        Materia seleccionada = materiasTotales.get(index);
                        //Si la materia es encontrada, verifica que los cupos no estém agotados, que si el estudiante
                        //matricula la materia la cantidad de créditos incritos no supere el límite, que los
                        //prerrequisitos se cumplan y que el estudiante no esté cursando esta maeria.
                        if (seleccionada.getCupos() == 0) {
                            System.out.println("Materia sin cupos disponibles");
                            invalido = true;
                        } else if (Materia.comprobarPrerrequisitos(estudiante, seleccionada) == false) {
                            System.out.println("El estudiante no cumple con los prerrequisitos de la materia");
                            invalido = true;
                        } else if (estudiante.getCreditos() + seleccionada.getCreditos() > Coordinador
                                .getLimitesCreditos()) {
                            System.out.println("El estudiante tiene creditos insuficientes");
                            invalido = true;
                        } else if (estudiante.getMaterias().contains(seleccionada)) {
                            System.out.println("El estudiante ya esta viendo esta materia");
                            invalido = true;
                        } else { //Si todo está en orden, se confirma la selección y se continúa el proceso.
                            System.out.println("Materia seleccionada " + seleccionada.getNombre());
                            matricularMateriaParte3(estudiante, seleccionada);//tercera parte.
                            salir = true; //salir del bucle.
                        }
                    }
                } catch (NumberFormatException e) { //Atrapa el error en caso de que se presente.
                    System.out.println("Opcion invalida");
                    invalido = true;
                }

            } else { //opción no valida.

                System.out.println("Opcion invalida");
                invalido = true;
            }

            if (invalido) {
                //Si en algún momento, el usuario se equivoca al ingresar algún dato, se pregunta si se quiere volver 
                //a intentar el proceso o salir.
                System.out.println("Desea intentarlo otra vez o desea salir?");
                System.out.println("Ingrese la opcion deseada: \n1- Intentarlo otra vez\n2- Salir");
                int opcion2 = scanner.nextInt(); //capturamos.
                scanner.nextLine();
                if (opcion2 != 1) {
                    salir = true;//Salimos.
                }

            }
        }

    }

    //Tercera parte de la funcionalidad matricular materia. Con estudiante y materia listos
    //es momento de seleccionar el grupo.
    public static void matricularMateriaParte3(Estudiante estudiante, Materia materia) {

        Scanner scanner = new Scanner(System.in);
        Boolean salir = false; //Variable de control del bucle.

        while (salir == false) {
            //Antes de enseñar los grupos hay que aplicar un filtro.
            System.out.println("Grupos disponibles para matricular: ");
            ArrayList<Grupo> gruposDisponibles = new ArrayList<Grupo>();
            boolean mostro = false;
            for (Grupo grupo : materia.getGrupos()) {
                if (!estudiante.getHorario().comprobarDisponibilidad(grupo.getHorario())) { //Que el estudiante tenga el horario libre para acomodar el grupo.
                    continue;//pasamos al otro grupo.
                }
                if (grupo.getCupos() != 0) {//que haya cupos en el grupo.
                    gruposDisponibles.add(grupo); //añadimos al grupo candidato.
                    mostro = true; //Esta materia se vuelve true cuando hay almenos un grupo con cupos.
                    System.out.println((gruposDisponibles.size()) + " Grupo #" + grupo.getNumero() + " cupos: "
                            + grupo.getCupos() + " Profesor: " + grupo.getProfesor().getNombre());//Mostrar el grupo.
                }
            }
            if (mostro == false) { //Esto es que ningún grupo tenga cupos, en este caso no podemos seguir.
                System.out.println("La materia no cuenta con grupos disponibles para el estudiante");
                salir = true;
                break;//salir del bucle.
            }

            int opcion = scanner.nextInt(); //Elección del usuario.
            scanner.nextLine();
            if (opcion > 0 && opcion <= gruposDisponibles.size()) {
                //Si se hace una elección correcta se sigue:
                Grupo grupoSeleccionado = gruposDisponibles.get(opcion - 1);
                ArrayList<Materia> materiasInscritas = new ArrayList<Materia>(estudiante.getMaterias());//Lista de materias inscritas del estudiante.
                ArrayList<Grupo> gruposInscritos = new ArrayList<Grupo>(estudiante.getGrupos());//Lista de grupos a los que pertenece el estudiante.
                gruposInscritos.add(grupoSeleccionado); //agregar grupo
                materiasInscritas.add(materia); //agregar materia
                grupoSeleccionado.agregarEstudiante(estudiante);//agregar estudiante a la lista de estudiantes del grupo
                grupoSeleccionado.getMateria().cantidadCupos();
                estudiante.setCreditos(estudiante.getCreditos() + materia.getCreditos());//actualiza los créditos inscritos del estudiante.
                estudiante.setMaterias(materiasInscritas);//Actualiza las materias inscritas del estudiante.
                estudiante.setGrupos(gruposInscritos); //Actualiza los grupos inscritos del estudiante.
                String imprimir = "Materia " + materia.getNombre() + " - grupo #" + grupoSeleccionado.getNumero();
                System.out.println(imprimir + ". Ha sido matriculado al estudiante: " + estudiante.getNombre());//Mensaje de matricula exitosa.
                salir = true;
                estudiante.getHorario().ocuparHorario(grupoSeleccionado); //Se ocupa el horario del estudiante con las clases del grupo.

                System.out.println("Desea visualizar el horario del estudiante?: \n1- Si\n2- No");//Mostrar o no, el horario del estudiante.
                int opcion2 = scanner.nextInt();
                scanner.nextLine();

                if (opcion2 == 1) {
                    System.out.println(estudiante.getHorario().mostrarHorario()); //Muestra horario.
                }

            } else { //Opción no valida, salir o volver a intentar.
                System.out.println("Opcion invalida");
                System.out.println("Desea intentarlo otra vez o desea salir?");
                System.out.println("Ingrese la opcion deseada: \n1- Intentarlo otra vez\n2- Salir");
                int opcion3 = scanner.nextInt();
                scanner.nextLine();
                if (opcion3 != 1) {
                    salir = true;
                }
            }
        }

    }

    //Hace todo el proceso de vinculas un estudiante a un grupo.
    public static void matricularMateriaParte4(Estudiante estudiante, Grupo grupo) {
        ArrayList<Materia> materiasInscritas= new ArrayList<Materia>(estudiante.getMaterias());
        materiasInscritas.add(grupo.getMateria());
        grupo.agregarEstudiante(estudiante);
        grupo.getMateria().setCupos(grupo.getMateria().getCupos()-1);
        grupo.setCupos(grupo.getCupos()-1);
        ArrayList<Grupo> gruposInscritos= new ArrayList<Grupo>(estudiante.getGrupos());
        gruposInscritos.add(grupo);
        estudiante.setGrupos(gruposInscritos);
        estudiante.setCreditos(estudiante.getCreditos()+grupo.getMateria().getCreditos());
        estudiante.setMaterias(materiasInscritas);


        // matricularle al estudiante en un grupo especifico

    }

    //Este método genera un id aleatoriamente, siempre procurando que no haya otro usuario que lo tenga.
    public static long generarId() {

        // un numero random entre 100000 y 999999
        int min = 10000;
    	int max = 99999;
    	int id;
    	boolean existe = false;
    	do {
    		id = new Random().nextInt(max-min)+min;
    		for (Usuario usuario:Usuario.getUsuariosTotales()) {
    			if (usuario.getId()==id) {
    				existe=true;
    				break;
    			}
    		}
    	}while(existe);
    	return id;

    }

    //Este método indaga en la lista de usuarios totales para verificar la existencia de un usuario
    //cuyo nombre coincida con el pasado. True si existe, false de lo contrario.
    public static boolean existenciaUsuario(String nombre) {

        boolean existe = false;

        for (Usuario usuario : Usuario.getUsuariosTotales()) {
            if (usuario.getNombre() == nombre) {
                existe = true;
                break;
            }
        }

        return existe;

    }

    //Método que verifica que algún usuario tenga el id.
    public static boolean existenciaId(long id) {

        // verificar si el id existe
        boolean exist = false;
    	for (Usuario usuario:Usuario.getUsuariosTotales()) {
    		if (usuario.getId()==id) {
    			exist = true;
    			break;
    		}
    	}
    	return exist;

    }

    //Devuelve al usuario que tiene el id pasado.
    public static Usuario encontrarUsuario(long id) {
        // encontrar el usuario
        Usuario encontrado = null;
    	for (Usuario usuario:Usuario.getUsuariosTotales()) {
    		if (usuario.getId()==id) {
    			encontrado = usuario;
    		}
    	}
    	return encontrado;


    }

    //Método que verifica el password del usuario. True si es correcto, false si es incorrecto.
    public static boolean verificarPw(Usuario usuario, String pw) {

        // verificar si la contraseña es correcta
        if (usuario.getPw().equals(pw)) {
            return true;
        } else {
            return false;
        }

    }

    //Verifica que un String representa de forma correcta un horario.
    public static boolean formatoHorario(String horario) {
        boolean formato = false;
        if (horario.length() == 7) {
            String hi = horario.substring(2, 4);
            String hf = horario.substring(5, 7);
            String diaS = horario.substring(0, 1);

            if (hi.matches("\\d+") && hf.matches("\\d+") && diaS.matches("\\d+")) {
                int horI = Integer.parseInt(hi);
                int horF = Integer.parseInt(hf);
                int dia = Integer.parseInt(diaS);

                if (dia >= 0 && dia <= 7 && horario.substring(1, 2).equals("-") && hi.matches("\\d+") && horI >= 0
                        && horI <= 23) {
                    if (horI >= 0 && horI <= 23 && horario.substring(4, 5).equals("-") && hi.matches("\\d+")
                            && horF > horI && horF > 0 && horF <= 23) {
                        formato = true;
                    }
                }
            }
        }
        return formato;
    }
}