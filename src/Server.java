import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Etienne BINET on 2017-03-02.
 */
public class Server {

    //------- Arguments de la classe server ------------
    //informations relatives au server a passer en argument
    private static InetAddress ipAdress;
    private static int portNumber = 5001;
    private static int surveyTime = 0;


    //Methode appele au lancement du server
    public static void main(String args[]) throws IOException, UnknownHostException {

        //lecture des arguments depuis le fichier texte
        String [] arg = new String[3];
        try{
            InputStream fichier = new FileInputStream("server.txt");
            InputStreamReader fichierReader = new InputStreamReader(fichier);
            BufferedReader br = new BufferedReader(fichierReader);
            String ligne;
            int i = 0;
            while ((ligne=br.readLine())!=null){//ajoute les opérations du fichier texte dans l'array list de type operations
                arg[i] = ligne;
                i++;
            }
            //l'ArrayLit de type operation est remplie on ferme le fichier texte.
            br.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

        long delta = 0;
        long startTime = System.currentTimeMillis();
        //pour lancer le serveur il faut que l'utilisateur entre les 3 arguments necessaires
        if (arg.length >= 3) {
            //getByName se contente de verifier la validite du format d'adresse
            ipAdress = InetAddress.getByName(arg[0]);

            portNumber = Integer.parseInt(arg[1]);
            //indique à l'utilisateur s'il s'est trompé de port
            if (!(portNumber > 5000 && portNumber < 5050)){
                System.out.println("port number must be between 5000 and 5050");
                return;
            }
            surveyTime = Integer.parseInt(arg[2]);

            System.out.println(ipAdress);
            System.out.println(portNumber);
            System.out.println(surveyTime);

            //Une fois le serveur lancé on demande au prof de poser sa question, taille maximale 500 caractères
//            System.out.println("Veuillez poser votre question :");
//            BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ), 500 );

            //Question posée au prof
            Scanner sc = new Scanner(System.in);
            System.out.println("Veuillez poser votre question :");
            String str = sc.nextLine();
            System.out.println("Vous avez saisi : " + str);

            //On écrit la question dans le journal
            PrintWriter writer = new PrintWriter("journal.txt", "UTF-8");
            writer.println(str);
            writer.close();

            //Le serveur ne commence à écouter sur son port seulement si une question a été posée
            if(str != null) {

                System.out.println("Server listening ...");
                ServerSocket serverSocket = new ServerSocket(portNumber, 10, ipAdress);
                while (true) {
                    delta = (System.currentTimeMillis() - startTime)/1000;
                    System.out.println(delta);
                    //si le délais n'est pas dépassé
                    if(delta < surveyTime){
                        //a chaque fois q'un client se connecte
                        Socket clientSocket = serverSocket.accept();
                        Task task = new Task(clientSocket, str);
                        task.start();
                    }else{
                        return;
                    }

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

