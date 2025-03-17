package view.popups;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.*;

public class PopupCadastrarUsuarios extends JDialog {

    private static final Color PRIMARY_COLOR = new Color(28, 95, 138);
    private static final Dimension FIELD_SIZE = new Dimension(300, 30);

    public PopupCadastrarUsuarios(JFrame parent) {
        super(parent, "Cadastrar Usuário", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        addField(panel, "Nome:");
        JTextField nomeField = new JTextField(20);
        nomeField.setMaximumSize(FIELD_SIZE);
        panel.add(nomeField);
        panel.add(Box.createVerticalStrut(10));

        addField(popupPanel, "Email:");
        JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(FIELD_SIZE);
        popupPanel.add(emailField);
        popupPanel.add(Box.createVerticalStrut(10));

        addField(popupPanel, "Senha:");
        JPasswordField senhaField = new JPasswordField(20);
        senhaField.setMaximumSize(FIELD_SIZE);
        popupPanel.add(senhaField);
        popupPanel.add(Box.createVerticalStrut(10));

        addField(popupPanel, "Confirmar Senha:");
        JPasswordField confirmarSenhaField = new JPasswordField(20);
        confirmarSenhaField.setMaximumSize(FIELD_SIZE);
        popupPanel.add(confirmarSenhaField);
        popupPanel.add(Box.createVerticalStrut(20));

        JButton cadastrarButton = createButton("Cadastrar", PRIMARY_COLOR);
        JButton cancelarButton = createButton("Cancelar", Color.LIGHT_GRAY);

        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = nomeField.getText().trim();
            char[] senha = senhaField.getPassword();
            char[] confirmarSenha = confirmarSenhaField.getPassword();

            if (nome.isEmpty() || email.isEmpty() || senha.length == 0) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!Arrays.equals(senha, confirmarSenha)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        cancelarButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(cancelarButton);

        add(popupPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        JTextField field = new JTextField(20);
        field.setMaximumSize(FIELD_SIZE);
        panel.add(jLabel);
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        return button;
    }
}
