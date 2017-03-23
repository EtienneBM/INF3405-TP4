import java.io.*;
import java.net.Socket;

/**
 * Created by etien on 23/03/2017.
 */
public class Task extends Thread {
    private Socket socket;
    private String question;

    public Task (Socket socket, String question){
        this.socket = socket;
        this.question = question;
    }

    //méthode exécuté au lancement du Thread
    public void run(){
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                out.writeBytes(question +"\n\r");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

    }




}
