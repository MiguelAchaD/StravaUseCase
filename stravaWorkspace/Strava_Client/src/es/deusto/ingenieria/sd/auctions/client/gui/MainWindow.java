package es.deusto.ingenieria.sd.auctions.client.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private UserWindow userWindow;
	private TrainingSessionWindow trainingSessionWindow;
	private ChallengeWindow challengeWindow;
    private JButton btnChallenges, btnTrainingSessions, btnLogout;

    public MainWindow(UserWindow userWindow, ChallengeWindow challengeWindow, TrainingSessionWindow trainingSessionWindow) {
        super("Main Window");
        this.challengeWindow = challengeWindow;
        this.trainingSessionWindow = trainingSessionWindow;
        this.userWindow = userWindow;
        userWindow.setMainWindow(this);

        btnChallenges = new JButton("Go to Challenges");
        btnTrainingSessions = new JButton("Go to Training Sessions");
        btnLogout = new JButton("Logout");

        btnChallenges.addActionListener(e -> openChallengeWindow());
        btnTrainingSessions.addActionListener(e -> openTrainingSessionWindow());
        btnLogout.addActionListener(e -> logout());

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(btnChallenges);
        add(btnTrainingSessions);
        add(btnLogout);

        getContentPane().setBackground(new Color(230, 230, 250));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 150);
        addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				logout();
			}
		});
    }

    private void openChallengeWindow() {
    	challengeWindow.setMainWindow(this);
        challengeWindow.setVisible(true);
        setVisible(false);
    }

    private void openTrainingSessionWindow() {
    	trainingSessionWindow.setMainWindow(this);
    	trainingSessionWindow.setVisible(true);
    	setVisible(false);
    }

    private void logout() {
        userWindow.logout();
        userWindow.setVisible(true);
        setVisible(false);
    }
    
    public void init() {
    	userWindow.setVisible(true);
    }
}
