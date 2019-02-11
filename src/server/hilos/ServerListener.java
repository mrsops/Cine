package server.hilos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
    public static void main(String[] args) {

        try {
            ServerSocket socketServidor = new ServerSocket(9000);
            do{
                Socket socketCliente = socketServidor.accept();


                ServerThread servidor = new ServerThread(socketCliente);
                System.out.println("Conexion aceptada con "+servidor.getName());
                servidor.start();
            }while (true);


        }catch (IOException e){
            System.out.println(e);
        }
    }
}
