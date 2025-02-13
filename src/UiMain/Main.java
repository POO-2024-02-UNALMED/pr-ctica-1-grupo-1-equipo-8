/*
 Autores:
 -Lina Marcela Sánchez Morales
 -Stiven Santiago Rosero Quemag
 -Tomas Velásquez Eusse
 -Sergio Mario Morales Martínez
 -Jhoan Alexis Rúa García

 Módulo principal de la aplicación, aquí está toda la lógica que permite al usuario manipular
 el sistema académico siguiendo los pasos correctamente.
 */
package UiMain;

import baseDatos.Deserializador;
import baseDatos.Serializador;
import gestorAplicacion.administracion.Beca;
import gestorAplicacion.administracion.Grupo;
import gestorAplicacion.administracion.Materia;
import gestorAplicacion.administracion.Salon;
import gestorAplicacion.usuario.Coordinador;
import gestorAplicacion.usuario.Estudiante;
import gestorAplicacion.usuario.Profesor;
import gestorAplicacion.usuario.Usuario;
import java.util.ArrayList;
import java.util.Scanner; //Clase para capturar la entrada del usuario.

//La clase que contiene el método main, para la ejecución de la aplicación, están todas las opciones
//para llevar a cabo las funciones del programa.
public class Main implements Interfaz{

