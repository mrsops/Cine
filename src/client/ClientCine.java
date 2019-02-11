package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientCine {
    private InetAddress direccion;
    private String defaulthost;
    private Socket socket;
    private Scanner tc = new Scanner(System.in);

    public static void main(String[] args) {
        PrintStream salida;
        BufferedReader entradaStrings = new BufferedReader(new InputStreamReader(System.in));
        try {
            Socket socket = new Socket();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void menu(){
        String opcion;
        do {
            System.out.println("1. Configuracion Cine");
            System.out.println("2. Comprar entradas");
            System.out.println("0. Salir");
            opcion = tc.nextLine();
        }while(!opcionValidaMenu(opcion));
        switch (opcion){
            case "1":

                break;
            case "2":

                break;
            case "0":

                break;

                default:
                    break;

        }

    }

    private boolean opcionValidaMenu(String opcion){
        if(opcion.equals("1") ||opcion.equals("0") ){
            return true;
        }
        return false;
    }

    private Socket menuConfig() throws UnknownHostException {
        String direccionIP;
        System.out.print("Introduce la direccion del servidor cine:");
        direccionIP = tc.nextLine();

        System.out.print("Introduce el puerto: ");
        int puerto = tc.nextInt();



        InetAddress dirIP = InetAddress.getByAddress(miIp);

    }

    private boolean esIP(String ip){
        String[] fraccionado = ip.split(".",4);
        Byte primero = fraccionado[]

    }

}
