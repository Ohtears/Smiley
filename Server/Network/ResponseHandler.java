package Server.Network;

import java.io.*;
import java.net.*;
import org.json.JSONObject;

import Server.Database.MYSQLHandler;
import Server.Services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseHandler {
    private static final int PORT_NUMBER = 12345;
    private static final Logger LOGGER = Logger.getLogger(ResponseHandler.class.getName());

    protected static Map<UserService, Socket> userSocketMap = new HashMap<>();
    public static void main(String[] args) {
        
        UserStatusMonitor monitor = new UserStatusMonitor();
        monitor.startMonitoring();

        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            LOGGER.log(Level.INFO, "Server started...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Client connected: " + clientSocket.getInetAddress());
                new Thread(() -> handleClient(clientSocket)).start(); 
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server exception: " + e.getMessage(), e);
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            handleRequest(clientSocket);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Client handling exception: " + e.getMessage(), e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to check client socket status: " + e.getMessage(), e);
            }
        }
    }
    
    
    private static void handleRequest(Socket clientSocket) throws IOException {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
        String jsonString = in.readLine();
        LOGGER.log(Level.INFO, "Received request: " + jsonString);
        // out.println("completed");
        JSONObject jsonObject = new JSONObject(jsonString);
        UserRequest userRequest = JsonConverter.jsonToUsers(jsonObject); 

        try {

            UserService user = userRequest.users.get(0);
            userSocketMap.put(user, clientSocket);
            MYSQLHandler.setUserStatus(userRequest.users.get(0), "online");
            //needs further implementation
        }
        catch (Exception e) {
            System.out.println(e);
        }
        JSONObject processedRequest = RequestProcessor.processRequests(userRequest); 

        String responseString = (processedRequest != null) ? processedRequest.toString() : "200 OK";        

        LOGGER.log(Level.INFO, "Sending response: " + responseString);

        sendResponse(clientSocket, responseString);
    }
     catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Exception in handling client request", e);
     }
}

    protected static void sendResponse(Socket clientSocket, String jsonResponse) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(jsonResponse);
        out.flush();
    }
}
