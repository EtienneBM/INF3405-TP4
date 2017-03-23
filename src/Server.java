import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by Etienne BINET on 2017-03-02.
 */
public class Server {

    //------- Arguments de la classe server ------------
    //informations relatives au server a passer en argument
    private static InetAddress ipAdress;
    private static int portNumber = 5001;
    private static int surveyTime = 0;


    private int nbClients = 0;


    //Methode appele au lancement du server
    public static void main(String args[]){

        LocalTime time = null;
        int delta = 0;
        int startTime = 0;
        //pour lancer le serveur il faut que l'utilisateur entre les 3 arguments necessaires
        if (args.length >= 3) {
            try {
                //getByName se contente de verifier la validite du format d'adresse
                ipAdress = InetAddress.getByName(args[0]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            portNumber = Integer.parseInt(args[1]);
            //indique à l'utilisateur s'il s'est trompé de port
            if (!(portNumber > 5000 && portNumber < 5050)){
                System.out.println("port number must be between 5000 and 5050");
                return;
            }
            surveyTime = Integer.parseInt(args[2]);

            //Une fois le serveur lancé on demande au prof de poser sa question, taille maximale 500 caractères
            System.out.println("Veuillez poser votre question :");
            BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ), 500 );

            //Le serveur ne commence à écouter sur son port seulement si une question a été posée
            if(stdIn != null) {
                startTime = time.getSecond();
                System.out.println("Server listening ...");
                try {
                    ServerSocket serverSocket = new ServerSocket(portNumber, 10, ipAdress);
                    while (true) {
                        delta = time.getSecond() - startTime;
                        //si le délais n'est pas dépassé
                        if(surveyTime < delta){
                            //a chaque fois q'un client se connecte
                            Socket clientSocket = serverSocket.accept();
                            Task task = new Task(clientSocket, stdIn.readLine());
                            task.start();
                        }else{
                            return;
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //serverSocket.close();
            }
        }
        //sinon le server ne se lance pas
        else {
            System.out.println("Not enough arguments, you need 3 arguments :");
            System.out.println("sever <ipAdress> <portNumber> <surveyTime>");
        }

    }
}

