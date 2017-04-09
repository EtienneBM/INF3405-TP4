import java.io.*;
import java.net.Socket;

/**
 * Created by etien on 23/03/2017.
 */
public class Task extends Thread {
    private Socket socket;
    private String question;
    private DataInputStream is = null;

    public Task (Socket socket, String question){
        this.socket = socket;
        this.question = question;
    }

    //méthode exécuté au lancement du Thread
    public void run(){
        DataOutputStream out = null;
        try {
            is = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                out.writeBytes(question +"\n\r");
                out.flush();
                PrintWriter writer = new PrintWriter("journal.txt", "UTF-8");
                writer.println(is.readLine());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

    }




}
