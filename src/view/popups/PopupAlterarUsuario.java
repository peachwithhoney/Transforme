package view.popups;

import DAO.*;
import classes.Usuario;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import view.UsuariosScreen;



public class PopupAlterarUsuario extends JDialog {

    private static final Color PRIMARY_COLOR = new Color(28, 95, 138);
    private static final Dimension FIELD_SIZE = new Dimension(300, 30);

    private UsuariosScreen usuariosScreen;
    private final JTextField nomeField;
    private final JTextField emailField;
    private final JPasswordField senhaField;
    private final JPasswordField confirmarSenhaField;
    private final Usuario usuario;

    public PopupAlterarUsuario(JFrame parent, Usuario usuario) {
        super(parent, "Alterar Usuário", true);
        this.usuario = usuario;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (parent instanceof UsuariosScreen u) {
            this.usuariosScreen = u;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        addField(panel, "Nome:");
        nomeField = new JTextField(usuario.getNome(), 20);
        nomeField.setMaximumSize(FIELD_SIZE);
        panel.add(nomeField);
        panel.add(Box.createVerticalStrut(10));

        addField(panel, "Email:");
        emailField = new JTextField(usuario.getEmail(), 20);
        emailField.setMaximumSize(FIELD_SIZE);
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));

        addField(panel, "Nova Senha:");
        senhaField = new JPasswordField(20);
        senhaField.setMaximumSize(FIELD_SIZE);
        panel.add(senhaField);
        panel.add(Box.createVerticalStrut(10));

        addField(panel, "Confirmar Senha:");
        confirmarSenhaField = new JPasswordField(20);
        confirmarSenhaField.setMaximumSize(FIELD_SIZE);
        panel.add(confirmarSenhaField);
        panel.add(Box.createVerticalStrut(20));

        JButton alterarButton = createButton("Alterar", PRIMARY_COLOR);
        JButton cancelarButton = createButton("Cancelar", Color.LIGHT_GRAY);

        alterarButton.addActionListener(e -> atualizarUsuario());
        cancelarButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(alterarButton);
        buttonPanel.add(cancelarButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        panel.add(jLabel);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void atualizarUsuario() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        char[] senha = senhaField.getPassword();
        char[] confirmarSenha = confirmarSenhaField.getPassword();

        if (nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e email são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (senha.length > 0 && !Arrays.equals(senha, confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        if (senha.length > 0) {
            usuario.setSenha(new String(senha));
        }

        UsuarioDAO.atualizarUsuario(usuario);

        JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();

        if (usuariosScreen != null) {
            usuariosScreen.atualizarListaUsuarios(UsuarioDAO.listarUsuarios());
        }
    }
}
