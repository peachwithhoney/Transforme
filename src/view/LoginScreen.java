package view;

import java.awt.*;
import javax.swing.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        // Configurações da janela
        setTitle("Transforme+ - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null); 

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground( Color.WHITE);

        // Painel esquerdo para o logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setPreferredSize(new Dimension(500, 450)); 

        
        ImageIcon logoIcon = new ImageIcon("src/assets/Yellow and Green Modern Logo.png"); 
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        // Painel direito para o formulário de login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(39, 164, 242)); 
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); 

        // Título do painel de login
        JLabel titleLabel = new JLabel("Transforme+");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createVerticalStrut(20)); 

        // Campo de email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginPanel.add(emailLabel);
        JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(250, 30));
        loginPanel.add(emailField);
        loginPanel.add(Box.createVerticalStrut(10)); 

        // Campo de senha
        JLabel passwordLabel = new JLabel("Senha");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(250, 30));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(10)); 

        // Checkbox "Lembrar-me"
        JCheckBox rememberMe = new JCheckBox("Lembrar-me");
        rememberMe.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginPanel.add(rememberMe);
        loginPanel.add(Box.createVerticalStrut(10)); 

        // Link "Esqueci a senha"
        JLabel forgotPassword = new JLabel("Esqueci a senha");
        forgotPassword.setForeground(Color.BLUE);
        forgotPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginPanel.add(forgotPassword);
        loginPanel.add(Box.createVerticalStrut(20)); 

        // Botão "Entrar"
        JButton loginButton = new JButton("Entrar");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(0, 120, 215)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(250, 40));
        loginButton.setMaximumSize(new Dimension(250, 40));
        loginPanel.add(loginButton);

        // Rodapé
        JLabel footerLabel = new JLabel("Transforme+ - Todos os direitos reservados");
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(footerLabel);

        // Adiciona os painéis ao painel principal
        mainPanel.add(logoPanel, BorderLayout.WEST);
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}