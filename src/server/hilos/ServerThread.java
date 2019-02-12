package server.hilos;

import server.controlador.ControlCine;
import server.modelo.Butaca;
import server.modelo.Sesion;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerThread extends Thread{
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
                    System.out.println("Peticion de conexion");
                    sleep(2000);
                    salida.println("SYN-ACK"); // Respondemos al cliente que se ha establecido bien la conexion

                }

                if(linea.equals("CMP-ENT")){ //Si recibe este comando, envia al cliente las sesiones, y se queda a la espera de recibir el nombre de sesion
                    salida.println(cine.mostrarSesiones()); //Enviamos las sesiones al cliente para que las vea
                    salida.println("END-TRM");
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
                        salida.println(sesion.mostrarMapa()); //Enviamos un paquete de muchas lineas
                        salida.println("END-TRM"); //Indicamos al cliente que aqui termina el envio de lineas
                    }
                }

                if (linea.equals("RSV-BUT")){
                    if (sesionOk){ //Si la sesion es correcta, procedemos a la reserva de butacas
                        salida.println("RSV-RDY"); //enviamos al cliente que el servidor esta listo para procesar las butacas
                        String nbut = entrada.readLine(); // aqui almacenaremos la cantidad de butacas que se van a reservar
                        int numButacas=0;
                        if (isNum(nbut)){
                            numButacas= Integer.parseInt(nbut);
                        }
                        butacas=new ArrayList<>();
                        int fila, posButaca;
                        salida.println("NXT-BUT"); //Indicamos al cliente que proceda a enviar las butacas, la siguiente en la lista, en este punto sera la primera de ellas
                        for (int i = 0; i < numButacas; i++) {
                            String sFila = entrada.readLine();
                            String sPosButaca = entrada.readLine();
                            if (isNum(sFila) && isNum(sPosButaca)){
                                fila = Integer.parseInt(sFila);
                                posButaca = Integer.parseInt(sPosButaca);
                                butacas.add(new Butaca(fila, posButaca));
                                salida.println("NXT-BUT"); //Indicamos al cliente que puede proceder a enviar la siguiente butaca
                            }else{
                                salida.println("ERR-BUT"); //Indicamos al cliente que ha habido un error con los parametros enviados para la toma de la butaca
                                butacas = null;
                                break;
                            }
                        }
                        if (butacas!=null){
                            cine.reservarEntradas(sesion, butacas);
                        }
                        salida.println(sesion.mostrarMapa());
                        salida.println("END-TRM");

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


        }catch (SocketException e){
            try{
                this.socketClient.close();
                System.out.println("Conexion perdida");
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
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
