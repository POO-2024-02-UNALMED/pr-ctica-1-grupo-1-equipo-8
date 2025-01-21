package uiMain;

import java.util.Scanner;

import javax.management.MBeanTrustPermission;

import gestorAplicacion.administracion.*;
import gestorAplicacion.usuario.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import baseDatos.Serializador;
import baseDatos.Deserializador;

public class Main implements Interfaz{
    public static void main(Stirng[] args){
        Deserializador.deserializarListas();
        Scanner sc = new Scanner(System.in);
        Boolean continuar=true;
        Boolean logueado=false;
        System.out.println("Bienvenido a este dolor de cabeza");

        Usuario usuario = null;
        while(!logueado){
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("\n Como desea ingresar?: \n1. Crear un nuevo usuario. \n2. Ingresar con un usuario existente.");
            int opcion_login = scanner.nextInt();
            scanner.nextLine();
            if(opcion_log==1){

            }
            else if(opcion_log==2){

            }
            else {
                System.out.println("Opcion invalida");   
            }
        }

        while(continuar)
    }
}