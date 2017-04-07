import java.io.*;
import java.net.Socket;

/**
 * Created by etien on 23/03/2017.
 */
public class Task extends Thread {
    private Socket socket;
    private String question;
    private DataInputStream is = null;
    private DataOutputStream out = null;
    private boolean accepted;


    public Task (Socket socket, String question, boolean accepted){
        this.socket = socket;
        this.question = question;
        this.accepted = accepted;
    }

    //méthode exécuté au lancement du Thread
    public void run(){
        try {
            is = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        while (true) {
            try {
                out.writeBytes(question +"\n\r");
                out.flush();
                //On écrit dans le fichier réponse que si le temps n'est pas écoulé
                if (accepted){
                    FileWriter fw = new FileWriter("journal.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw);
                    out.println(is.readLine()+"\n");
                }
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

    }




}
