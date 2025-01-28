package UiMain;

import java.util.ArrayList;
import java.util.Scanner;
import gestorAplicacion.usuario.*;
import gestorAplicacion.administracion.*;
import java.util.Random;

public interface Interfaz {

    // Tomas Velasquez
    public static ArrayList<Materia> mostrarMateriasConFiltro(int opcionFiltro, String filtro) {
        ArrayList<Materia> listaFiltrada = new ArrayList<Materia>();
        // por facultad
        if (opcionFiltro == 1) {
            for (Materia pMateria : Materia.getMateriasTotales()) {
                if (pMateria.getFacultad().equalsIgnoreCase(filtro)) {
                    listaFiltrada.add(pMateria);
                }
            }
        }

        // por creditos
        else if (opcionFiltro == 2) {
            for (Materia pMateria : Materia.getMateriasTotales()) {
                if (pMateria.getCreditos() == Integer.parseInt(filtro)) {
                    listaFiltrada.add(pMateria);
                }
            }
        }

        else if (opcionFiltro == 3) {
            for (Materia pMateria : Materia.getMateriasTotales()) {
                if (Integer.toString(pMateria.getCodigo()).contains(filtro)) {
                    listaFiltrada.add(pMateria);
                }
            }
        }

        return listaFiltrada;
    }

    // Tomas Velasquez
    public static void imprimirListaPorConsola(ArrayList<Materia> listaAMostrar) {
        int con = 1;
        System.out.printf("%-3s %-60s %-45s %-10s%n", "Num", "Nombre", "Facultad", "Codigo");

        for (Materia pMateria : listaAMostrar) {
            System.out.printf("%-3s %-60s %-45s %-10s%n", con, pMateria.getNombre(), pMateria.getFacultad(),
                    pMateria.getCodigo());
            con++;
        }
    }

    public static void imprimirHorarioGenerado(ArrayList<Materia> listaAGenerar) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Materia> listaMateriasAGenerar = new ArrayList<Materia>();

        System.out.println(
                "Indique uno por uno los números de las materias que quiere incluir en su horario, tenga en cuenta que las primeras que envíe tendrán mayor prioridad, evite repetir materias para evitar errores y envíe 0 cuando termine");

        boolean condicion = true;
        while (condicion) {
            System.out.print("-> ");
            int opt3 = scanner.nextInt();
            scanner.nextLine();
            if (opt3 != 0) {
                listaMateriasAGenerar.add(listaAGenerar.get(opt3 - 1));
            } else {
                condicion = false;
            }
        }

        boolean ok = true;
        String materiaVacia = "";

