package server.hilos;

import server.controlador.ControlCine;
import server.modelo.Butaca;
import server.modelo.Sesion;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private Socket socketClient;
    private ControlCine cine;
    private Sesion sesion;

    public ServerThread(Socket s, ControlCine cine) {
        this.socketClient = s;
        this.cine = cine;
        this.sesion = null;
    }

    public ServerThread() {
    }

    @Override
    public void run() {
        super.run();
        boolean conectado = false;
        ArrayList<Butaca> butacas;
        boolean sesionOk = false;
        Socket socket;
        PrintWriter salida;
        BufferedReader entrada;
        try {
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socketClient.getOutputStream())), true);
            entrada = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
            String linea;
            do{

                //Esperamos a que el cliente envie el primer comando de establecimiento de conexion para sincroniza cliente y servidor
                linea = entrada.readLine();
                if(linea.equals("SYN")) {
                    conectado=true;
                    salida.println("SYN-ACK"); // Respondemos al cliente que se ha establecido bien la conexion

                }

                if(linea.equals("CMP-ENT")){ //Si recibe este comando, envia al cliente las sesiones, y se queda a la espera de recibir el nombre de sesion
                    salida.println(cine.mostrarSesiones()); //Enviamos las sesiones al cliente para que las vea
                    sesion = cine.buscarSesion(entrada.readLine());
                    if (sesion!= null){
                        salida.println("sesion-ok"); //Enviamos al cliente que la sesion esta correcta
                        sesionOk = true;
                    }else{
                        salida.println("sesion-not-ok");// Si no encuentra la sesion lo comunicamos al cliente
                    }
                }
                if (linea.equals("MST-SLA")){ //Si recibe este comando es que el cliente esta pidiendo ver el mapa de butacas
                    if (sesionOk){
                        salida.println(sesion.mostrarMapa());
                    }
                }

                if (linea.equals("RSV-BUT")){
                    if (sesionOk){ //Si la sesion es correcta, procedemos a la reserva de butacas
                        salida.println("RSV-RDY"); //enviamos al cliente que el servidor esta listo para procesar las butacas
                        String nbut = entrada.readLine();

                    }else{
                        salida.println("sesion-error");
                    }
                }
                if (linea.equals("RST")){ // cerramos la conexion
                    entrada.close();
                    salida.close();
                    socketClient.close();
                    break;

                }
                try {
                    sleep(20);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }while(true);


        }catch (IOException e){
            System.out.println(e);
        }catch (NullPointerException e){
            try{
                this.socketClient.close();
                System.out.println("Conexion cerrada con "+this.getName());
            }catch (IOException e1){
                System.out.println(e1);
            }
        }
    }

    private boolean isNum(String num){
        try {
            Integer.parseInt(num); //Si no es numero, fallara y entrara en la excepcion, retornando false, si es numero continuara y retornara true
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
