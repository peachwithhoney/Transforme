package view.popups;

import DAO.ProjetoDAO;
import classes.Projeto;
import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import view.ProjetosScreen;
import java.util.Objects;

public class PopupAlterarProjeto extends JDialog {
    private static final Color PRIMARY_COLOR = new Color(28, 95, 138);
    private static final Dimension FIELD_SIZE = new Dimension(300, 30);

    private ProjetosScreen projetosScreen;
    private final JTextField nomeField;
    private final JTextArea descricaoField;
    private final JTextField metaField;
    private final Projeto projeto;

    public PopupAlterarProjeto(JFrame parent, Projeto projeto) {
        super(parent, "Alterar Projeto", true);
        this.projeto = projeto;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (parent instanceof ProjetosScreen p) {
            this.projetosScreen = p;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        addField(panel, "Nome:");
        nomeField = new JTextField(projeto.getNome(), 20);
        nomeField.setMaximumSize(FIELD_SIZE);
        panel.add(nomeField);
        panel.add(Box.createVerticalStrut(10));

        addField(panel, "Descrição:");
        descricaoField = new JTextArea(projeto.getDescricao(), 3, 20);
        descricaoField.setLineWrap(true);
        descricaoField.setWrapStyleWord(true);
        JScrollPane descricaoScroll = new JScrollPane(descricaoField);
        descricaoScroll.setMaximumSize(new Dimension(300, 80));
        panel.add(descricaoScroll);
        panel.add(Box.createVerticalStrut(10));

        addField(panel, "Meta Financeira:");
        metaField = new JTextField(projeto.getMetaFinanceira().toString(), 20);
        metaField.setMaximumSize(FIELD_SIZE);
        panel.add(metaField);
        panel.add(Box.createVerticalStrut(20));

        JButton alterarButton = createButton("Alterar", PRIMARY_COLOR);
        JButton cancelarButton = createButton("Cancelar", Color.LIGHT_GRAY);

        alterarButton.addActionListener(e -> atualizarProjeto());
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

    private void atualizarProjeto() {
        String nome = nomeField.getText().trim();
        String descricao = descricaoField.getText().trim();
        String metaStr = metaField.getText().trim();

        if (nome.isEmpty() || descricao.isEmpty() || metaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BigDecimal metaFinanceira = new BigDecimal(metaStr);
            
            if (Objects.equals(projeto.getNome(), nome) &&
                Objects.equals(projeto.getDescricao(), descricao) &&
                projeto.getMetaFinanceira().compareTo(metaFinanceira) == 0) {
                JOptionPane.showMessageDialog(this, "Nenhuma alteração foi feita!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente alterar os dados do projeto?",
                "Confirmar Alteração",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                projeto.setNome(nome);
                projeto.setDescricao(descricao);
                projeto.setMetaFinanceira(metaFinanceira);

                ProjetoDAO.atualizarProjeto(projeto);

                JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();

                if (projetosScreen != null) {
                    projetosScreen.atualizarListaProjetos(ProjetoDAO.listaProjeto());
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Meta financeira inválida! Use números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar projeto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}