package es.deusto.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.deusto.server.Server;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
        
        try (ServerSocket serverSocket = new ServerSocket(22222)) {
        	LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String fecha = fechaActual.format(formatter);
        	logger.info("[ " + Main.class.getName() + " ] - " + fecha + " - Server initialized");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("[ " + Server.class.getName() + " ] - " + fecha + " - ");
                new Thread(() -> Server.handleClient(clientSocket)).start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
