package view.popups;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class PopupCadastrarUsuarios extends JDialog {

    private static final Color PRIMARY_COLOR = new Color(28, 95, 138);
    private static final Dimension FIELD_SIZE = new Dimension(300, 30);

    public PopupCadastrarUsuarios(JFrame parent) {
        super(parent, "Cadastrar Usuário", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true));

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
        popupPanel.setBackground(Color.WHITE);
        popupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addField(popupPanel, "Nome:");
        JTextField nomeField = new JTextField(20);
        nomeField.setMaximumSize(FIELD_SIZE);
        popupPanel.add(nomeField);
        popupPanel.add(Box.createVerticalStrut(10));

        addField(popupPanel, "Usuário:");
        JTextField usuarioField = new JTextField(20);
        usuarioField.setMaximumSize(FIELD_SIZE);
        popupPanel.add(usuarioField);
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
            String usuario = usuarioField.getText().trim();
            char[] senha = senhaField.getPassword();
            char[] confirmarSenha = confirmarSenhaField.getPassword();

            if (nome.isEmpty() || usuario.isEmpty() || senha.length == 0 || confirmarSenha.length == 0) {
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

        popupPanel.add(cadastrarButton);
        popupPanel.add(Box.createVerticalStrut(10));
        popupPanel.add(cancelarButton);

        add(popupPanel, BorderLayout.CENTER);
    }

    private void addField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
        return button;
    }
}