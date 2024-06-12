package Client.Network;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

public class RequestHandler {
    private static final int TIMEOUT = 5000;
    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());

    public String sendRequest(String requestJson) throws IOException {
        LOGGER.log(Level.INFO, "Sending request to server: " + requestJson);
        Socket socket = new Socket("localhost", 12345); 
        socket.setSoTimeout(TIMEOUT);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(requestJson);
        out.flush();  

        String response = in.readLine();
        LOGGER.log(Level.INFO, "Received response from server: " + response);

        in.close(); 
        out.close();  
        socket.close();

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