    //Método main
    public static void main(String[] args){
        Deserializador.deserializarListas(); //Se llama este método de clase de la clase Deserializador para convertir las secuencias de bytes en los archivos de texto a objetos.
        Scanner scanner = new Scanner(System.in);
        Boolean continuar=true;
        Boolean logueado=false;
        System.out.println("Bienvenido al Z.I.A"); //Mensaje de Bienvenida.

        Usuario usuario = null;
        while(!logueado){
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("\n Como desea ingresar?: \n1. Crear un nuevo usuario. \n2. Ingresar con un usuario existente."); //Se le pregunta al usuario cómo desea ingresar.
            int opcion_log = scanner.nextInt(); //Se captura su entrada.
            scanner.nextLine();
            if(opcion_log==1){ //Si elige 1, procedemos con la creación de un nuevo usuario.
                String nomb;
        		boolean existe; //Nos ayuda a mantener al usuario en el bucle.
        		boolean salir = false;
        		do {
        			System.out.println("Ingrese su nombre completo:\nSi desea salir introduzca la palabra Salir");
        			nomb = scanner2.nextLine(); //Capturamos su entrada.
        			if (nomb.equals("Salir")) {
        				existe = false; //Para que salga del bucle do-while.
        				salir = true; //Para salir de crear un nuevo usuario.
        			}
        			else if (Interfaz.existenciaUsuario(nomb)) { //Usamos el método estático de la interfaz Interfaz.
        				System.out.println("Ya existe un usuario asociado a este nombre.");
        				existe=true; //Si ya hay un usuario con el nombre, se pide el nombre de nuevo.
        			}
        			else {
        				existe=false; //Si no hay problemas con el nombre, salimos del bucle.
        			}
        		}while (existe);
        		if (salir){  //Aquí salimos de crear un nuevo usuario.
        			continue;
        		}
        		String facul = null;
        		while(true) {
        			System.out.println("Seleccione la facultad a la que pertenece:");
        			System.out.println(Coordinador.mostrarFacultades()); //Método que muestra todas las facultades enumeradas.
        			int fac = scanner2.nextInt();//Capturamos la entrada.
        			scanner2.nextLine();
        			if(fac<=0||fac>Coordinador.getFacultades().length) {  //Nos aseguramos que el usuario haga una entrada correcta.
        				System.out.println("Valor invalido. Intente nuevamente");; //Mensaje de advertencia.
        			}
        			else {
        				facul = Coordinador.getFacultades()[fac-1]; //Tomamos la facultad si el usuario hace una entrada correcta.
        				break; //Salir del bucle.
        			}
        		}
                //Se pide la contraseña del nuevo usuario.
        		System.out.println("Ingrese su contrasena:\nSi desea salir introduzca la palabra Salir");
        		String cont = scanner2.nextLine();//Capturamos la entrada.
        		if(cont.equals("Salir")) {
        			continue; //Salimos si así lo pide.
        		}
        		long id = Interfaz.generarId(); //Se genera el id para el nuevo usuario.
        		usuario = new Coordinador(facul,id,nomb,cont); //Se crea el nuevo Coordinador, con facultad, id, nombre y contraseña.
        		System.out.println("Se ha creado un nuevo usuario a nombre de "+nomb+" con el id "+id+" asignado.\nRecuerde que este id sera con el que inicie sesion en este usuario de ahora en adelante");//Mnesaje de confirmación
        		logueado=true;
            }
            else if(opcion_log==2){ //Opción para entrar con un usuario existente.
                Scanner scanner3 = new Scanner(System.in);
        		boolean intentando = true;
        		while(intentando) {
        			System.out.println("Ingrese su id de usuario:\nSi desea salir escriba el numero 0.");
        			long id = scanner3.nextLong(); //Se captura el id.
                    scanner3.nextLine();
        			if (id==0) {
        				break;//Salir.
        			}
        			else if (id<10000||id>99999) {//ids de 4 cifras o 6 cifras no permitidos.
        				System.out.println("Id invalido. Ingrese un id de 5 cifras.");
        			}
        			else if (!Interfaz.existenciaId(id)){ //Si el id no lo tiene ningún usuario ya creado, lo hacemos saber.
        				System.out.println("El id ingresado no corresponde a ningun usuario registrado en el sistema.");
        			}
        			else if (Interfaz.encontrarUsuario(id).getTipo()=="Estudiante") { //Si el usuario ingresado es de un estudiante, informamos que solo ingresan coordinadores.
        				System.out.println("Error. Solo pueden ingresar coordinadores en la plataforma.");
        			}
        			else {
        				Coordinador coordinadorE = (Coordinador) Interfaz.encontrarUsuario(id);//Ubicamos al coordinador.
        				boolean pwCorect = false; //Para verificar que el password sea correcto.
        				while(!pwCorect){
        					System.out.println("Ingrese la contrasena:");
        					String cont = scanner3.nextLine(); //Capturamos contraseña.
        					if(!Interfaz.verificarPw(coordinadorE,cont)) { //Se verifica la constraseña ingresada para este coordinador.
        						while(true) {//Si es falsa entramos al bucle.
        							System.out.println("La contrasena es incorrecta.\nDesea intentar nuevamente?\n1. Si.\n2. No.");
        							int opCf = scanner3.nextInt();
                                    scanner3.nextLine();
        							if (opCf==1) {	
        								break; //Para volver a ingresar la contraseña.
        							}
        							else if (opCf==2) {
        								pwCorect=true; //Salir y no intentar la contraseña otra vez.
        								intentando = false;
        								break;
        								
        							}
        							else {//Entrada invalida.
        								System.out.println("Valor invalido. Ingrese el numero de una de las opciones mencionadas.");
        							}
        						}
        					}
        					else { //Si la contraseña es correcta damos el mensaje de bienvenida.
        						System.out.println("\nHola "+coordinadorE.getNombre()+" has ingresado exitosamente al sistema :D");
        						usuario = coordinadorE; //Asignamos a la variable usuario el coordinador que ingresa.
        						intentando=false;
        						logueado = true;				
        						break;//Salimos de los tres bucles.
        					}
        				}
        			}
        		}
            } //Opción no valida.
            else {
                System.out.println("Opcion invalida");   
            }
        }

        while(continuar){ //ya con usuario, presentamos las funcionalidades del sistema.
            System.out.println("\nA continuacion encontrara los diferentes servicios ofrecidos por la plataforma.");
            System.out.println("Ingrese la opcion deseada: \n1. Matricular Materia.\n2. Generar Horario.\n3. Eliminar o agregar Materia / Grupo.\n4. Desmatricular Alumno. \n5. Busqueda y Postulacion de Becas. \n6. Salir y Guardar");
            int opcion = scanner.nextInt(); //Recibimos la elección del usuario.
            scanner.nextLine();
            switch(opcion) { //En función de lo que quiera el usuario, ejecutamos cierto código
            case 1:
                System.out.println("Has seleccionado la opcion 1 (Matricular materia)");
                Interfaz.matricularMateria(); //Método estático de la interfaz Interfaz.
                break; //Salir del switch.
            case 2:
                System.out.println("\nHas seleccionado la opcion 2 (Generar Horario).");
                System.out.println("Esta Opcion te permitira generar una horario según unas materias dadas.");
                
                boolean salir = true;
                while(salir){

                    //Elegir el modo de seleccionar las materias para generar el horario.
                    System.out.println("Elija como quiere seleccionar las materias: \n1.Ver la lista de materias. \n2.Buscar por criterio (Facultad - Creditos - Codigo). \n3.Salir");
                    int opt=scanner.nextInt();
                    scanner.nextLine();
                    

                    // Ver la lista de materias.
                    if (opt==1){
                        Interfaz.fusionImpresiones(Materia.getMateriasTotales()); //Método estático de la interfaz Interfaz.
                    }
    
                    // Ver lista pero con filtro
                    else if (opt ==2){
                        System.out.println("Por cual criterio quiere buscar: \n1. Facultad. \n2. Creditos. \n3. Codigo");
                        int opt2=scanner.nextInt();//Capturar criterio.
                        scanner.nextLine();
                        
                        if (opt2==1){
                            System.out.println("Ingrese la facultad: ");
                            String opt3=scanner.nextLine();//tomar facultad.
    
                            Interfaz.fusionImpresiones(Interfaz.mostrarMateriasConFiltro(opt2, opt3));//Se invoca el método solo con las materias que cumplen el filtro.

                            
                        }
                        else if (opt2 == 2){
                            System.out.println("Ingrese el numero de creditos: ");
                            String opt3=scanner.nextLine(); //Capturamos
    
                            Interfaz.fusionImpresiones(Interfaz.mostrarMateriasConFiltro(opt2, opt3));//Se invoca el método solo con las materias que cumplen el filtro.

                        }
                        else if (opt2 == 3){
                            System.out.println("Ingrese el codigo o la parte del codigo a filtrar: ");
                            String opt3=scanner.nextLine();//Capturamos
    
                            Interfaz.fusionImpresiones(Interfaz.mostrarMateriasConFiltro(opt2, opt3));//Se invoca el método solo con las materias que cumplen el filtro.

                        }
                    
                    // Salir
                    } else{
                        salir = false;
                    }
                }
                break;//Salir del switch.
            case 3:
                System.out.println("Has seleccionado la opcion 3 (Eliminar o agregar Materia / Grupo).");
                System.out.println("Ingrese la opcion que se ajuste a su busqueda:\n1.Agregar Materia.       2.Eliminar Materia.\n3.Agregar Grupo.         4.Eliminar Grupo.\n5.Salir.");
                int opcion_3 = scanner.nextInt();
                scanner.nextLine();
                if(opcion_3==5){
                	continue;
                }
                if(opcion_3 == 1){
                    System.out.println("Has seleccionado la opcion 1 (Agregar materia.)");
                    if(opcion_3 == 1){
                        boolean fin = false;
                        
                        while(!fin){
                            Scanner scanner3_1 = new Scanner(System.in);
                            System.out.println("Ingresa el nombre de la materia que desea agregar.");
                            String nombre = scanner3_1.nextLine();
                            for (Materia materia : Materia.getMateriasTotales()){
                                if (materia.getNombre().equals(nombre) == true){
                                    System.out.println("La materia que intenta crear, ya se existe actualmente.");
                                    fin = true;
                                }
                            }                      
                            System.out.println("Ingresa el codigo de la materia que desea agregar.");
                            int codigo = scanner3_1.nextInt();
                            scanner3_1.nextLine();
                            for (Materia materia : Materia.getMateriasTotales()){
                                if (materia.getCodigo() == codigo){
                                    System.out.println("El codigo que intenta asignarle a la materia, ya le corresponde a una existente.");
                                    fin = true;
                                }
                            }
                            System.out.println("Ingrese una breve descripcion de la materia.");
                            String descrip = scanner3_1.nextLine();
                            System.out.println("Ingresa los creditos que le asigna a la materia.");
                            int creditos = scanner3_1.nextInt();
                            scanner3_1.nextLine();
                            System.out.println("Ingrese la facultad a la que pertenece la materia");
                            String facu = scanner3_1.nextLine();
                            
                            System.out.println("Ingrese como desea crear la materia:");
                            System.out.println("1.Con prerrequisitos.       2.Sin prerrequisitos.");
                            int decision = scanner3_1.nextInt();
                            scanner3_1.nextLine();
                            if(decision == 1){                              
                                System.out.println("Ha escogido crear la materia con prerrequisitos.");
                                System.out.println("Ingrese los prerrequisitos que tiene la materia para poder ser inscritas por el estudiante (separadas por comas con su respectivo espacio). Si desea salir, ingrese la palabra 'Salir'.");
                                String[] pReq;
                                ArrayList<String> ingresos = new ArrayList<String>();
                                ArrayList<Materia> pRequisitos = new ArrayList<Materia>();
                                boolean seguir = true;
                                boolean saliendo = true;
                                while(true){
                                    String prerreq = scanner3_1.nextLine();
                                    pReq = prerreq.split(", ");
                            
                                for(String i : pReq){
                                    ingresos.add(i);
                                }
                                for(String r: pReq){
                                    for(Materia materia:Materia.getMateriasTotales()){
                                        if (r.equals(materia.getNombre()) == true){
                                            pRequisitos.add(materia);
                                            
                                        }
                                        
                                    }
                                }

                                if(ingresos.contains("Salir") || ingresos.contains("salir")){
                                    System.out.println("Ha decidido salir, sera redireccionado al menu principal.");
                                    saliendo = false;
                                    break;
                                }
                                else if(pRequisitos.size() == 0){
                                    System.out.println("Ha escogido crear la materia con prerrequisitos pero no ingreso ninguna materia valida, intentelo nuevamente.");
                                    continue;
                                }
                                    System.out.println("Las prerrequisitos con los que quedará la materia son:");
                                    int j = 1;
                                    for(Materia prerrequisito : pRequisitos){
                                        System.out.println(j+". "+ prerrequisito.getNombre()+".");
                                        j += 1;
                                    }
                                    System.out.println("1.Continuar.        2. Modificar");
    
                                    int pre = scanner3_1.nextInt();
                                    scanner3_1.nextLine();
                                    if(pre == 1){
                                        break; 
                                    }
                                    else if(pre == 2){
                                        System.out.println("Ingrese los prerrequisitos que desea agregar. Si desea salir, ingrese 'Salir'");
                                        continue;                                        
                                    }
                                    else if(0 > pre || pre > 2){
                                        System.out.println("Opcion invalida. Sera redireccionado a crear la materia nuevamente");
                                        saliendo = false;
                                        break;
                                    }
                                }
                                
                                if(!seguir){
                                    continue;
                                }

                                if(!saliendo){
                                    break;
                                }

                                if(seguir){
                                    usuario.agregarMateria(nombre, codigo, descrip, creditos, facu, pRequisitos);
                                    System.out.println("La materia "+ nombre + " ha sido creada con exito.");
                                    break;                                    
                                }

                            }
                            else if(decision == 2){
                                System.out.println("Ha escogido crear la materia sin prerrequisitos.");
                                Materia materiaN = new Materia(nombre, codigo, descrip, creditos, facu);
                                System.out.println("La materia "+ nombre + " ha sido creada con exito.");
                                break; 
                            }
                            else if(0 > decision || decision > 2){
                                System.out.println("Opcion invalida.");
                                continue;
                            }
                            break;                               
                            
                        }
                        
                    }
                }
                else if(opcion_3 == 2){
                    System.out.println("Has seleccionado la opcion 2 (Eliminar materia.)");
                    boolean terminar = false;
                    while(!terminar){
                        Scanner scanner3_2 = new Scanner(System.in);
                        System.out.println("Ingresa el nombre de la materia que desea eliminar.");
                        String nomMat = scanner3_2.nextLine();
                        boolean existencia = true;
                        ArrayList<String> nombresMaterias = new ArrayList<String>();
                        for (Materia materia : Materia.getMateriasTotales()){
                            String a = materia.getNombre();
                            nombresMaterias.add(a);
                        }
                        if ((nombresMaterias.contains(nomMat)) == false){
                            System.out.println("La materia que desea eliminar no existe en la base de datos o no la ha ingresado correctamente, intentelo nuevamente.");
                            existencia = false;
                        }
                        if(existencia){
                            for (Materia materia : Materia.getMateriasTotales()){
                                if (materia.getNombre().equals(nomMat) == true){
                                    usuario.eliminarMateria(materia);
                                    break;
                                }
                            }
                            System.out.println("La materia "+ nomMat + " ha sido eliminada con exito.");
                            break;                     
                        }
                        else{
                            continue;
                        }
                    }                    
                }
                else if(opcion_3 == 3){
                    System.out.println("Has seleccionado la opcion 3 (Agregar grupo.)");
                    boolean salida = false;
                    while(!salida) {
                    	Scanner scanner3 = new Scanner(System.in);
	                    System.out.println("Seleccione la materia a la cual desea agregar el grupo:");
	                    System.out.print(Materia.mostrarMaterias());
	                    System.out.println((Materia.getMateriasTotales().size()+1)+". Salir");
	                    
	                    int materiaN = scanner3.nextInt();
	                    scanner3.nextLine();
	                    Materia materiaSel = null;
	                    if (materiaN==Materia.getMateriasTotales().size()+1) {
	                    	salida=true;
	                    	break;
	                    }
	                    else if(materiaN<=0 || materiaN>Materia.getMateriasTotales().size()+1) {
	                    	System.out.println("Valor invalido. Intente nuevamente.");
	                    	continue;
	                    }
	                    else {
	                    	materiaSel = Materia.getMateriasTotales().get(materiaN-1);
	                    }
	                    
	                    Profesor profesorSel = null;
		                while(true) {
		                	Scanner scanner4 = new Scanner(System.in);
		                    System.out.println("Ingrese el metodo por el cual quiere asignar el profesor para el nuevo grupo:\n1. Seleccionar del listado de profesores.\n2. Asignar un profesor nuevo.\n3. Salir");
		                    int opcion_profe = scanner4.nextInt();
                            scanner4.nextLine();
		                    
		                    if (opcion_profe==1) {
		                    	if(Profesor.profesoresDeMateria(materiaSel.getNombre()).size()==0) {
		                    		System.out.println("No existen profesores registrados capacitados para dictar la materia");
		                    	}
		                    	else {
		                    		System.out.println("Seleccione uno de los siguientes profesores");
		                    		System.out.print(Profesor.mostrarProfesMateria(materiaSel.getNombre()));
		                    		int seleccionProfe = scanner4.nextInt();
		                    		scanner4.nextLine();
		                    		ArrayList<Profesor> preProfes = Profesor.profesoresDeMateria(materiaSel.getNombre());
		                    		profesorSel = preProfes.get(seleccionProfe-1);
		                    		break;
		                    	}
		                    }
		                    
		                    else if(opcion_profe==2){
		                    	System.out.println("Ingrese el nombre completo del profesor: ");
		                    	String nomb = scanner4.nextLine();
		                    	String facu = materiaSel.getFacultad();
		                    	boolean maters = false;
		                    	ArrayList<Materia> mates = null;
		                    	while(!maters) {
			                    	System.out.println("Ingrese los numeros correspondientes a las materias para las cuales el profesor esta capacitado:\nCuando termine ingrese el numero 0.");
			                    	System.out.println(Materia.mostrarMaterias());
			                    	mates = new ArrayList<Materia>();
			                    	while(true) {
			                    		int mat = scanner4.nextInt();
			                    		scanner4.nextLine();
			                    		if(mat==0) {
			                    			break;
			                    		}
			                    		else if(mat<0 || mat>Materia.getMateriasTotales().size()){
			                    			System.out.println("Ha seleccionado un valor invalido. Ingrese uno nuevo o finalice la seleccion de materias");
			                    		}
			                    		else {
			                    			Materia mate = Materia.getMateriasTotales().get(mat-1);
			                    			mates.add(mate);
			                    		}
			                    	}

			                    	if(mates.size()==0) {
			                    		System.out.println("Debe seleccionar al menos una materia. Intente nuevamente");
			                    	}
			                    	else if (!mates.contains(Materia.encontrarMateria(materiaSel.getNombre()))){
			                    		System.out.println("Debe incluir la materia a la que corresponde este nuevo grupo. Intente nuevamente.");
			                    	}
			                    	else {
			                    		maters = true;
			                    	}
		                    	}
		                    	profesorSel = new Profesor(nomb,facu,mates);
		                    	break;
		                    }
		                    else if(opcion_profe==3){
		                    	salida = true;
		                    	break;
		                    }
		                    else {
		                    	System.out.println("Valor erroneo. Ingrese un valor valido.");
		                    }
	                	}
		                if(salida) {break;}
		                int sesiones = 0;
		                while(true) {
		                	Scanner scanSes = new Scanner(System.in);
		                	System.out.println("Ingrese la cantidad de sesiones de clase que tendra el grupo a la semana:");
		                	sesiones = scanSes.nextInt();
		                	scanSes.nextLine();
		                	if(sesiones<=0||sesiones>6) {
		                		System.out.println("El numero de sesiones de clase debe ser entre 1 y 6. Ingrese un nuevo valor");
		                	}
		                	else {
		                		break;
		                	}
		                }
		                System.out.println("A continuacion debe repetir el proceso de seleccion de horario una vez por cada sesion de clase.");
		                ArrayList<String> horarioSel = new ArrayList<String>();
		                for(int i=0; i<sesiones; i++) {
		                	Scanner scanHor = new Scanner(System.in);
		                	int dia = 0;
		                	while(true) {
		                		System.out.println("Seleccione el dia se la semana al que corresponde la sesion de clase.\n1. Lunes.\n2. Martes.\n3. Miercoles\n4. Jueves\n5. Viernes.\n6. Sabado.\n7. Domingo.\n8. Salir");
		                		dia = scanHor.nextInt();
		                		scanHor.nextLine();
		                		if(dia==8) {
		                			salida = true;
		                			break;
		                		}
		                		else if(dia<=0||dia>8) {
		                			System.out.println("Valor invalido. Seleccione un dia valido.");
		                		}
		                		else {
		                			break;
		                		}
		                	}
		                	if(salida) {
		                		break;
		                	}
		                	int hi = 0;
		                	while(true) {
		                		System.out.println("Seleccione la hora de inicio de la clase:\n1. 6 am.\n2. 8 am\n3. 10 am\n4. 12 pm\n5. 2 pm\n6. 4 pm\n7. 6 pm\n8. Salir.");
		                		hi = scanHor.nextInt();
		                		scanHor.nextLine();
		                		if(hi==8) {
		                			salida = true;
		                			break;
		                		}
		                		else if(hi<=0||hi>8) {
		                			System.out.println("Valor invalido. Seleccione una hora valida");
		                		}
		                		else {
		                			hi = hi+4+hi;
		                			break;
		                		}
		                	}
		                	if(salida) {
		                		break;
		                	}
		                	int hf = 0;
		                	while(true) {
		                		System.out.println("Seleccione la hora de finalizacion de la clase:\n1. 8am\n2. 10am\n3. 12 pm\n4. 2 pm\n5. 4 pm\n6. 6pm\n7. 8pm\n8. Salir.");
		                		hf = scanHor.nextInt();
		                		scanHor.nextLine();
		                		if(hf==8) {
		                			salida = true;
		                			break;
		                		}
		                		else if(hf<=0||hf>8) {
		                			System.out.println("Valor invalido. Seleccione una hora valida");
		                		}
		                		else {
		                			hf = hf+6+hf;
		                			if(hf<=hi) {
		                				System.out.println("La hora de finalizacion debe ser mayor a la hora de inicio. Seleccione otra hora.");
		                			}
		                			else {
		                				break;
		                			}
		                		}
		                	}
		                	String hor = "";
		                	if(hi<10) {
		                		hor += dia+"-0"+hi;
		                		if(hf<10) {
		                			hor += "-0"+hf;
		                		}
		                		else {
		                			hor +="-"+hf;
		                		}
		                	}
		                	else {
		                		hor += dia+"-"+hi+"-"+hf;
		                	}
		                	if(horarioSel.contains(hor)) {
		                		System.out.println("Ya se agrego una sesion de clase igual. Ingrese una nueva.");
		                		sesiones++;
		                	}
		                	else {
		                		horarioSel.add(hor);
		                		System.out.println("Se ha agregado el horario "+hor);
		                	}

		                }
		                //scanHor.close();
		                if(salida) {break;}
			            System.out.println("Ingrese la cantidad de cupos con la que contara el grupo:");
			            int cuposSel = scanner.nextInt();
                        scanner.nextLine();
			            Salon salonSel = null;
			            while(true) {
			            	System.out.println("Seleccione el salon donde se daran las sesiones de clase del grupo:");
			            	System.out.print(Salon.mostrarSalones());
			            	System.out.println((Salon.salones.size()+1)+". Salir.");
			            	int numSalon = scanner.nextInt();
			            	scanner.nextLine();
			            	if (numSalon==Salon.salones.size()+1) {
			            		salida = true;
			            		break;
			            	}
			            	else if(numSalon<=0||numSalon>(Salon.salones.size()+1)) {
			            		System.out.println("Valor invalido. Seleccione un salon valido.");
			            	}
			            	else {
			            		salonSel = Salon.getSalones().get(numSalon-1);
			            		break;
			            	}
			            	
			            }
			            if(salida) {break;}
			            int numSel = materiaSel.getGrupos().size();
			            materiaSel.agregarGrupo(numSel+1, profesorSel, horarioSel, cuposSel, salonSel);
			            if (materiaSel.getGrupos().size()==numSel+1) {
			            	System.out.println("El grupo "+(numSel+1)+" de la materia "+materiaSel.getNombre()+" ha sido asignado correctamente");
			            }
			            else {
			            	System.out.println("El grupo no ha sido agregado. El salon y/o el profesor no contaba/n con disponibilidad en el horario asignado.");
			            }
			            break;
                    }
                }
                else if(opcion_3 == 4){
                    System.out.println("Has seleccionado la opcion 4 (Eliminar grupo.)");
                    boolean salida = false;
                    while(!salida) {
                        System.out.println("Seleccione la materia a la cual desea eliminar el grupo:");
	                    System.out.print(Materia.mostrarMaterias());
	                    System.out.println((Materia.getMateriasTotales().size()+1)+". Salir");
	                    
	                    int materiaN = scanner.nextInt();
	                    scanner.nextLine();
	                    Materia materiaSel = null;
	                    if (materiaN==Materia.getMateriasTotales().size()+1) {
	                    	salida=true;
	                    	break;
	                    }
	                    else if(materiaN<=0 || materiaN>Materia.getMateriasTotales().size()+1) {
	                    	System.out.println("Valor invalido. Intente nuevamente.");
	                    	continue;
	                    }
	                    else {
	                    	materiaSel = Materia.getMateriasTotales().get(materiaN-1);
	                    }

                    	while(true) {
                    		System.out.println("Seleccione el numero del grupo que desea eliminar:");
                    		System.out.print(materiaSel.mostrarGrupos());
                    		System.out.println((materiaSel.getGrupos().size()+1)+". Salir.");
                    		int numSel = scanner.nextInt();
                            scanner.nextLine();
                    		if (numSel<=0||numSel>(materiaSel.getGrupos().size()+1)) {
                    			System.out.println("El numero de grupo ingresado no existe. Ingrese el numero de un grupo valido.");
                    		}
                    		else if(numSel==(materiaSel.getGrupos().size()+1)) {
                    			salida = true;
                    			break;
                    		}
                    		else {
                    			materiaSel.eliminarGrupo(numSel);
                    			System.out.println("El grupo "+numSel+" de la materia "+materiaSel.getNombre()+" ha sido eliminado con exito");
                    			break;
                    		}
                    	}
                    }
                }
                else if(0 > opcion_3 || opcion_3 > 4){
                    System.out.println("Opcion invalida.");
                    continue;
                }
                break;
            case 4:
                System.out.println("Has seleccionado la opcion 4 (Desmatricular Alumno).");
                Estudiante estudiante = null;
                Scanner scanner2 = new Scanner(System.in);
                while (true){
                    System.out.println("Elija como quiere seleccionar el alumno: \n1.Ver la lista de estudiantes. \n2.Buscar estudiante por ID (Documento) y nombre. \n3.Salir");
                    int eleccion = scanner.nextInt();
                    scanner.nextLine();
                    if (eleccion == 1){
                        System.out.println("Elija el numero del estudiante");
                        System.out.println(Estudiante.mostrarEstudiantes());
                        int numeroEstudiante = scanner2.nextInt();
                        scanner2.nextLine();
                        estudiante = Estudiante.getEstudiantes().get(numeroEstudiante-1);
                        break;
                    }
                    else if(eleccion == 2){
                        System.out.print("Ingrese el nombre del estudiante: ");
                        String nombre = scanner2.nextLine();
                        System.out.print("Ingrese el ID (Documento) del estudiante: ");
                        long id = scanner2.nextLong();
                        scanner2.nextLine();
                        int numeroEstudiante = Estudiante.buscarEstudiante(nombre, id);
                        if (numeroEstudiante != -1){
                            System.out.println("El estudiante ha sido encontrado\n");
                            estudiante = Estudiante.getEstudiantes().get(numeroEstudiante);
                            break;
                        }
                        else{
                            System.out.println("El estudiante no ha sido encontrado, busque nuevamente\n");
                        }
                    }
                    else if(eleccion == 3){
                        break;
                    }
                    else{
                        System.out.println("Ingresa una opcion valida\n");
                    }
                    
                }

                while(true && estudiante != null){
                    while(true){
                        System.out.println("Seleccione de que quiere desmatricular al estudiante:");
                        System.out.println("1. Desmatricular de una materia \n2. Desmatricular del sistema \n3. Retroceder");
                        int opcion_1 = scanner2.nextInt();
                        scanner2.nextLine();
                        if (opcion_1 == 1){
                            Scanner scanner3 = new Scanner(System.in);
                            if (estudiante.getMaterias().size() == 0){
                                System.out.println("El estudiante no tiene ninguna materia matriculada. Intente otra opcion o retroceda para seleccionar otro estudiante\n");
                            }
                            else{
                            while (true){
                                boolean terminado = false;
                                System.out.println("Elija como quiere seleccionar la materia y el grupo");
                                System.out.println("1. Ver lista de materias y grupos \n2. Buscar materia y grupo");
                                int opcion_2 = scanner3.nextInt();
                                scanner3.nextLine();
                                switch(opcion_2){
                                    case 1:
                                    System.out.println(estudiante.mostrarMaterias());
                                    System.out.print("Ingrese el numero de la materia: ");
                                    int numeroMateria = scanner3.nextInt();
                                    scanner3.nextLine();
                                    System.out.print("Ingrese el numero del grupo: ");
                                    int numeroGrupo = scanner3.nextInt();
                                    scanner3.nextLine();
                                    Materia materiaE = estudiante.getMaterias().get(numeroMateria-1);
                                    Grupo grupoE = estudiante.getMaterias().get(numeroMateria-1).getGrupos().get(numeroGrupo-1);
                                    Grupo grupo = Grupo.buscarGrupo(materiaE, grupoE);
                                    System.out.println("Se ha seleccionado la materia " + estudiante.getMaterias().get(numeroMateria-1).getNombre());
                                    if (grupo.existenciaEstudiante(estudiante)){
                                        grupo.eliminarEstudiante(estudiante);
                                        estudiante.getHorario().liberarHorario(grupo.getHorario());
                                        System.out.println("El estudiante ha sido desmatriculado de la materia y el grupo");
                                        terminado = true;
                                        break;
                                    }
                                    else{
                                        System.out.println("El estudiante no esta matriculado en el grupo");
                                    }
                                    break;
                                    case 2:
                                    System.out.print("Ingrese el nombre de la materia: ");
                                    String nombreMateria = scanner3.nextLine();
                                    Materia materia = estudiante.buscarMateriaPorNombre(nombreMateria);
                                    if (materia != null){
                                        Grupo grupoEst = materia.buscarGrupoDeEstudiante(estudiante);
                                        Grupo grupoEstudiante = Grupo.buscarGrupo(materia, grupoEst);
                                        grupoEstudiante.eliminarEstudiante(estudiante);
                                        estudiante.getHorario().liberarHorario(grupoEstudiante.getHorario());
                                        System.out.println("El estudiante ha sido desmatriculado de la materia y el grupo");
                                        terminado = true;
                                        break;
                                    }
                                    else{
                                        System.out.println("El estudiante no tiene matriculada esta materia");
                                    }                                   
                                }
                                if (terminado){
                                    break;
                                }
                            }
                        }
                        }
                        else if(opcion_1 == 2){
                            if (usuario.comprobacionFacultad(estudiante)){
                                estudiante.getHorario().vaciarHorario(estudiante.getGrupos());
                                estudiante.desmatricularMaterias();
                                usuario.desmatricularDelSistema(estudiante);
                                System.out.println("El estudiante ha sido desmatriculado del sistema\n");
                                break;

                            }
                            else{
                                System.out.println("El estudiante es de una facultad diferente a la suya");
                                System.out.println("Puede volver a intentar con otro estudiante o salir\n");
                            }
                        }
                        else if(opcion_1 == 3){
                            break;
                        }
                        else{
                            System.out.println("Ingresa una opcion valida\n");
                        }
                    }
                    break;
                }

                break;
            case 5:
            Coordinador e = (Coordinador) usuario;
            ArrayList<Estudiante> estudiantesBeneficiados = new ArrayList<Estudiante>();
                System.out.println("Has seleccionado la opcion 5 (Busqueda y Postulacion Becas).");
                boolean end = false;
                while(!end){
                    Scanner scanner_5 = new Scanner(System.in); 
                    System.out.println("Ingrese el numero que corresponde a la opcion que mejor se adapta a su busqueda:");
                    System.out.println("1.Ver listado de becas existentes actualmente.       2.Aplicar beca a estudiante.\n3.Crear nueva beca.                                  4.Eliminar beca.");
                    int opcion_5 = scanner_5.nextInt();
                    scanner_5.nextLine();
                    if (opcion_5 == 1){
                        System.out.println("Has seleccionado la opcion 1 (Ver listado de becas existentes actualmente.)");
                        System.out.println("A continuacion podra ver la lista de becas existentes en el momento:");
                        Interfaz.mostrarBecas();
                        continue;
                    }
                    else if(opcion_5 == 2){
                        Scanner scanner5_2 = new Scanner(System.in);
                        System.out.println("Has seleccionado la opcion 2 (Aplicar beca a estudiante.)");
                        System.out.println("Ingrese el nombre del estudiante tal como aparece en la lista que vera a continuacion:");
                        System.out.println(Estudiante.mostrarEstudiantes());
                        String estNombre = scanner5_2.nextLine();
                        
                        ArrayList<String> nomEst = new ArrayList<String>();
                        for(Estudiante estu : Estudiante.getEstudiantes()){
                            String nom = estu.getNombre();
                            nomEst.add(nom);
                        }

                        if(nomEst.contains(estNombre) == false){
                            System.out.println("El nombre ingresado no fue encontrado entre los estudiantes actuales, intentelo otra vez (Recuerde que este debe ir con mayuscula inicial en cada palabra y sin tildes).");
                            continue;
                        }

                        for(Estudiante becado : estudiantesBeneficiados){
                            if(becado.getNombre().equals(estNombre) == true){
                                System.out.println("El estudiante ya ha aplicado exitosamente a una beca, no puede aplicar a otra durante el semestre academico actual.");
                                end = true;
                            }
                        }

                        System.out.println("Ingrese el numero que corresponde a la beca que quiere aplicar el estudiante.");
                        Interfaz.mostrarBecas();
                        int nBeca = scanner5_2.nextInt();
                        scanner5_2.nextLine();
                        Beca tipoBeca = (Beca.getBecas()).get(nBeca-1);

                        
                        for(Estudiante est : Estudiante.getEstudiantes()){
                            if(est.getNombre().equals(estNombre) == true){
                                if(tipoBeca.getCupos() == 0){
                                    System.out.println("La beca "+tipoBeca.getConvenio()+" no cuenta con vacantes disponibles en el momento.");
                                    break;
                                }
                                else if (e.candidatoABeca(est,tipoBeca)){
                                    tipoBeca.setCupos(tipoBeca.getCupos()-1);
                                    System.out.println("El estudiante "+ est.getNombre() +" cumple con los requisitos para aplicar a la beca " +tipoBeca.getConvenio()+".");
                                    estudiantesBeneficiados.add(est);
                                    est.setSueldo(tipoBeca.getAyudaEconomica());
                                    System.out.println("La ayuda economica ha sido cargada al sueldo del estudiante "+est.getNombre()+".");

                                    if (est.pagarMatricula()){
                                        System.out.println("Con la ayuda economica, su matricula ha sido pagada con totalidad.");
                                    }
                                    else if (est.pagarMatricula() == false){
                                        System.out.println("Con la ayuda economica, su matricula aún no ha sido pagada con totalidad.");
                                    }
                                    break;

                                }
                                else if (e.candidatoABeca(est,tipoBeca) == false){
                                    System.out.println("El estudiante no cumple con los requisitos para aplicar a la beca " +tipoBeca.getConvenio()+".");
                                }                                
                            }
                        }
                    }else if(opcion_5 == 3){
                        System.out.println("Has seleccionado la opcion 3 (Crear nueva beca.)");
                        Scanner scanner5_3 = new Scanner(System.in);
                        System.out.println("Ingrese el numero de cupos totales que tendra la nueva beca:");
                        int cuposBeca =  scanner5_3.nextInt();
                        scanner5_3.nextLine();
                        System.out.println("Ingrese el nombre de la beca:");
                        String nombreBeca =  scanner5_3.nextLine();
                        for(Beca beca : Beca.getBecas()){
                            if(beca.getConvenio().equals(nombreBeca)){
                                System.out.println("Ya existe una beca con el nombre que se pretende dar, intentelo nuevamente.");
                                end = true;
                            }
                        }
                        
                        if(end){continue;}
                        System.out.println("Ingrese el promedio requerido que debe tener el estudiante para poder aplicar a la beca:");
                        double promedioBeca =  scanner5_3.nextDouble();
                        scanner5_3.nextLine();
                        System.out.println("Ingrese el numero que representa el porcentaje de avance con el que debe contar el estudiante para poder aplicar a la beca:");
                        double avanceBeca =  scanner5_3.nextDouble();
                        scanner5_3.nextLine();
                        System.out.println("Ingrese el estrato maximo que puede tener el estudiante para poder aplicar a la beca:");
                        int estratoBeca =  scanner5_3.nextInt();
                        scanner5_3.nextLine();
                        System.out.println("Ingrese el numero de creditos inscritos en el semestre que debe tener el estudiante para aplicar beca:");
                        int creditosBeca =  scanner5_3.nextInt();
                        scanner5_3.nextLine();
                        System.out.println("Ingrese la ayuda economica a la que puede acceder el estudiante una vez tenga la beca (Sin puntos ni comas):");
                        int ayudaBeca =  scanner5_3.nextInt();
                        scanner5_3.nextLine();
                        System.out.println("¿La beca necesita de recomendacion por parte de un profesor?");
                        System.out.println("1.Si.          2.No.");
                        boolean recomendacionBeca = true;
                        while(true){
                        int booleano =  scanner5_3.nextInt();
                        scanner5_3.nextLine();
                            if(booleano == 1){
                            recomendacionBeca = true;
                            break;
                        }                        
                        else if(booleano == 2){
                            recomendacionBeca = false;
                            break;
                        }
                        else if(booleano != 1 || booleano != 2){
                            System.out.println("Valor invalido, por favor intente nuevamente.");
                            continue;
                        }
                        }
                        
                        Beca nBeca = new Beca(cuposBeca, nombreBeca, promedioBeca, avanceBeca, estratoBeca, creditosBeca, ayudaBeca, recomendacionBeca);
                        System.out.println("La beca "+ nombreBeca +" ha sido creada con exito.");
                        break;
                    }
                    else if(opcion_5 == 4){
                        System.out.println("Has seleccionado la opcion 4 (Eliminar beca.)");
                        Scanner scanner5_4 = new Scanner(System.in);
                        System.out.println("Ingrese el numero que corresponde a la beca que quiere eliminar:");
                        Interfaz.mostrarBecas();
                        int becaSel = scanner5_4.nextInt();
                        scanner5_4.nextLine();
                        Beca delBeca = (Beca.getBecas()).get(becaSel-1);
                        Beca.eliminarBeca(delBeca);
                        System.out.println("La beca "+ delBeca.getConvenio() +" ha sido eliminada con exito.");
                        break;                     
                    }
                    else if(0 > opcion_5 || opcion_5 > 4){
                        System.out.println("Opcion invalida.");
                        continue;
                    }
                    break;
                    
                }
                break;

            case 6:
                Serializador.serializarListas(); //Guardar todos los objetos antes de que la aplicación cierre.
                System.out.println("Has salido del programa");
                continuar = false;
                break;

            default:
                System.out.println("Opcion invalida");
                System.out.println("Desea continuar?");
                System.out.println("Opcion 1- SI");
                System.out.println("Opcion 2- NO");
                System.out.println("Por favor ingrese la opcion deseada");
                int continuarint=scanner.nextInt();
                scanner.nextLine();
                if (continuarint==1){
                    continue;
                } else{
                continuar=false;
                }
            }
        }
    }
}