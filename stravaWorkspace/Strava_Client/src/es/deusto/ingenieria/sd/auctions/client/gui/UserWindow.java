package es.deusto.ingenieria.sd.auctions.client.gui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

import es.deusto.ingenieria.sd.auctions.client.controller.UserController;

@SuppressWarnings("serial")
public class UserWindow extends JFrame {
    private MainWindow mainWindow;
    private UserController userController;
    private JTextField txtEmail, txtPassword, txtName, txtBirthDate, txtWeight, txtHeight, txtRestingHeartRate, txtMaxHeartRate;
    private JButton btnLogin, btnRegister;
    private JPanel contentPane;
    private boolean isLoginMode;

    public UserWindow(UserController userController) {
        super("Login/Register");
        this.userController = userController;
        isLoginMode = true;

        initComponents();
        configureLayout();
    }

    private void initComponents() {
        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtName = new JTextField(20);
        txtBirthDate = new JTextField(20);
        txtWeight = new JTextField(20);
        txtHeight = new JTextField(20);
        txtRestingHeartRate = new JTextField(20);
        txtMaxHeartRate = new JTextField(20);

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> register());
    }

    private void configureLayout() {
        contentPane = new JPanel(new GridLayout(0, 2, 10, 10));
        contentPane.setBackground(new Color(230, 230, 250));
        updateLayout();

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updateLayout() {
        contentPane.removeAll();

        contentPane.add(new JLabel("Email:"));
        contentPane.add(txtEmail);
        contentPane.add(new JLabel("Password:"));
        contentPane.add(txtPassword);

        if (!isLoginMode) {
            contentPane.add(new JLabel("Name:"));
            contentPane.add(txtName);
            contentPane.add(new JLabel("Birth Date (yyyy-mm-dd):"));
            contentPane.add(txtBirthDate);
            contentPane.add(new JLabel("Weight (kg):"));
            contentPane.add(txtWeight);
            contentPane.add(new JLabel("Height (cm):"));
            contentPane.add(txtHeight);
            contentPane.add(new JLabel("Resting Heart Rate:"));
            contentPane.add(txtRestingHeartRate);
            contentPane.add(new JLabel("Max Heart Rate:"));
            contentPane.add(txtMaxHeartRate);
        }

        contentPane.add(btnLogin);
        contentPane.add(btnRegister);

        revalidate();
        repaint();
        pack();
    }

    private void login() {
        if (!isLoginMode) {
            isLoginMode = true;
            updateLayout();
        } else {
        	String email = txtEmail.getText();
        	String password = txtPassword.getText();
        	if (userController.login(email, password)) {
        	    setVisible(false);
        	    if (mainWindow != null) {
        	        mainWindow.setVisible(true);
        	    }
        	} else {
        	    JOptionPane.showMessageDialog(this, "Login failed.", "Error", JOptionPane.ERROR_MESSAGE);
        	}

        }
    }

    private void register() {
        if (isLoginMode) {
            isLoginMode = false;
            updateLayout();
        } else {
        	String email = txtEmail.getText();
        	String password = txtPassword.getText();
        	String name = txtName.getText();
        	String birthDate = txtBirthDate.getText();
        	double weight = Double.parseDouble(txtWeight.getText());
        	double height = Double.parseDouble(txtHeight.getText());
        	int restingHeartRate = Integer.parseInt(txtRestingHeartRate.getText());
        	int maxHeartRate = Integer.parseInt(txtMaxHeartRate.getText());
        	if (!userController.register(email, birthDate, weight, height, restingHeartRate, maxHeartRate, name, password)) {
        		JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
    }

    public void logout() {
        userController.logout();
        setVisible(true);
        if (mainWindow != null) {
            mainWindow.setVisible(false);
        }
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
}