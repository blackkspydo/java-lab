package org.com.kishor.SocketProgramming;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private DataOutputStream dos;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server.");

            // Start thread to read messages from server
            new ClientHandler(socket).start();

            dos = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            String message;

            while (true) {
                message = scanner.nextLine();
                dos.writeUTF(message);
                dos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null)
                    dos.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client("localhost", 9080);
    }
}
