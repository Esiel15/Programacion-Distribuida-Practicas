import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * WhoAmI
 * Programación Distribuida Aplicada
 * Primavera 2021
 * 201737811 - Esiel Kevin Arizmendi Ramírez 
 */
public class WhoAmI {
    public static void main(String[] args) throws UnknownHostException{
        if (args.length != 1) {
            System.err.println("Usage: java WhoAmI MachineName");
            System.exit(1);
        }

        InetAddress a = InetAddress.getAllByName(args[0])[1];
        System.out.println(a);
    }
    
}