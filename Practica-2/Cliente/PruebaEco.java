import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.print.event.PrintEvent;
import java.io.InputStreamReader;

/**
 * Esiel Kevin Arizmendi Ramírez
 * Programación Distribuida Aplicada
 * Benemérita Universidad Autónoma de Puebla
 * Primavera 2021
 */

public class PruebaEco {
    public static void main(String[] args) {
        //Servidor
        new Thread(new Runnable(){
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                DataOutputStream salidaS = null;
                BufferedReader entradaS = null;
                Socket client = null;

                try {
                    System.out.println("||-- Server up --||");
                    serverSocket = new ServerSocket(7);
                    client = serverSocket.accept();
                    salidaS = new DataOutputStream(client.getOutputStream());
                    entradaS = new BufferedReader(new InputStreamReader(client.getInputStream()));
                } catch (Exception e) {
                    System.err.println("Error en el servidor");
                    System.exit(0);
                }

                if (client != null && salidaS != null && entradaS != null){
                    try {
                        String userInput;
                        while((userInput = entradaS.readLine()) != null){
                            salidaS.writeBytes(userInput);
                            salidaS.writeByte('\n');
                        }

                        System.out.println("Se cierra el server");
                        salidaS.close();
                        entradaS.close();
                        client.close();
                        serverSocket.close();
                    }catch(IOException e){
                        System.err.println("E/S fallo en la conexión a: " + args[0]);
                    }
                }  
            }
        }).start();

        //Cliente
        //Delay para dejar que se inicie el servidor correctamente
        try { Thread.sleep(1000);}catch (Exception e) {System.out.println("Error");};
        Socket ecoSocket = null;
        DataOutputStream salida = null;
        /*BufferedReader d
        = new BufferedReader(new InputStreamReader(in));*/
        
        BufferedReader entrada = null, stdIn = null;
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        //DataInputStream entrada = null, stdIn = null;
    // stdIn = new DataInputStream(System.in);
        
        /* Conexión del Socket entre el cliente y el servidor y apertura
        del canal E/S sobre el socket */
        try {
            ecoSocket = new Socket(args[0], 7);
            salida = new DataOutputStream(ecoSocket.getOutputStream());
            entrada = new BufferedReader(new InputStreamReader(ecoSocket.getInputStream()));
                    //new DataInputStream(ecoSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.out.println("No conozco al host: " + args[0]);
        } catch (IOException e){
            System.err.println("Error de E/S para la conexión con: " + args[0]);
        }

        /* Lectura del stream de entrada estándar una línea cada vez 
            El programa escribe inmeditamente la entrada seguida por
            un caracter de nueva linea en el stream de salida 
            conectado al socket */
        if (ecoSocket != null && salida != null && entrada != null){
            try {
                String userInput;
                while(!(userInput = stdIn.readLine()).equals("0")){
                    salida.writeBytes(userInput);
                    salida.writeByte('\n');
                    System.out.println("eco: " + entrada.readLine());
                }
                System.out.println("Se cierra el cliente");
                salida.close();
                entrada.close();
                ecoSocket.close();
            }catch(IOException e){
                System.err.println("E/S fallo en la conexión a: " + args[0]);
            }
        }
    }
}
