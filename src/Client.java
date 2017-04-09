import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Etienne BINET on 2017-03-02.
 */
public class Client {
    private static InetAddress ipAdress;
    private static int portNumber = 5001;

    public static void main( String [] args ) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Saisissez l'adresse IP du serveur:");
        String ip_str = sc.nextLine();
        System.out.println("Saisissez le port d'écoute du serveur:");
        String port_str = sc.nextLine();

        ipAdress = InetAddress.getByName(ip_str);
        portNumber = Integer.parseInt(port_str);

        if (!(portNumber > 5000 && portNumber < 5050)){
            System.out.println("port number must be between 5000 and 5050");
            return;
        }

        System.out.println("Vous êtes connecté au server");
        System.out.println("Nom de l'étudiant: ");
        String name_str = sc.nextLine();

        Socket socket;
        BufferedReader stdIn;
        BufferedReader  fromServer;
        PrintWriter toServer;
        String result;

        socket = new Socket( ipAdress, portNumber );

        fromServer = new BufferedReader( new InputStreamReader( socket.getInputStream() ),200 );
        toServer = new PrintWriter( new OutputStreamWriter( socket.getOutputStream() ), true );

        if ( fromServer != null )
        {
            result = fromServer.readLine();
            System.out.println( "Question: " + result );
            stdIn = new BufferedReader( new InputStreamReader( System.in ) );
            toServer.println(name_str+" - "+stdIn.readLine() );


        }


        socket.close();
        System.out.println("Réponse envoyée");
    }
}


