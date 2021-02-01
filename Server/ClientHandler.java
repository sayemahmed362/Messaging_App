package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientHandler extends Thread {
    private ArrayList<ClientHandler> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        label80:
        while(true) {
            try {
                String msg;
                if ((msg = this.reader.readLine()) != null && !msg.equalsIgnoreCase("exit")) {
                    Iterator x = this.clients.iterator();

                    while(true) {
                        if (!x.hasNext()) {
                            continue label80;
                        }
                        ClientHandler cl = (ClientHandler)x.next();
                        cl.writer.println(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    this.reader.close();
                    this.writer.close();
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }
}

