package Client.Network;

import java.io.*;
import java.net.*;

public class RequestHandler {

    private int portnumber = 12345;

    private String ipaddress = "localhost";

    public void main(String[] args) {

    try (Socket socket = new Socket(ipaddress, portnumber)) {
            System.out.println("Connected to server.");
    } 
    catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}