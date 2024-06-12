package Client.Network;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

public class RequestHandler {
    private static final int TIMEOUT = 5000; // 5 seconds timeout
    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());

    public String sendRequest(String requestJson) throws IOException {
        LOGGER.log(Level.INFO, "Sending request to server: " + requestJson);
        Socket socket = new Socket("localhost", 12345); // replace with your server address and port
        socket.setSoTimeout(TIMEOUT);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(requestJson); // Send JSON string followed by newline
        out.flush();  // Ensure the request is sent out

        String response = in.readLine(); // This will wait for the server response
        LOGGER.log(Level.INFO, "Received response from server: " + response);

        in.close();   // Close the input stream
        out.close();  // Close the output stream
        socket.close(); // Close the socket connection

        return response;
    }

    public void sendRequestAsync(String requestJson, Callback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                String response = sendRequest(requestJson);
                SwingUtilities.invokeLater(() -> callback.onSuccess(response));
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to send request", e);
                SwingUtilities.invokeLater(() -> callback.onFailure(e));
            }
        });
        executor.shutdown();
    }

    public interface Callback {
        void onSuccess(String response);
        void onFailure(IOException e);
    }
}
