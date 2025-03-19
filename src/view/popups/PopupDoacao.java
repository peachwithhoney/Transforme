package view.popups;

import DAO.DoacaoDAO;
import DAO.ProjetoDAO;
import classes.Doacao;
import classes.Projeto;
import classes.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.ProjetoNaoEncontradoException;
import exceptions.UsuarioNaoEncontradoException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class PopupDoacao extends JDialog {
    private final JComboBox<Projeto> comboProjetos;
    private final JTextField valorField;
    private final JButton confirmarButton, cancelarButton;
    private final Runnable onDoacaoSuccess; 

    public PopupDoacao(Frame parent, Runnable onDoacaoSuccess) {
        super(parent, "Registrar Doação", true);
        this.onDoacaoSuccess = onDoacaoSuccess; 
        
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Selecione o projeto:"));
        comboProjetos = new JComboBox<>();
        carregarProjetos();
        panel.add(comboProjetos);

        panel.add(new JLabel("Valor da doação:"));
        valorField = new JTextField();
        panel.add(valorField);

        JPanel buttonPanel = new JPanel();
        confirmarButton = new JButton("Confirmar");
        cancelarButton = new JButton("Cancelar");

        buttonPanel.add(confirmarButton);
        buttonPanel.add(cancelarButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        cancelarButton.addActionListener(e -> dispose());
        confirmarButton.addActionListener(this::registrarDoacao);
    }

    private void carregarProjetos() {
        List<Projeto> projetos = ProjetoDAO.listaProjeto();
        if (projetos != null) {
            for (Projeto projeto : projetos) {
                comboProjetos.addItem(projeto);
            }
        }
    }

    private void registrarDoacao(ActionEvent e) {
        Projeto projetoSelecionado = (Projeto) comboProjetos.getSelectedItem();
        if (projetoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um projeto!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuarioLogado = Usuario.getUsuarioLogado();
        if (usuarioLogado == null) {
            JOptionPane.showMessageDialog(this, "É necessário estar logado para fazer uma doação!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BigDecimal valor = new BigDecimal(valorField.getText().trim());
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal restante = projetoSelecionado.getMetaFinanceira().subtract(projetoSelecionado.getArrecadacao());

            if (valor.compareTo(restante) > 0) {
                JOptionPane.showMessageDialog(this, "A doação excede o valor necessário!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Doacao novaDoacao = new Doacao(0, valor.doubleValue(), new Date(), usuarioLogado.getId(), projetoSelecionado.getId());
            DoacaoDAO.registrarDoacao(novaDoacao);

            JOptionPane.showMessageDialog(this, "Doação registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            projetoSelecionado.setArrecadacao(projetoSelecionado.getArrecadacao().add(valor));
        
            if (onDoacaoSuccess != null) {
                onDoacaoSuccess.run();
            }
        
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (CampoObrigatorioException | UsuarioNaoEncontradoException | ProjetoNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}