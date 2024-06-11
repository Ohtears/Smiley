package Client.Network;

import org.json.JSONObject;
import java.io.*;
import java.net.*;

public class RequestHandler {
    private static int portNumber = 12345;
    private static String ipAddress = "localhost";

    public static JSONObject call(JSONObject jsonObject) {
        try (Socket socket = new Socket(ipAddress, portNumber)) {

            String jsonString = jsonObject.toString();

            jsonString += "\n";

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(jsonString.getBytes());
            outputStream.flush();
            socket.shutdownOutput();

            // InputStream inputStream = socket.getInputStream();
            // BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // StringBuilder responseBuilder = new StringBuilder();
            // String line;
            // while ((line = reader.readLine()) != null) {
            //     responseBuilder.append(line);
            // }

            // JSONObject jsonResponse = new JSONObject(responseBuilder.toString());

            // return jsonResponse;

        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            }
        return null;
            }
}
