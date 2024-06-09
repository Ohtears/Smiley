package Server.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ResponseHandler {
 
    private int portnumber = 12345;


    public void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(portnumber)) {
            System.out.println("Server started...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());
        

        
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }

}
