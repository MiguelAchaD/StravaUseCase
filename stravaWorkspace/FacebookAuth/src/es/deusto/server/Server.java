package es.deusto.server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
    public static void handleClient(Socket clientSocket) {

        	try (InputStream inputStream = clientSocket.getInputStream()) {

            byte[] lengthBytes = new byte[4];
            inputStream.read(lengthBytes);
            int length = bytesToInt(lengthBytes);

            byte[] dataBytes = new byte[length];
            inputStream.read(dataBytes);
            String jsonData = new String(dataBytes, StandardCharsets.UTF_8);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String email = objectMapper.readTree(jsonData).get("email").asText();
            String hashedPassword = objectMapper.readTree(jsonData).get("password").asText();
            
            //TODO: Use DB.
//            if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
//                out.println("Autenticacion exitosa.");
//
//                out.println("Redirigiendo a la página de inicio de sesión de Facebook...");
//
//                String authToken = generateAuthToken();
//                out.println("Token de acceso: " + authToken);
//            } else {
//                out.println("Autenticacion fallida. Usuario o contraseña incorrectos.");
//            }
        	} catch (Exception e) {
        		//
        	}
    }
    
    private static int bytesToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
               ((bytes[1] & 0xFF) << 16) |
               ((bytes[2] & 0xFF) << 8)  |
               (bytes[3] & 0xFF);
    }

    private static String generateAuthToken() {
        return "TOKEN_SIMULADO";
    }
}
