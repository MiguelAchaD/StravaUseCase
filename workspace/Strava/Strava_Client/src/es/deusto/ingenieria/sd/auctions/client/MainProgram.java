package es.deusto.ingenieria.sd.auctions.client;

import es.deusto.ingenieria.sd.auctions.client.controller.ChallengeController;
import es.deusto.ingenieria.sd.auctions.client.controller.TrainingSessionController;
import es.deusto.ingenieria.sd.auctions.client.controller.UserController;
import es.deusto.ingenieria.sd.auctions.client.gui.ChallengeWindow;
import es.deusto.ingenieria.sd.auctions.client.gui.MainWindow;
import es.deusto.ingenieria.sd.auctions.client.gui.TrainingSessionWindow;
import es.deusto.ingenieria.sd.auctions.client.gui.UserWindow;
import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;

public class MainProgram {

	public static void main(String[] args) {	
		ServiceLocator serviceLocator = new ServiceLocator();
		
		//args[0] = RMIRegistry IP
		//args[1] = RMIRegistry Port
		//args[2] = Service Name
		serviceLocator.setService(args[0], args[1], args[2]);
		
		UserController userController = new UserController(serviceLocator); 
		ChallengeController challengeController = new ChallengeController(serviceLocator);
		TrainingSessionController trainingSessionController = new TrainingSessionController(serviceLocator);
		
		
		UserWindow userWindow = new UserWindow(userController);
		ChallengeWindow challengeWindow = new ChallengeWindow(challengeController);
		TrainingSessionWindow trainingSessionWindow = new TrainingSessionWindow(trainingSessionController);
		new MainWindow(userWindow, challengeWindow, trainingSessionWindow).init();
	}
}