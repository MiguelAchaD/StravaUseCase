package es.deusto.ingenieria.sd.auctions.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private UserWindow userWindow;
	private TrainingSessionWindow trainingSessionWindow;
	private ChallengeWindow challengeWindow;
    private JButton btnChallenges, btnTrainingSessions, btnLogout;

    public MainWindow(UserWindow userWindow, ChallengeWindow challengeWindow, TrainingSessionWindow trainingSessionWindow) {
        super("Main Window");
        this.challengeWindow = challengeWindow;
        this.trainingSessionWindow = trainingSessionWindow;
        this.userWindow = userWindow;
        this.userWindow.setMainWindow(this);

        btnChallenges = new JButton("Go to Challenges");
        btnTrainingSessions = new JButton("Go to Training Sessions");
        btnLogout = new JButton("Logout");

        btnChallenges.addActionListener(e -> openChallengeWindow());
        btnTrainingSessions.addActionListener(e -> openTrainingSessionWindow());
        btnLogout.addActionListener(e -> logout());

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.add(createStyledButton(btnChallenges));
        this.add(createStyledButton(btnTrainingSessions));
        this.add(createStyledButton(btnLogout));

        this.getContentPane().setBackground(new Color(230, 230, 250));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        
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
        this.setVisible(false);
    }

    private void openTrainingSessionWindow() {
    	trainingSessionWindow.setMainWindow(this);
    	trainingSessionWindow.setVisible(true);
    	this.setVisible(false);
    }

    private void logout() {
        userWindow.logout();
        userWindow.setVisible(true);
        this.setVisible(false);
    }
    
    public long getToken() {
    	return userWindow.getToken();
    }
    
    public void init() {
    	userWindow.setVisible(true);
    }
    
    private JButton createStyledButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }
}
