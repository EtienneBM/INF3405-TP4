import com.sun.org.apache.xml.internal.security.utils.ElementCheckerImpl;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * Created by Etienne BINET on 2017-03-02.
 */
public class Server {

    //------- Arguments de la classe server ------------
    //informations relatives au server a passer en argument
    private static InetAddress ipAdress;
    private static int portNumber = 18000;
    private static int surveyTime = 0;

    private Vector clients = new Vector();
    private int nbClients = 0;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    //Methode appele au lancement du server
    public static void main(String args[]){

        Server server = new Server();

        //pour lancer le serveur il faut que l'utilisateur entre les 3 arguments necessaires
        if (args.length >= 3){
            try {
                //getByName se contente de verifier la validite du format d'adresse
                ipAdress = InetAddress.getByName(args[0]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            portNumber = Integer.parseInt(args[1]);
            surveyTime = Integer.parseInt(args[2]);
            //fait tourner le serveur
            while(true){
                server.run();
            }
        //sinon le server ne se lance pas
        }else{
            System.out.println("Not enough arguments, you need 3 arguments :");
            System.out.println("sever <ipAdress> <portNumber> <surveyTime>");
        }

    }

    //constructeur du serveur
    public Server(){
        super();
    }

    //---------------------  Methode ne repondant pas du tout au tp pour l'instant  -----------------
    private void run(){
        try {
            serverSocket = new ServerSocket(portNumber, 10 , ipAdress);
            clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));

        }catch (Exception e){
            System.out.println(e);
        }

    }
}
