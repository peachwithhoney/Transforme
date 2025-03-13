package view.popups;

import DAO.ProjetoDAO;
import classes.Projeto;
import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import view.ProjetosScreen;

public class PopupCadastroProjetos extends JDialog {

    private ProjetosScreen projetosScreen; 

    public PopupCadastroProjetos(ProjetosScreen parent) {
        super(parent, "Cadastrar Projeto", true); 
        this.projetosScreen = parent; 
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(28, 95, 138), 2, true)); 

        // Painel principal do popup
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
        popupPanel.setBackground(Color.WHITE);
        popupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        // Campo "Nome"
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        popupPanel.add(nomeLabel);
        JTextField nomeField = new JTextField(20);
        nomeField.setMaximumSize(new Dimension(300, 30));
        popupPanel.add(nomeField);
        popupPanel.add(Box.createVerticalStrut(10)); 

        // Campo "Descrição"
        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        popupPanel.add(descricaoLabel);
        JTextArea descricaoField = new JTextArea(3, 20);
        descricaoField.setLineWrap(true);
        descricaoField.setWrapStyleWord(true);
        JScrollPane descricaoScroll = new JScrollPane(descricaoField);
        descricaoScroll.setMaximumSize(new Dimension(300, 80));
        popupPanel.add(descricaoScroll);
        popupPanel.add(Box.createVerticalStrut(10));

        // Campo "Meta Financeira"
        JLabel metaLabel = new JLabel("Meta Financeira:");
        metaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        popupPanel.add(metaLabel);
        JTextField metaField = new JTextField(20);
        metaField.setMaximumSize(new Dimension(300, 30));
        popupPanel.add(metaField);
        popupPanel.add(Box.createVerticalStrut(20));

        // Botão "Salvar"
        JButton salvarButton = new JButton("Salvar");
        salvarButton.setBackground(new Color(28, 95, 138));
        salvarButton.setForeground(Color.WHITE);
        salvarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        salvarButton.setFocusPainted(false);
        salvarButton.setPreferredSize(new Dimension(100, 30));

        // Ação do botão "Salvar"
        salvarButton.addActionListener(e -> {
            // Validação dos campos
            String nome = nomeField.getText().trim();
            String descricao = descricaoField.getText().trim();
            String metaStr = metaField.getText().trim();

            if (nome.isEmpty() || descricao.isEmpty() || metaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Converte a meta financeira para BigDecimal
                BigDecimal metaFinanceira = new BigDecimal(metaStr);

                // Criar um novo projeto
                Projeto projeto = new Projeto();
                projeto.setNome(nome);
                projeto.setDescricao(descricao);
                projeto.setMetaFinanceira(metaFinanceira);
                projeto.setArrecadacao(BigDecimal.ZERO); 

                // Insere o projeto no banco de dados
                ProjetoDAO.inserirProjeto(projeto);

                // Mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Projeto salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                
                dispose();

                // Atualiza a lista de projetos na tela de projetos
                projetosScreen.atualizarListaProjetos(ProjetoDAO.listaProjeto());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Meta financeira inválida! Use números.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar projeto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        popupPanel.add(salvarButton);

        add(popupPanel, BorderLayout.CENTER);
    }
}