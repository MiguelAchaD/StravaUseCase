package es.deusto.ingenieria.sd.auctions.client.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import es.deusto.ingenieria.sd.auctions.client.controller.ChallengeController;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;

@SuppressWarnings("serial")
public class ChallengeWindow extends JFrame {

	private static final Color BACKGROUNDCOLOR = new Color(230, 230, 250);

	private MainWindow mainWindow;
	private ChallengeController challengeController;
	private List<ChallengeDTO> challenges;
	private CardLayout cardLayout;
	private JPanel cardPanel, buttonPanel;
	private JRadioButton rbtnRunning, rbtnCycling;
	private boolean isOnSetUpChallengeMode;
	private JTable challengeTable;
	private ChallengeTableModel challengeTableModel;
	private JButton btnAcceptChallenge, btnViewActiveChallenges, btnViewAllChallenges, btnSetUpNewChallenge,
			btnToogleMode;
	private JTextField txtName, txtStartDate, txtEndDate, txtTargetDistance, txtTargetTime, txtFilter;

	public ChallengeWindow(ChallengeController challengeController) {
		super("Challenges");
		this.challengeController = challengeController;
		isOnSetUpChallengeMode = true;
		initComponents();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainWindow.setVisible(true);
			}
		});
		setSize(550, 550);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		buttonPanel = new JPanel();

		btnToogleMode = new JButton("View Challenges");
		btnSetUpNewChallenge = new JButton("Set Up New Challenge");
		btnAcceptChallenge = new JButton("Accept Challenge");
		btnViewActiveChallenges = new JButton("View Active Challenges");
		btnViewAllChallenges = new JButton("View All Challenges");

		btnAcceptChallenge.addActionListener(e -> acceptChallenge());
		btnViewActiveChallenges.addActionListener(e -> viewActiveChallenges());
		btnViewAllChallenges.addActionListener(e -> viewAllChallenges());
		btnSetUpNewChallenge.addActionListener(e -> setUpANewChallenge());
		btnToogleMode.addActionListener(e -> updateLayout());

		buttonPanel.setBackground(BACKGROUNDCOLOR);
		buttonPanel.add(btnToogleMode);
		buttonPanel.add(btnSetUpNewChallenge);
		add(buttonPanel, BorderLayout.SOUTH);

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		cardPanel.add(createSetUpChallengePanel(), "SetUpChallenge");
		cardPanel.add(createViewChallengesPanel(), "ViewChallenges");

		add(cardPanel, BorderLayout.CENTER);

	}

	private JPanel createSetUpChallengePanel() {
		JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
		mainPanel.setBackground(BACKGROUNDCOLOR);

		txtName = new JTextField(10);
		txtStartDate = new JTextField(10);
		txtEndDate = new JTextField(10);
		txtTargetDistance = new JTextField(10);
		txtTargetTime = new JTextField(10);

		addLabeledComponentToPanel(mainPanel, "Name: ", txtName);
		addLabeledComponentToPanel(mainPanel, "Start Date (yyyy-mm-dd): ", txtStartDate);
		addLabeledComponentToPanel(mainPanel, "End Date (yyyy-mm-dd): ", txtEndDate);
		addLabeledComponentToPanel(mainPanel, "Target Distance (km): ", txtTargetDistance);
		addLabeledComponentToPanel(mainPanel, "Target Time (minutes): ", txtTargetTime);

		// Sport Type
		JPanel sportTypePanel = new JPanel();
		sportTypePanel.setLayout(new BoxLayout(sportTypePanel, BoxLayout.Y_AXIS));
		sportTypePanel.setBackground(BACKGROUNDCOLOR);
		rbtnRunning = new JRadioButton("RUNNING");
		rbtnCycling = new JRadioButton("CYCLING");
		rbtnRunning.setBackground(BACKGROUNDCOLOR);
		rbtnCycling.setBackground(BACKGROUNDCOLOR);
		sportTypePanel.add(rbtnRunning);
		sportTypePanel.add(rbtnCycling);
		addLabeledComponentToPanel(mainPanel, "Sport Type: ", sportTypePanel);

		return mainPanel;
	}

	private void addLabeledComponentToPanel(JPanel panel, String labelText, Component component) {
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		leftPanel.setBackground(BACKGROUNDCOLOR);
		rightPanel.setBackground(BACKGROUNDCOLOR);

		leftPanel.add(new JLabel(labelText));
		rightPanel.add(component);

		panel.add(leftPanel);
		panel.add(rightPanel);
	}

	private JPanel createViewChallengesPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(BACKGROUNDCOLOR);

		challengeTableModel = new ChallengeTableModel(new ArrayList<>());
		challengeTable = new JTable(challengeTableModel);
		challengeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(challengeTable);
		scrollPane.setBackground(BACKGROUNDCOLOR);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		configureTableColumns();

		txtFilter = new JTextField(20);
		txtFilter.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				filter();
			}

			public void removeUpdate(DocumentEvent e) {
				filter();
			}

			public void insertUpdate(DocumentEvent e) {
				filter();
			}
		});
		JPanel northPanel = new JPanel();
		northPanel.setBackground(BACKGROUNDCOLOR);
		northPanel.add(new JLabel("Filter by Name: "));
		northPanel.add(txtFilter);
		mainPanel.add(northPanel, BorderLayout.NORTH);

		return mainPanel;
	}

	private void updateLayout() {
		buttonPanel.removeAll();
		buttonPanel.add(btnToogleMode);
		if (isOnSetUpChallengeMode) {
			cardLayout.show(cardPanel, "ViewChallenges");
			btnToogleMode.setText("Set Up New Challenge");
			buttonPanel.add(btnViewActiveChallenges);
			buttonPanel.add(btnAcceptChallenge);
			viewAllChallenges();
		} else {
			cardLayout.show(cardPanel, "SetUpChallenge");
			btnToogleMode.setText("View Challenges");
			buttonPanel.add(btnSetUpNewChallenge);
		}
		buttonPanel.revalidate();
		buttonPanel.repaint();
		isOnSetUpChallengeMode = !isOnSetUpChallengeMode;
	}

	private void viewAllChallenges() {
		challenges = challengeController.getAllChallenges();
		challengeTableModel.updateInformation(challenges);
		buttonPanel.remove(btnViewAllChallenges);
		buttonPanel.add(btnViewActiveChallenges);
		buttonPanel.add(btnAcceptChallenge);
		revalidate();
		repaint();
	}

	private void viewActiveChallenges() {
		challenges = challengeController.getActiveChallenges();
		challengeTableModel.updateInformation(challenges);
		buttonPanel.remove(btnAcceptChallenge);
		buttonPanel.remove(btnViewActiveChallenges);
		buttonPanel.add(btnViewAllChallenges);
		revalidate();
		repaint();
	}

	private void acceptChallenge() {
		int selectedRow = challengeTable.getSelectedRow();
		if (selectedRow >= 0) {
			ChallengeDTO selectedChallenge = challengeTableModel.getChallengeAt(selectedRow);
			if (challengeController.acceptChallenge(selectedChallenge.getName())) {
				JOptionPane.showMessageDialog(this, "Challenge " + selectedChallenge.getName() + " accepted", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Could not accept challenge " + selectedChallenge.getName(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No challenge selected", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void setUpANewChallenge() {
		try {
			String name = txtName.getText();
			String startDate = txtStartDate.getText();
			String endDate = txtEndDate.getText();
			Duration targetTime = Duration.ofMinutes(Long.parseLong(txtTargetTime.getText()));
			String sportType = rbtnRunning.isSelected() ? "RUNNING" : rbtnCycling.isSelected() ? "CYCLING" : "";
			double targetDistance = Double.parseDouble(txtTargetDistance.getText());
			if (challengeController.createChallenge(name, startDate, endDate, targetTime, sportType, targetDistance)) {
				JOptionPane.showMessageDialog(this, "Challenge " + name + " created", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Challenge creation failed.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Invalid input " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	private void filter() {
		String filterText = txtFilter.getText();
		if (filterText.isEmpty()) {
			challengeTableModel.updateInformation(challenges);
		} else {
			List<ChallengeDTO> filteredChallenges = challenges.stream()
					.filter(challenge -> challenge.getName().toLowerCase().contains(filterText.toLowerCase()))
					.collect(Collectors.toList());
			challengeTableModel.updateInformation(filteredChallenges);
		}
	}

	private void configureTableColumns() {
		TableColumnModel columnModel = challengeTable.getColumnModel();
		int[] columnWidths = { 600, 500, 500, 500, 500, 500, 400 };
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
		}
	}
}
