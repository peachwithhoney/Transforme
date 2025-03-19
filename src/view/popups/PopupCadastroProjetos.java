package view.popups;

import DAO.ProjetoDAO;
import classes.Projeto;
import exceptions.CampoInvalidoException;
import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import view.ProjetosScreen;

public class PopupCadastroProjetos extends JDialog {

    private ProjetosScreen projetosScreen;

    public PopupCadastroProjetos(JFrame parent) {
        super(parent, "Cadastrar Projeto", true); 
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(28, 95, 138), 2, true)); 

        
        if (parent instanceof ProjetosScreen screen) {
            this.projetosScreen = screen;
        }

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
        popupPanel.setBackground(Color.WHITE);
        popupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

       
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        popupPanel.add(nomeLabel);
        JTextField nomeField = new JTextField(20);
        nomeField.setMaximumSize(new Dimension(300, 30));
        popupPanel.add(nomeField);
        popupPanel.add(Box.createVerticalStrut(10)); // Espaçamento

        
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

        
        JLabel metaLabel = new JLabel("Meta Financeira:");
        metaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        popupPanel.add(metaLabel);
        JTextField metaField = new JTextField(20);
        metaField.setMaximumSize(new Dimension(300, 30));
        popupPanel.add(metaField);
        popupPanel.add(Box.createVerticalStrut(20));

       
        JButton salvarButton = new JButton("Salvar");
        salvarButton.setBackground(new Color(28, 95, 138));
        salvarButton.setForeground(Color.WHITE);
        salvarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        salvarButton.setFocusPainted(false);
        salvarButton.setPreferredSize(new Dimension(100, 30));

        
        salvarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String descricao = descricaoField.getText().trim();
            String metaStr = metaField.getText().trim();

            try {
                
                if (nome.isEmpty()) {
                    throw new CampoInvalidoException("O campo 'Nome' não pode estar vazio!");
                }
                if (descricao.isEmpty()) {
                    throw new CampoInvalidoException("O campo 'Descrição' não pode estar vazio!");
                }
                if (metaStr.isEmpty()) {
                    throw new CampoInvalidoException("O campo 'Meta Financeira' não pode estar vazio!");
                }

                
                BigDecimal metaFinanceira = new BigDecimal(metaStr);

                
                if (metaFinanceira.compareTo(BigDecimal.ZERO) < 0) {
                    throw new CampoInvalidoException("A meta financeira não pode ser negativa!");
                }

                
                Projeto projeto = new Projeto();
                projeto.setNome(nome);
                projeto.setDescricao(descricao);
                projeto.setMetaFinanceira(metaFinanceira);
                projeto.setArrecadacao(BigDecimal.ZERO); 

                
                ProjetoDAO.inserirProjeto(projeto);

                
                JOptionPane.showMessageDialog(this, "Projeto salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                
                dispose();

                
                if (projetosScreen != null) {
                    projetosScreen.atualizarListaProjetos(ProjetoDAO.listaProjeto());
                }
            } catch (CampoInvalidoException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        popupPanel.add(salvarButton);

        add(popupPanel, BorderLayout.CENTER);
    }
}