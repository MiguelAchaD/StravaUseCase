package es.deusto.ingenieria.sd.auctions.client.gui;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import es.deusto.ingenieria.sd.auctions.client.controller.TrainingSessionController;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionDTO;

@SuppressWarnings("serial")
public class TrainingSessionWindow extends JFrame{
	private MainWindow mainWindow;
	private TrainingSessionController trainingSessionController;
	private JList<TrainingSessionDTO> trainingSessionsList;
    private JTextField txtTitle, txtStartDate, txtStartTime, txtDuration, txtDistance;
    private JButton btnCreateSession, btnToogle, btnViewPersonalTrainingSessions, btnViewAllTrainingSessions;
    private JPanel mainPanel, buttonContainer;
    private boolean isOnCreateMode;

    public TrainingSessionWindow(TrainingSessionController trainingSessionController) {
        super("Training Sessions");
        this.trainingSessionController = trainingSessionController;
        isOnCreateMode = true;
        initComponents();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	mainWindow.setVisible(true);
            }
        });
        setSize(400, 200);
    }

    private void initComponents() {
        txtTitle = new JTextField(20);
        txtStartDate = new JTextField(20);
        txtStartTime = new JTextField(20);
        txtDuration = new JTextField(20);
        txtDistance = new JTextField(20);
        trainingSessionsList = new JList<TrainingSessionDTO>();
        btnToogle = new JButton();
        btnCreateSession = new JButton("Create Session");
        btnViewPersonalTrainingSessions = new JButton("View Personal Training Sessions");
        btnViewAllTrainingSessions = new JButton("View All Training Sessions");
        btnCreateSession.addActionListener(e -> createTrainingSession());
        btnViewPersonalTrainingSessions.addActionListener(e -> viewPersonalTrainingSessions());
        btnViewAllTrainingSessions.addActionListener(e -> viewAllTrainingSessions());
        btnToogle.addActionListener(e -> toogleLayout());
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(230, 230, 250));
        buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonContainer.setBackground(new Color(230, 230, 250));
        configureLayout();
    }
    
    private void configureLayout() {
    	mainPanel.setLayout(new GridLayout(6, 2, 10, 10));
    	btnToogle.setText("View All Sessions");
        mainPanel.add(new JLabel("Title:"));
        mainPanel.add(txtTitle);
        mainPanel.add(new JLabel("Start Date (yyyy-mm-dd):"));
        mainPanel.add(txtStartDate);
        mainPanel.add(new JLabel("Start Time (HH:MM):"));
        mainPanel.add(txtStartTime);
        mainPanel.add(new JLabel("Duration (minutes):"));
        mainPanel.add(txtDuration);
        mainPanel.add(new JLabel("Distance (km):"));
        mainPanel.add(txtDistance);
        buttonContainer.add(btnCreateSession);
        buttonContainer.add(btnToogle);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(mainPanel);
        getContentPane().add(buttonContainer);
    }
    
    private void toogleLayout() {
    	mainPanel.removeAll();
    	if(isOnCreateMode) {
    		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    		buttonContainer.remove(btnCreateSession);
    		btnToogle.setText("Create Session");
    		mainPanel.add(add(new JScrollPane(trainingSessionsList)));
    		viewAllTrainingSessions();
    	}else {
    		buttonContainer.removeAll();
    		configureLayout();
    	}
		revalidate();
		repaint();
    	isOnCreateMode = !isOnCreateMode;
    }
    
    private void viewAllTrainingSessions() {
		List<TrainingSessionDTO> trainingSessions = trainingSessionController.getAllTrainingSessions();
		trainingSessionsList.setListData(trainingSessions.toArray(new TrainingSessionDTO[0]));
		buttonContainer.add(btnViewPersonalTrainingSessions);
		buttonContainer.remove(btnViewAllTrainingSessions);
		revalidate();
		repaint();
    }
    
    private void viewPersonalTrainingSessions() {
		List<TrainingSessionDTO> trainingSessions = trainingSessionController.getPersonalTrainingSessions();
		trainingSessionsList.setListData(trainingSessions.toArray(new TrainingSessionDTO[0]));
		buttonContainer.remove(btnViewPersonalTrainingSessions);
		buttonContainer.add(btnViewAllTrainingSessions);
		revalidate();
		repaint();
    }
    
    private void createTrainingSession() {
        try {
            String title = txtTitle.getText();
            String startDate =txtStartDate.getText();
            Time startTime = Time.valueOf(txtStartTime.getText() + ":00");
            Duration duration = Duration.ofMinutes(Long.parseLong(txtDuration.getText()));
            double distance = Double.parseDouble(txtDistance.getText());

            if (trainingSessionController.createTrainingSession(title, startDate, startTime, duration, distance)) {
                JOptionPane.showMessageDialog(this, "Training session " + title + " created", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create training session.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setMainWindow(MainWindow mainWindow) {
    	this.mainWindow = mainWindow;
    }
}
