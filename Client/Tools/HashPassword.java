package Client.Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
    
    public static String hashwithsha256(String password){
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
    
            byte[] bytes = md.digest(password.getBytes());
    
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        
        }
    }

}
