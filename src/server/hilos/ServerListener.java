package server.hilos;

import server.controlador.ControlCine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread{
    ControlCine cine;
    boolean pausaFlag;

    public ServerListener(ControlCine cine) {
        this.cine = cine;
        pausaFlag=false;
    }

    @Override
    public void run() {
        super.run();
        ServerSocket socketServidor=null;
        int contador=0;
        try {
            socketServidor = new ServerSocket(9000);
            do{
                contador++;
                Socket socketCliente = socketServidor.accept();
                if (pausaFlag){
                    pausePoint();
                }
                ServerThread servidor = new ServerThread(socketCliente, cine,"Cliente "+contador);
                servidor.start();
                sleep(20);
            }while (true);


        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            System.out.println("Se ha detenido la escucha");
            try {
                socketServidor.close();
            }catch (IOException e1){

            }

        }
    }

    public synchronized void pausePoint() {
        while (pausaFlag) {
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }


    public synchronized void pause() {
        pausaFlag = true;
    }

    public synchronized void unpause() {
        pausaFlag = false;
        this.notifyAll();
    }

    public boolean isPausaFlag() {
        return pausaFlag;
    }

    public void setPausaFlag(boolean pausaFlag) {
        this.pausaFlag = pausaFlag;
    }
}
