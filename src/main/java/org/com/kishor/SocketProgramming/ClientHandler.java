package org.com.kishor.SocketProgramming;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private DataInputStream dis;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = dis.readUTF();
                System.out.println("Server: " + message);
            }
        } catch (IOException e) {
            System.out.println("Server disconnected.");
        } finally {
            try {
                dis.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
