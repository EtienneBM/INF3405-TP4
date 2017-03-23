import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Etienne BINET on 2017-03-02.
 */
public class Client {
    private static InetAddress ipAdress;
    private static int portNumber = 5001;

    public static void main( String [] args ) throws Exception
    {
        if (args.length < 2){
            System.out.println("Not enough arguments, you need 2 arguments :");
            System.out.println("sever <ipAdress> <portNumber>");
        }
        ipAdress = InetAddress.getByName(args[0]);
        portNumber = Integer.parseInt(args[1]);

        if (!(portNumber > 5000 && portNumber < 5050)){
            System.out.println("port number must be between 5000 and 5050");
            return;
        }

        Socket socket;
        BufferedReader stdIn;
        BufferedReader  fromServer;
        PrintWriter toServer;
        String result;

        socket = new Socket( ipAdress, portNumber );

        fromServer = new BufferedReader( new InputStreamReader( socket.getInputStream() ),200 );
        toServer = new PrintWriter( new OutputStreamWriter( socket.getOutputStream() ), true );

        while ( fromServer != null )
        {
            result = fromServer.readLine();
            System.out.println( "reverse: " + result );
            stdIn = new BufferedReader( new InputStreamReader( System.in ) );
            toServer.println( stdIn.readLine() );
        }
        socket.close();
    }
}

//    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//    BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));

