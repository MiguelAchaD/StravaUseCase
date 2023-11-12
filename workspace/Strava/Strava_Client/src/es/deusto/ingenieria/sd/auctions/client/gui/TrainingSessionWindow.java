package es.deusto.ingenieria.sd.auctions.client.gui;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.time.Duration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.deusto.ingenieria.sd.auctions.client.controller.TrainingSessionController;

public class TrainingSessionWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	private TrainingSessionController trainingSessionController;
    private JTextField txtTitle, txtStartDate, txtStartTime, txtDuration, txtDistance;
    private JButton btnCreateSession;

    public TrainingSessionWindow(TrainingSessionController trainingSessionController) {
        super("Create Training Session");
        this.trainingSessionController = trainingSessionController;

        txtTitle = new JTextField(20);
        txtStartDate = new JTextField(10);
        txtStartTime = new JTextField(5);
        txtDuration = new JTextField(5);
        txtDistance = new JTextField(5);
        btnCreateSession = new JButton("Create Session");

        btnCreateSession.addActionListener(e -> createTrainingSession());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Title:"));
        panel.add(txtTitle);
        panel.add(new JLabel("Start Date (yyyy-mm-dd):"));
        panel.add(txtStartDate);
        panel.add(new JLabel("Start Time (HH:MM):"));
        panel.add(txtStartTime);
        panel.add(new JLabel("Duration (minutes):"));
        panel.add(txtDuration);
        panel.add(new JLabel("Distance (km):"));
        panel.add(txtDistance);
        panel.add(btnCreateSession);

        this.setContentPane(panel);
        panel.setBackground(new Color(230, 230, 250));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	mainWindow.setVisible(true);
            }
        });
    }

    private void createTrainingSession() {
        try {
            String title = txtTitle.getText();
            String startDate =txtStartDate.getText();
            Time startTime = Time.valueOf(txtStartTime.getText() + ":00");
            Duration duration = Duration.ofMinutes(Long.parseLong(txtDuration.getText()));
            double distance = Double.parseDouble(txtDistance.getText());

            if (trainingSessionController.createTrainingSession(mainWindow.getToken(), title, startDate, startTime, duration, distance)) {
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
