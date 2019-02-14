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
        PrintStream salida=null;
        BufferedReader entrada=null;
        boolean seleccionadas = false;
        String opcion, respuesta, textoEnvio;
        boolean conectado = false;
        try {
            Socket socket = null;


            do {
                cliente.menu();
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
                    do {

                        menuEntradas();
                        opcion = tc.nextLine();
                        switch (opcion) {

                            case "1": //Seleccionar las entradas
                                if (!configurado) {
                                    System.out.println("Se usaran los parametros por defecto (ip: localhost, puerto 9000)");
                                    socket = new Socket(defaulthost, defaultPort);
                                }
                                socket.setSoTimeout(20000);
                                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                salida = new PrintStream(socket.getOutputStream());


                                //Dialogo cliente - servidor
                                salida.println("SYN");
                                respuesta = entrada.readLine();
                                if (respuesta.equals("SYN-ACK")) {
                                    conectado = true;
                                } else {
                                    System.out.println("Ha habido problemas con el intento de conexion al servidor, por favor revisa la configuracion del servidor remoto");

                                }


                                if (conectado) {
                                    salida.println("SEL-SES"); //INDICAMOS AL SERVIDOR QUE NOS DISPONEMOS A COMPRAR ENTRADAS Y LO PROXIMO QUE ENVIAREMOS SERA EL NOMBRE DE LA SESION
                                    System.out.println(leerTrama(entrada));
                                    System.out.println("Introduce el nombre de sesion: ");
                                    textoEnvio = tc.nextLine();
                                    salida.println(textoEnvio);//enviamos al sevidor el nombre de la sesion, y nos quedamos a la espera de que este correcto o no
                                    respuesta = entrada.readLine();
                                    if (respuesta.equals("sesion-ok")) {
                                        System.out.println("Sesion correcta");
                                        System.out.println("Desea mostrar el mapa de butacas?(s/n)");
                                        textoEnvio = tc.nextLine();
                                        if (textoEnvio.equals("s")) {
                                            salida.println("MST-SLA");
                                            System.out.println(leerTrama(entrada)); //Mostramos el mapa de butacas
                                        }

                                        salida.println("RSV-BUT"); //Indicamos al servidor que queremos reservar entradas
                                        respuesta = entrada.readLine();
                                        if (respuesta.equals("RSV-RDY")) { //El servidor esta listo para procesar las butacas
                                            System.out.print("Cuantas butacas desea reservar?: ");
                                            String nbutacas = tc.nextLine();
                                            salida.println(nbutacas); //Enviamos al servidor el num de butacas que queremos
                                            if (isNum(nbutacas)) {
                                                int nbut = Integer.parseInt(nbutacas);
                                                if (nbut>0){
                                                    seleccionadas=true;
                                                }
                                                int i = 0;
                                                while (i < nbut) {
                                                    respuesta = entrada.readLine();
                                                    if (respuesta.equals("NXT-BUT")) {
                                                        System.out.print("Introduce la fila de la butaca numero " + (i + 1) + ": ");
                                                        String fila = tc.nextLine();
                                                        System.out.print("Introduce el numero de la butaca numero " + (i + 1) + ": ");
                                                        String numBut = tc.nextLine();
                                                        salida.println(fila);
                                                        salida.println(numBut);
                                                        respuesta= entrada.readLine();
                                                        if (respuesta.equals("BUT-NOT-FREE")){
                                                            System.out.println("La butaca introducida, no esta disponible para seleccionar");
                                                        }else if (respuesta.equals("RSRVED-BYU")) {
                                                            System.out.print("La butaca ya la has seleccionado previamente, ¿Desea liberarla? (s/n)");
                                                            textoEnvio = tc.nextLine();
                                                            if (textoEnvio.equals("s")|| textoEnvio.equals("S")){
                                                                System.out.println("Se pretende que se libere la butaca");
                                                                salida.println("KAI"); //Le decimos al servidor que libere la butaca que ya hemos seleccionado
                                                                System.out.println("Butaca liberada correctamente");
                                                                i--; //Como tenemos una butaca menos, descendemos el contador
                                                            }else {
                                                                salida.println("NOT-KAI");
                                                            }
                                                        }else if(respuesta.equals("BUT-OK")){
                                                            i++; // Si la respuesta es que la butaca es correcta, aumentamos el contador
                                                        }
                                                    } else if (respuesta.equals("ERR-BUT")) {
                                                        System.out.println("Ha habido un error al introducir la butaca");
                                                        conectado = false;
                                                        seleccionadas=false;
                                                        break; //Salimos del bucle
                                                    }
                                                }

                                                entrada.readLine();//Limpiamos el ultimo NXT-BUT
                                                salida.println("MST-SLA");
                                                System.out.println(leerTrama(entrada)); //Mostramos el mapa de butacas

                                            }
                                        }


                                    } else if (respuesta.equals("sesion-not-ok")) {
                                        System.out.println("Sesion incorrecta");
                                    } else {
                                        System.out.println("Ha habido problemas al procesar esta peticion");
                                    }


                                }
                                break;

                            case "2":
                                if (seleccionadas){


                                }else {
                                    System.out.println("No se ha seleccionado ninguna entrada");
                                }
                                break; //Fin de case 2 Comprar las entradas

                            case "0":
                                System.out.println("Cerrando conexion");
                                salida.close();
                                entrada.close();
                                socket.close();
                                break;

                        }

                    }while (!opcion.equals("0")); //
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
        System.out.println("2. Acceder al cine");
        System.out.println("0. Salir");
    }

    private static boolean opcionValidaMenu(String opcion){
        if(opcion.equals("1") ||opcion.equals("2") || opcion.equals("0") ){
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

    private static String leerTrama(BufferedReader in) throws IOException{
        String trama="";
        String respuesta;
        do{
            respuesta = in.readLine();
            if (!respuesta.equals("END-TRM")){
                trama +=respuesta+"\n";
            }
        }while (!respuesta.equals("END-TRM"));
        return trama;
    }

    private static void menuEntradas(){
        System.out.println("1. Seleccionar entradas");
        System.out.println("2. Comprar entradas");
        System.out.println("3. Imprimir tiquet");
        System.out.println("0. Salir");
    }

}
