package es.deusto.ingenieria.sd.auctions.client.gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.client.controller.ChallengeController;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;

public class ChallengeWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	private ChallengeController challengeController;

	private JPanel mainContainer;
	private JPanel buttonContainer;
	private JPanel options;
	private JRadioButton rbtnRunning, rbtnCycling;
	private boolean isOnSetUpChallengeMode;
	private JList<ChallengeDTO> challengeList;
	private JButton btnAcceptChallenge, btnViewActiveChallenges, btnViewAllChallenges, btnSetUpNewChallenge, btnChangeMode;
	private JTextField txtName, txtStartDate, txtEndDate, txtTargetDistance, txtTargetTime;

	public ChallengeWindow(ChallengeController challengeController) {
		super("ChallengeWindow");
		this.challengeController = challengeController;
		isOnSetUpChallengeMode = false;
		initComponents();
		configureLayout();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainWindow.setVisible(true);
			}
		});
	}

	private void initComponents() {
		mainContainer = new JPanel(new GridLayout(0, 2, 10, 10));
		buttonContainer = new JPanel(new FlowLayout());
		options = new JPanel(new FlowLayout());
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
		challengeList = new JList<>();
		btnAcceptChallenge = new JButton("Accept Challenge");
		btnViewActiveChallenges = new JButton("View Active Challenges");
		btnViewAllChallenges = new JButton("View All Challenges");
		btnSetUpNewChallenge = new JButton("Set Up New Challenge");
		btnChangeMode = new JButton();
		btnAcceptChallenge.addActionListener(e -> acceptChallenge());
		btnViewActiveChallenges.addActionListener(e -> viewActiveChallenges());
		btnViewAllChallenges.addActionListener(e -> viewAllChallenges());
		btnSetUpNewChallenge.addActionListener(e -> setUpANewChallenge());
		btnChangeMode.addActionListener(e -> updateLayout());
		txtName = new JTextField(20);
		txtStartDate = new JTextField(20);
		txtEndDate = new JTextField(20);
		txtTargetDistance = new JTextField(20);
		txtTargetTime = new JTextField(20);
		rbtnRunning = new JRadioButton("RUNNING");
		rbtnCycling = new JRadioButton("CYCLING");
		getContentPane().setBackground(new Color(230, 230, 250));
		options.setBackground(new Color(230, 230, 250));
		mainContainer.setBackground(new Color(230, 230, 250));
	}

	private void configureLayout() {
		btnChangeMode.setText("Set Up a New Challenge");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(new JScrollPane(challengeList));
		options.add(rbtnCycling);
		options.add(rbtnRunning);
		buttonContainer.add(btnViewAllChallenges);
		buttonContainer.add(btnChangeMode);
		getContentPane().add(buttonContainer);
		revalidate();
		repaint();
		pack();
	}

	private void updateLayout() {
		getContentPane().removeAll();
		buttonContainer.removeAll();
		if (!isOnSetUpChallengeMode) {
			mainContainer.add(new JLabel("Name: "));
			mainContainer.add(txtName);
			mainContainer.add(new JLabel("Start Date (yyyy-mm-dd): "));
			mainContainer.add(txtStartDate);
			mainContainer.add(new JLabel("End Date (yyyy-mm-dd): "));
			mainContainer.add(txtEndDate);
			mainContainer.add(new JLabel("Target Distance (km): "));
			mainContainer.add(txtTargetDistance);
			mainContainer.add(new JLabel("Target Time (minutes): "));
			mainContainer.add(txtTargetTime);
			mainContainer.add(new JLabel("Sport Type: "));
			mainContainer.add(options);
			buttonContainer.add(btnChangeMode);
			buttonContainer.add(btnSetUpNewChallenge);
			getContentPane().add(mainContainer);
			getContentPane().add(buttonContainer);
			btnChangeMode.setText("View Challenges");
			revalidate();
			repaint();
			pack();
		} else {
			configureLayout();
		}
		isOnSetUpChallengeMode = !isOnSetUpChallengeMode;
	}

	private void acceptChallenge() {
		ChallengeDTO selectedChallenge = challengeList.getSelectedValue();
		if (selectedChallenge != null) {
			challengeController.acceptChallenge(mainWindow.getToken(), selectedChallenge.getName());
		}
	}

	private void viewAllChallenges() {
		List<ChallengeDTO> challenges = challengeController.getAllChallenges(mainWindow.getToken());
		challengeList.setListData(challenges.toArray(new ChallengeDTO[0]));
		buttonContainer.add(btnAcceptChallenge);
		buttonContainer.add(btnViewActiveChallenges);
		buttonContainer.remove(btnViewAllChallenges);
		revalidate();
		repaint();
		pack();
	}

	private void viewActiveChallenges() {
		List<ChallengeDTO> challenges = challengeController.getActiveChallenges(mainWindow.getToken());
		challengeList.setListData(challenges.toArray(new ChallengeDTO[0]));
		buttonContainer.remove(btnAcceptChallenge);
		buttonContainer.remove(btnViewActiveChallenges);
		buttonContainer.add(btnViewAllChallenges);
		revalidate();
		repaint();
		pack();
	}

	private void setUpANewChallenge() {
		try {
			String name = txtName.getText();
			String startDate = txtStartDate.getText();
			String endDate = txtEndDate.getText();
			Duration targetTime = Duration.ofMinutes(Long.parseLong(txtTargetTime.getText()));
			String sportType = rbtnRunning.isSelected() ? "RUNNING" : rbtnCycling.isSelected() ? "CYCLING" : "";
			double targetDistance = Double.parseDouble(txtTargetDistance.getText());
			if (challengeController.createChallenge(mainWindow.getToken(), name, startDate, endDate, targetTime, sportType, targetDistance)) {
				JOptionPane.showMessageDialog(this, "Challenge " + name + " created", "Success", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this, "Challenge creation failed.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Invalid input " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

}
