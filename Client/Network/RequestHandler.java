package Client.Network;

import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class RequestHandler {
    private static int portnumber = 12345;
    private static String ipaddress = "localhost";

    public static void call(JSONArray jsonArray){
        try (Socket socket = new Socket(ipaddress, portnumber)) {

            JSONObject user1 = new JSONObject();
            user1.put("userid", 1);
            user1.put("Username", "john_doe");
            user1.put("Name", "John Doe");
            jsonArray.put(user1);

            String jsonString = jsonArray.toString();

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(jsonString.getBytes());
            outputStream.flush(); 


    } catch (IOException e) {
        System.out.println("Client exception: " + e.getMessage());
    }

    }


}
