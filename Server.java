package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class server {


    public static void main(String[] args) {
        try
        {
            ServerSocket server = new ServerSocket(5000);
            ArrayList<ClientHandler> clients = new ArrayList();

            while (true)
            {
                try
                {
                    Socket socket = server.accept();
                    ClientHandler cl = new ClientHandler(socket,clients);
                    clients.add(cl);
                    Thread t = new Thread(cl);
                    t.start();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

    }
}
