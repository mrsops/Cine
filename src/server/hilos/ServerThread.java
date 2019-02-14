package server.hilos;

import server.controlador.ControlCine;
import server.modelo.Butaca;
import server.modelo.Sesion;

import javax.xml.soap.SAAJMetaFactory;
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
        boolean compraRealizada=false;
        boolean butSeleccionadas=false;
        boolean conectado = false;
        ArrayList<Butaca> butacas=null;
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

                if(linea.equals("SEL-SES")){ //Si recibe este comando, envia al cliente las sesiones, y se queda a la espera de recibir el nombre de sesion
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

                if (linea.equals("SEL-BUT")){
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
                                Butaca butaca =new Butaca(fila,posButaca);
                                if (sesion.getMapaSesion()[fila][posButaca].verificaButaca() && !butByThisUser(butaca, butacas)){
                                    butacas.add(butaca);
                                    salida.println("BUT-OK");
                                }else{ //Si no esta disponible
                                    i--; //Con esta linea, al no estar disponible la reserva, nos quedamos igual que estabamos en el contador
                                    if (butByThisUser(butaca, butacas)){ //Si esta reservada ya, pero por este usuario
                                            salida.println("RSRVED-BYU");
                                            String liberar = entrada.readLine();
                                            if (liberar.equals("KAI")){
                                                sesion.getMapaSesion()[fila][posButaca].liberarButaca();
                                                liberarButaca(butaca,butacas);
                                                i--; //Al liberar, perdemos una butaca asi que descendemos el contador
                                            }


                                    }else{ //Si esta reservada por otro usuario
                                        salida.println("BUT-NOT-FREE");
                                    }
                                }

                                salida.println("NXT-BUT"); //Indicamos al cliente que puede proceder a enviar la siguiente butaca
                            }else{
                                salida.println("ERR-BUT"); //Indicamos al cliente que ha habido un error con los parametros enviados para la toma de la butaca
                                butacas = null;
                                break;
                            }
                        }
                    }else{
                        salida.println("sesion-error");
                    }
                }
                if (linea.equals("RSV-ENT")){ //Reservar las butacas seleccionadas
                    //Compramos las entradas seleccionadas
                    if(cine.reservarEntradas(sesion,butacas)){
                        salida.println("RSV-OK");
                    }else{
                        salida.println("RSV-NOT-OK");
                    }
                }

                if (linea.equals("CMP-ENT")){ //Compramos las entradas reservadas
                    sleep(1000);
                    cine.comprarEntradas(sesion,butacas);
                    compraRealizada=true;
                    salida.println("CMP-OK");
                }


                if (linea.equals("IMP-TIQUET")){
                    if(!compraRealizada){
                        salida.println("IMP-ERR");
                    }else{
                        String tiquet = cine.generarTiquet(sesion,butacas);
                        salida.println(tiquet);
                        salida.println("END-TRM");
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

        }catch (SocketException | NullPointerException e){
                liberarTodas(butacas, sesion);
                System.out.println("Conexion perdida");
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

    private boolean butByThisUser(Butaca butaca, ArrayList<Butaca> butacas){
        for (Butaca b:butacas
             ) {
            if (b.getNumfila()==butaca.getNumfila() && butaca.getNumButaca() == b.getNumButaca()){
                return true;
            }
        }
        return false;

    }

    private boolean liberarButaca(Butaca b, ArrayList<Butaca> butacas){
        for (Butaca b1:butacas
             ) {
            if (b.getNumButaca()==b1.getNumButaca() && b.getNumfila() == b1.getNumfila()){
                butacas.remove(b);
                return true;
            }
        }
        return false;
    }

    private void liberarTodas(ArrayList<Butaca> butacas, Sesion sesion){
        for (Butaca b:butacas
             ) {
            sesion.getMapaSesion()[b.getNumfila()][b.getNumButaca()].liberarButaca();
        }
    }
}
