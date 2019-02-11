package client;

import server.hilos.CompraEntradaPelicula;
import server.modelo.Sesion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientCine {
    private static InetAddress direccion;
    private static final String defaulthost="localhost";
    private static Socket socket;
    private static Scanner tc = new Scanner(System.in);
    private static boolean configurado = false;
    private static final int defaultPort = 9000;

    public static void main(String[] args) {
        Scanner tc = new Scanner(System.in);
        ClientCine cliente = new ClientCine();
        PrintStream salida;
        String opcion, respuesta, textoEnvio;
        boolean conectado = false;
        BufferedReader entradaStrings = new BufferedReader(new InputStreamReader(System.in));

        try {
            Socket socket = null;
            cliente.menu();

            do {
                System.out.println("1. Configuracion Cine");
                System.out.println("2. Comprar entradas");
                System.out.println("0. Salir");
                opcion = tc.nextLine();
            }while(!opcionValidaMenu(opcion));
            switch (opcion){
                case "1":
                    socket = menuConfig();
                    if (socket!=null){
                        configurado=true;
                    }else {
                        configurado=false;
                    }
                    break;
                case "2":
                    if(!configurado){
                        System.out.println("Se usaran los parametros por defecto (ip: localhost, puerto 9000)");
                        socket = new Socket(defaulthost,defaultPort);
                    }

                    BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    salida = new PrintStream(socket.getOutputStream());


                    //Dialogo cliente - servidor
                    System.out.println("¿Desea proceder al proceso de compra de entradas en el cine?(s/n)");
                    String elegido = tc.nextLine();
                    if (elegido.equals("s")){
                        salida.println("SYN");
                        respuesta = entradaStrings.readLine();
                        if (respuesta.equals("SYN-ACK")){
                            conectado=true;
                        }else{
                            System.out.println("Ha habido problemas con el intento de conexion al servidor, por favor revisa la configuracion del servidor remoto");
                        }
                    }else {
                        break;
                    }

                    if (conectado){
                        salida.println("CMP-ENT"); //INDICAMOS AL SERVIDOR QUE NOS DISPONEMOS A COMPRAR ENTRADAS Y LO PROXIMO QUE ENVIAREMOS SERA EL NOMBRE DE LA SESION
                        System.out.println(entrada.readLine()); //DEBERIA LLEGARNOS TODAS LAS SESIONES DISPONIBLES DE LA SEMANA
                        System.out.println("Introduce el nombre de sesion: ");
                        textoEnvio = tc.nextLine();
                        salida.println(textoEnvio);//enviamos al sevidor el nombre de la sesion, y nos quedamos a la espera de que este correcto o no
                        respuesta = entrada.readLine();
                        if (respuesta.equals("sesion-ok")){
                            System.out.println("Sesion correcta");
                            System.out.println("Desea mostrar el mapa de butacas?(s/n)");
                            textoEnvio = tc.nextLine();
                            if (textoEnvio.equals("s")){
                                salida.println("MST-SLA");
                                System.out.println(entrada.readLine()); //Mostramos el mapa de butacas
                            }

                            salida.println("RSV-BUT"); //Indicamos al servidor que queremos reservar entradas
                            respuesta = entrada.readLine();
                            if (respuesta.equals("RSV-RDY")){ //El servidor esta listo para procesar las butacas
                                System.out.print("Cuantas butacas desea reservar?: ");
                                String nbutacas = tc.nextLine();
                                salida.println(nbutacas); //Enviamos al servidor el num de butacas que queremos
                            }


                        }else if(respuesta.equals("sesion-not-ok")){
                            System.out.println("Sesion incorrecta");
                        }else{
                            System.out.println("Ha habido problemas al procesar esta peticion");
                        }


                    }















                    break;
                case "0":

                    break;

                default:
                    break;

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private static void menu(){
        System.out.println("1. Configuracion Cine");
        System.out.println("2. Comprar entradas");
        System.out.println("0. Salir");
    }

    private static boolean opcionValidaMenu(String opcion){
        if(opcion.equals("1") ||opcion.equals("0") ){
            return true;
        }
        return false;
    }

    private static Socket menuConfig() throws IOException{
        String direccionIP;
        Socket socket;
        InetAddress ip;
        System.out.print("Introduce la direccion ip del servidor de cine:");
        direccionIP = tc.nextLine();
        if (esIp(direccionIP)){


            System.out.print("Introduce el puerto: ");
            int puerto = tc.nextInt();
            ip = convierteAIp(direccionIP);
            socket = new Socket(ip,puerto);
            if (socket.isConnected()) {
                return socket;
            }else {
                return null;
            }
        }else{
            System.out.println("No se ha introducido una ip valida");
        }
        return null;


    }



    private static boolean esIp(String ip){
        String[] ipFrac = ip.split(".");
        if (ipFrac.length !=4){
            return false;
        }
        for (int i = 0; i<ipFrac.length;i++){
            if (!isNum(ipFrac[i])){
                return false;
            }
        }

        for (int i = 0; i <ipFrac.length ; i++) {
            int n=Integer.parseInt(ipFrac[i]);
            if (!rangoOk(n)){
                return false;
            }
        }
        return true;
    }

    private static InetAddress convierteAIp(String ipSting) throws UnknownHostException{
        try {
            return InetAddress.getByName(ipSting);
        }catch (UnknownHostException e){
            throw e;
        }



    }

    private static boolean isNum(String num){
        Integer.parseInt(num);
        try {
            Integer.parseInt(num);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    private static boolean rangoOk(int num){
        if (num<0 || num>255){
            return false;
        }
        return true;
    }

}
