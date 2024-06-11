package Server.Network;

import java.io.*;
import java.net.*;
import org.json.JSONArray;

public class ResponseHandler {

    private static int portnumber = 12345;

    public static void main(String[] args) {
        
        try (ServerSocket serverSocket = new ServerSocket(portnumber)) {
            System.out.println("Server started...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();

            JSONArray jsonArray = new JSONArray(jsonString);

            System.out.println("Received JSONArray: " + jsonArray.toString());

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