        for (Materia pMateria : listaMateriasAGenerar) {
            if (pMateria.getGrupos().size() == 0) {
                ok = false;
                materiaVacia = pMateria.getNombre();
                break;
            }
        }
        if (ok) {
            Object[] informacion = Coordinador.crearHorario(listaMateriasAGenerar);

            if ((boolean) informacion[0]) {
                Horario pHorario = (Horario) informacion[1];
                System.out.println(pHorario.mostrarHorario());
                asignacionDeHorarioGenerado(pHorario);
            } else {
                System.out.println("No fue posible generar la horario, ya que " + ((Materia) informacion[2]).getNombre()
                        + " es un obstáculo");
            }
        } else {
            System.out.println("No se pudo generar el horario, ya que la materia " + materiaVacia
                    + " no tiene ningún grupo registrado");
        }

    }

    public static void fusionImpresiones(ArrayList<Materia> listaObjetivo) {
        if (listaObjetivo.size() != 0) {
            imprimirListaPorConsola(listaObjetivo);
            imprimirHorarioGenerado(listaObjetivo);
        } else {
            System.out.println("Ningun elemento ha sido encontrado con el filtro dado");
        }

    }

    public static void asignacionDeHorarioGenerado(Horario horario) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Desea Conservar el horario?\n1. Si\n2. No");
        int opt2 = scanner.nextInt();
        scanner.nextLine();
        if (opt2 == 1) {
            System.out.println("Escoja un estudiante: ");
            ArrayList<Estudiante> estudiantesDisponibles = new ArrayList<>();
            int con = 1;
            for (Estudiante pEstudiante : Estudiante.getEstudiantes()) {
                if (pEstudiante.isMatriculaPagada()) {
                    estudiantesDisponibles.add(pEstudiante);
                    System.out.printf("%-3d %-40s %-4s %-12d%n", con++, pEstudiante.getNombre(), "ID:",
                            pEstudiante.getId());
                }
            }

            System.out.print("Eleccion: -> ");
            int opt3 = scanner.nextInt();
            scanner.nextLine();

            Estudiante seleccionEstudiante = estudiantesDisponibles.get(opt3 - 1);
            Horario tempHorario = seleccionEstudiante.getHorario();
            seleccionEstudiante.setHorario(new Horario());

            boolean flag = true;
            for (Grupo pGrupo : horario.getGrupoContenidos()) {
                if (!Materia.puedeVerMateria(seleccionEstudiante, pGrupo)) {
                    flag = false;
                    break;
                }
            }

            boolean mens = false;
            String nMateria = "";
            for (Grupo pGrupo : seleccionEstudiante.getGruposVistos()) {
                for (Grupo pGrupo1 : horario.getGrupoContenidos()) {
                    if (pGrupo.getMateria().getCodigo() == pGrupo1.getMateria().getCodigo()) {
                        flag = false;
                        mens = true;
                        nMateria = pGrupo.getMateria().getNombre();
                        break;
                    }
                }
            }

            if (flag) {
                seleccionEstudiante.setHorario(horario);
                seleccionEstudiante.desmatricularMaterias();
                for (Grupo pGrupo : horario.getGrupoContenidos()) {
                    matricularMateriaParte4(seleccionEstudiante, pGrupo);
                }
                System.out.println("Horario asignado con exito al estudiante " + seleccionEstudiante.getNombre());
            } else {
                seleccionEstudiante.setHorario(tempHorario);
                System.out.println("No es posible asignar el horario, el estudiante " + seleccionEstudiante.getNombre()
                        + " no cumple los Pre-requisitos");
                if (mens) {
                    System.out.println("o ya vio y aprobo una materia, dicha materia puede ser: " + nMateria);
                }
            }

        } else {
            System.out.println("Horario descartado");
        }
    }

    public static void mostrarBecas() {
        int i = 1;
        for (Beca beca : Beca.getBecas()) {
            String a = beca.getConvenio();
            System.out.println(i + ". " + a + ".");
            i += 1;
            System.out.println("    Cupos disponibles: " + beca.getCupos() + ".");
            System.out.println("    Estrato maximo para acceder: " + beca.getEstratoMinimo() + ".");
            System.out.println("    Creditos inscritos requeridos: " + beca.getCreditosInscritosRequeridos() + ".");

        }

    }

    public static void matricularMateria() {
        // Seleccionar estudiante
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (salir == false) {
            Boolean invalido = false;
            System.out.println("Desea buscar al estudiante mediante una lista o mediante su ID o su nombre?");
            System.out.println(
                    "Ingrese la opción deseada: \n1- Lista de estudiantes disponibles\2- Buscar al estudainte");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                System.out.println("Lista de estudiantes disponibles para matricular: ");
                ArrayList<Estudiante> totalEstudiantes = new ArrayList<Estudiante>();
                for (Estudiante estudiante : Estudiante.getEstudiantes()) {
                    if (estudiante.isMatriculaPagada() == false) {
                        continue;
                    }
                    if (estudiante.getCreditos() == Coordinador.getLimitesCreditos()) {
                        continue;
                    }
                    totalEstudiantes.add(estudiante);
                    System.out.println(totalEstudiantes.size() + " Nombre: " + estudiante.getNombre() +
                            " ID: " + estudiante.getId());
                }

                System.out.println("Por favor ingrese el número correspondiente al estudiante que desea seleccionar: ");
                int opcion2 = scanner.nextInt();
                scanner.nextLine();
                if (opcion2 <= totalEstudiantes.size() && opcion2 >= 1) {
                    Estudiante seleccionado = totalEstudiantes.get(opcion2 - 1);
                    System.out.println("Estudiante seleccionado, nombre: " + seleccionado.getNombre() + " ID: "
                            + seleccionado.getId());
                    matricularMateriaParte2(seleccionado);
                    salir = true;
                } else {
                    System.out.println("Opción invalida");
                    invalido = true;
                }
            } else if (opcion == 2) {
                System.out.println("Por favor ingrese el nombre del estudiante: ");
                String nombre = scanner.nextLine();
                System.out.println("Por favor ingrese el ID del estudiante");
                long id = 0;
                String idPrueba = scanner.nextLine();

                try {
                    int index = Estudiante.buscarEstudiante(nombre, id);
                    id = Long.parseLong(idPrueba);

                    if (index == -1) {
                        System.out.println("Estudiante no encontrado");
                        invalido = true;
                    } else {
                        Estudiante seleccionado = Estudiante.getEstudiantes().get(index);
                        if (seleccionado.isMatriculaPagada() == false) {
                            System.out.println("La matricula del estudiante no está pagada");
                            invalido = true;
                        } else if (seleccionado.getCreditos() >= Coordinador.getLimitesCreditos()) {
                            System.out.println("El estudiante no puede matricular más materias");
                            invalido = true;
                        } else {
                            System.out.println("Estudiante seleccionado, nombre: " + seleccionado.getNombre() +
                                    " ID: " + seleccionado.getId());
                            matricularMateriaParte2(seleccionado);
                            salir = true;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opción invalida");
                    invalido = true;
                }
            } else {
                System.out.println("Pción invalida");
                invalido = true;
            }
            if (invalido) {
                System.out.println("Desea intentarlo otra vez o desea salir?");
                System.out.println("Ingrese la opción deseada: \n1- Intentarlo otra vez\n2- Salir");
                int opcion3 = scanner.nextInt();
                scanner.nextLine();
                if (opcion3 != 1) {
                    salir = true;
                }
            }
        }

    }

    public static void matricularMateriaParte2(Estudiante estudiante) {
        // Este metodo se encarga de seleccionar la materia a matricular
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        while (salir == false) {
            Boolean invalido = false;
            System.out.println(
                    "Como desea buscar la materia?\n1- Mediante una lista de las materias disponibles\n2- Mediante una busqueda manual");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            ArrayList<Materia> materiasTotales = new ArrayList<Materia>(Materia.getMateriasTotales());
            // Recibe la opcion del usuario y muestra las materias disponibles
            if (opcion == 1) {

                ArrayList<Materia> materiasDisponibles = new ArrayList<Materia>();
                System.out.println("Lista de materias disponibles para matricular: ");
                int limitesCreditos = Coordinador.getLimitesCreditos();
                for (Materia materia : materiasTotales) {
                    if (Materia.comprobarPrerrequisitos(estudiante, materia) == false) {
                        continue;
                    }
                    if (materia.getCupos() <= 0) {
                        continue;
                    } else if (estudiante.getCreditos() + materia.getCreditos() > limitesCreditos) {
                        continue;
                    }
                    if (estudiante.getMaterias().contains(materia)) {
                        continue;
                    }
                    materiasDisponibles.add(materia);
                    System.out.println(materiasDisponibles.size() + " Nombre: " + materia.getNombre() + " Cupos: "
                            + materia.getCupos());
                }
                System.out.println("Por favor ingrese el numero correspondiente a la materia que desea matricular");
                int eleccion = scanner.nextInt();
                scanner.nextLine();

                if (1 <= eleccion && eleccion <= materiasDisponibles.size()) {

                    Materia seleccionada = materiasDisponibles.get(eleccion - 1);
                    System.out.println("Materia seleccionada " + seleccionada.getNombre());
                    matricularMateriaParte3(estudiante, seleccionada);
                    salir = true;

                } else {

                    System.out.println("Opcion invalida");
                    invalido = true;
                }
                // Recibe el nombre y el codigo de la materia y la busca
            } else if (opcion == 2) {

                System.out.println("Por favor ingrese el nombre de la materia deseada: ");
                String nombre = scanner.nextLine();
                System.out.println("Por favor ingrese el codigo de la materia deseada: ");
                String codigoPrueba = scanner.nextLine();
                int codigo;
                try {
                    codigo = Integer.parseInt(codigoPrueba);

                    int index = Materia.buscarMateria(nombre, codigo);

                    if (index == -1) {

                        System.out.println("Materia no encontrada");
                        invalido = true;

                    } else {

                        Materia seleccionada = materiasTotales.get(index);

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
                        } else {
                            System.out.println("Materia seleccionada " + seleccionada.getNombre());
                            matricularMateriaParte3(estudiante, seleccionada);
                            salir = true;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opcion invalida");
                    invalido = true;
                } // Manejo de excepciones en caso de que el codigo no sea un numero entre las
                  // opciones

            } else {

                System.out.println("Opcion invalida");
                invalido = true;
            }

            if (invalido) {
                // En caso de que la opcion sea invalida, se le pregunta al usuario si desea
                // intentarlo otra vez o salir
                System.out.println("Desea intentarlo otra vez o desea salir?");
                System.out.println("Ingrese la opcion deseada: \n1- Intentarlo otra vez\n2- Salir");
                int opcion2 = scanner.nextInt();
                scanner.nextLine();
                if (opcion2 != 1) {
                    salir = true;
                }

            }
        }

    }

    public static void matricularMateriaParte3(Estudiante estudiante, Materia materia) {
        // Esta parte se encarga de seleccionar el grupo de la materia seleccionada
        // Ademas termina la funcionalidad de MatricularMateria
        Scanner scanner = new Scanner(System.in);
        Boolean salir = false;

        while (salir == false) {
            // Muestra los grupos disponibles para matricular
            System.out.println("Grupos disponibles para matricular: ");
            ArrayList<Grupo> gruposDisponibles = new ArrayList<Grupo>();
            boolean mostro = false;
            for (Grupo grupo : materia.getGrupos()) {
                if (!estudiante.getHorario().comprobarDisponibilidad(grupo.getHorario())) {
                    continue;
                }
                if (grupo.getCupos() != 0) {
                    gruposDisponibles.add(grupo);
                    mostro = true;
                    System.out.println((gruposDisponibles.size()) + " Grupo #" + grupo.getNumero() + " cupos: "
                            + grupo.getCupos() + " Profesor: " + grupo.getProfesor().getNombre());
                }
            }
            if (mostro == false) { // En caso de que no haya grupos disponibles
                System.out.println("La materia no cuenta con grupos disponibles para el estudiante");
                salir = true;
                break;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine();
            if (opcion > 0 && opcion <= gruposDisponibles.size()) { // En caso de que la opcion sea valida se matricula
                                                                    // al estudiante en el grupo seleccionado

                Grupo grupoSeleccionado = gruposDisponibles.get(opcion - 1);
                ArrayList<Materia> materiasInscritas = new ArrayList<Materia>(estudiante.getMaterias());
                ArrayList<Grupo> gruposInscritos = new ArrayList<Grupo>(estudiante.getGrupos());
                gruposInscritos.add(grupoSeleccionado);
                materiasInscritas.add(materia);
                grupoSeleccionado.agregarEstudiante(estudiante);
                grupoSeleccionado.getMateria().cantidadCupos();
                estudiante.setCreditos(estudiante.getCreditos() + materia.getCreditos());
                estudiante.setMaterias(materiasInscritas);
                estudiante.setGrupos(gruposInscritos);
                String imprimir = "Materia " + materia.getNombre() + " - grupo #" + grupoSeleccionado.getNumero();
                System.out.println(imprimir + ". Ha sido matriculado al estudiante: " + estudiante.getNombre());
                salir = true;
                estudiante.getHorario().ocuparHorario(grupoSeleccionado);
                // Se agrega el grupo al horario del estudiante seleccionado y se matricula al
                // estudiante en el grupo
                System.out.println("Desea visualizar el horario del estudiante?: \n1- Si\n2- No");
                int opcion2 = scanner.nextInt();
                scanner.nextLine();

                if (opcion2 == 1) { // En caso de que el usuario desee visualizar el horario del estudiante
                    System.out.println(estudiante.getHorario().mostrarHorario());
                }

            } else { // En caso de que la opcion sea invalida
                System.out.println("Opcion invalida");
                System.out.println("Desea intentarlo otra vez o desea salir?");
                System.out.println("Ingrese la opcion deseada: \n1- Intentarlo otra vez\n2- Salir");
                int opcion3 = scanner.nextInt();
                scanner.nextLine();
                if (opcion3 != 1) { // En caso de que el usuario desee salir
                    salir = true;
                }
            }
        }

    }

    public static void matricularMateriaParte4(Estudiante estudiante, Grupo grupo) {
        ArrayList<Materia> materiasInscritas = new ArrayList<Materia>(estudiante.getMaterias());
        materiasInscritas.add(grupo.getMateria());
        grupo.agregarEstudiante(estudiante);
        grupo.getMateria().setCupos(grupo.getMateria().getCupos() - 1);
        grupo.setCupos(grupo.getCupos() - 1);
        ArrayList<Grupo> gruposInscritos = new ArrayList<Grupo>(estudiante.getGrupos());
        gruposInscritos.add(grupo);
        estudiante.setGrupos(gruposInscritos);
        estudiante.setCreditos(estudiante.getCreditos() + grupo.getMateria().getCreditos());
        estudiante.setMaterias(materiasInscritas);

        // matricularle al estudiante un grupo especifico

    }

    public static long generarId() {

        // un numero random entre 100000 y 999999
        int min = 10000;
        int max = 99999;
        int id;
        boolean existe = false;
        do {
            id = new Random().nextInt(max - min) + min;
            for (Usuario usuario : Usuario.getUsuariosTotales()) {
                if (usuario.getId() == id) {
                    existe = true;
                    break;
                }
            }
        } while (existe);
        return id;

    }

    public static boolean existenciaUsuario(String nombre) {
        // verificar si el usuario existe

        boolean existe = false;

        for (Usuario usuario : Usuario.getUsuariosTotales()) {
            if (usuario.getNombre() == nombre) {
                existe = true;
                break;
            }
        }

        return existe;

    }

    public static boolean existenciaId(long id) {

        // verificar si el id existe
        boolean exist = false;
        for (Usuario usuario : Usuario.getUsuariosTotales()) {
            if (usuario.getId() == id) {
                exist = true;
                break;
            }
        }
        return exist;

    }

    public static Usuario encontrarUsuario(long id) {
        // encontrar el usuario
        Usuario encontrado = null;
        for (Usuario usuario : Usuario.getUsuariosTotales()) {
            if (usuario.getId() == id) {
                encontrado = usuario;
            }
        }
        return encontrado;

    }

    public static boolean verificarPw(Usuario usuario, String pw) {

        // verificar si la contraseña es correcta
        if (usuario.getPw().equals(pw)) {
            return true;
        } else {
            return false;
        }

    }

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