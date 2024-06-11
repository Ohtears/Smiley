package Server.Network;

import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class ResponseHandler {

    private static int portnumber = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(portnumber)) {
            System.out.println("Server started...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    handleRequest(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String jsonString = stringBuilder.toString();

        JSONObject jsonObject = new JSONObject(jsonString);
        UserRequest userrequest = JsonConverter.jsonToUsers(jsonObject);

        JSONObject processedrequest = RequestProcessor.processRequests(userrequest);

        if (processedrequest.equals(null)){

            //empty for now
        }
        else {
            String stringrequest = processedrequest.toString();

            sendResponse(clientSocket, stringrequest);
        }


    }


    private static void sendResponse(Socket clientSocket, String jsonResponse) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(jsonResponse);
    }
}
