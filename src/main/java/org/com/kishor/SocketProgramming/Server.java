package org.com.kishor.SocketProgramming;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream dos;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for client...");
            socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Start thread to read messages from client
            new ServerHandler(socket).start();

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
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server(9080); // Server runs on port 5000
    }
}
