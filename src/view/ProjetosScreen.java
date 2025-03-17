package view;

import DAO.ProjetoDAO;
import classes.Projeto;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import view.popups.PopupAlterarProjeto;
import view.popups.PopupCadastrarUsuarios;
import view.popups.PopupCadastroProjetos;
import view.popups.PopupDoacao;


public class ProjetosScreen extends JFrame {
    private final JPanel centerPanel;

    public ProjetosScreen() {
        setTitle("Transforme+ - Projetos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = criarHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel filtrosPanel = criarBalaoFiltros();
        centerPanel.add(filtrosPanel);
        centerPanel.add(Box.createVerticalStrut(20));

        JPanel projetosPanel = criarBalaoProjetos();
        centerPanel.add(projetosPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ImageIcon logoIcon = new ImageIcon("src/assets/LogoPequeno100x40.png");
        JLabel logoLabel = new JLabel(logoIcon);
        footerPanel.add(logoLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel criarHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(28, 95, 138));
        headerPanel.setPreferredSize(new Dimension(1000, 60));

        
        ImageIcon casaIcon = new ImageIcon("src/assets/LogoDaCasa40x40.png");
        JLabel casaLabel = new JLabel(casaIcon);
        casaLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(casaLabel, BorderLayout.WEST);

        
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setBackground(new Color(28, 95, 138));

        
        JLabel cadastroLabel = new JLabel("Cadastro");
        cadastroLabel.setForeground(Color.WHITE);
        cadastroLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPopupMenu cadastroMenu = new JPopupMenu();
        JMenuItem usuariosItem = new JMenuItem("Usuários");
        JMenuItem projetosItem = new JMenuItem("Projetos");

        
        usuariosItem.addActionListener((actionEvent) -> {
            PopupCadastrarUsuarios popupUsuarios = new PopupCadastrarUsuarios(this);
            popupUsuarios.setVisible(true);
        });

        
        projetosItem.addActionListener((actionEvent) -> {
            PopupCadastroProjetos popupProjetos = new PopupCadastroProjetos(this);
            popupProjetos.setVisible(true);
        });

        cadastroMenu.add(usuariosItem);
        cadastroMenu.add(projetosItem);

        cadastroLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cadastroMenu.show(cadastroLabel, 0, cadastroLabel.getHeight());
            }
        });

        
        JLabel projetosLabel = new JLabel("Projetos");
        projetosLabel.setForeground(Color.WHITE);
        projetosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        projetosLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        projetosLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                ProjetosScreen projetosScreen = new ProjetosScreen();
                projetosScreen.setVisible(true);
            }
        });

        
        JLabel usuariosLabel = new JLabel("Usuários");
        usuariosLabel.setForeground(Color.WHITE);
        usuariosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usuariosLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        usuariosLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); 
                UsuariosScreen usuariosScreen = new UsuariosScreen(); 
                usuariosScreen.setVisible(true);
            }
        });

        
        menuPanel.add(cadastroLabel);
        menuPanel.add(projetosLabel);
        menuPanel.add(usuariosLabel);
        JButton doacaoButton = criarBotaoDoacao();
        menuPanel.add(doacaoButton);

        headerPanel.add(menuPanel, BorderLayout.CENTER);

        ImageIcon usuarioIcon = new ImageIcon("src/assets/LogoDeUsuario40x40.png");
        JLabel usuarioLabel = new JLabel(usuarioIcon);
        usuarioLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPopupMenu logoutMenu = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            dispose();
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
        logoutMenu.add(logoutItem);

        usuarioLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logoutMenu.show(usuarioLabel, 0, usuarioLabel.getHeight());
            }
        });

        headerPanel.add(usuarioLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JButton criarBotaoDoacao() {
        JButton doacaoButton = new JButton("Doar");
        doacaoButton.setBackground(new Color(255, 87, 34)); // Cor destacada (laranja)
        doacaoButton.setForeground(Color.WHITE);
        doacaoButton.setFont(new Font("Arial", Font.BOLD, 14));
        doacaoButton.setFocusPainted(false);
        doacaoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        doacaoButton.setPreferredSize(new Dimension(100, 30));

        doacaoButton.addActionListener(e -> {
            PopupDoacao popupDoacao = new PopupDoacao(this);
            popupDoacao.setVisible(true);
        });

    return doacaoButton;
}


    private JPanel criarBalaoFiltros() {
        JPanel filtrosPanel = new JPanel();
        filtrosPanel.setLayout(new BoxLayout(filtrosPanel, BoxLayout.Y_AXIS));
        filtrosPanel.setBackground(new Color(240, 240, 240));
        filtrosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tituloLabel = new JLabel("Projetos");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(28, 95, 138));
        filtrosPanel.add(tituloLabel);
        filtrosPanel.add(Box.createVerticalStrut(10));

        JPanel camposPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        camposPanel.setBackground(new Color(240, 240, 240));

        JTextField nomeField = new JTextField(15);
        JTextField descricaoField = new JTextField(15);
        JTextField metaField = new JTextField(10);

        camposPanel.add(new JLabel("Nome:"));
        camposPanel.add(nomeField);
        camposPanel.add(new JLabel("Descrição:"));
        camposPanel.add(descricaoField);
        camposPanel.add(new JLabel("Meta R$:"));
        camposPanel.add(metaField);

        filtrosPanel.add(camposPanel);
        filtrosPanel.add(Box.createVerticalStrut(10));

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        botoesPanel.setBackground(new Color(240, 240, 240));

        JButton filtrarButton = new JButton("Filtrar");
        filtrarButton.addActionListener(e -> {
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            String meta = metaField.getText();

            List<Projeto> projetosFiltrados = ProjetoDAO.filtrarProjetos(nome, descricao, meta);
            atualizarListaProjetos(projetosFiltrados);
        });

        JButton limparButton = new JButton("Limpar Filtros");
        limparButton.addActionListener(e -> {
            nomeField.setText("");
            descricaoField.setText("");
            metaField.setText("");
            List<Projeto> projetos = ProjetoDAO.listaProjeto();
            atualizarListaProjetos(projetos);
        });

        botoesPanel.add(filtrarButton);
        botoesPanel.add(limparButton);

        filtrosPanel.add(botoesPanel);
        return filtrosPanel;
    }

    private JPanel criarBalaoProjetos() {
        JPanel projetosPanel = new JPanel();
        projetosPanel.setLayout(new BoxLayout(projetosPanel, BoxLayout.Y_AXIS));
        projetosPanel.setBackground(new Color(240, 240, 240));
        projetosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tituloLabel = new JLabel("Nome, Descrição e Meta Financeira");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(28, 95, 138));
        projetosPanel.add(tituloLabel);
        projetosPanel.add(Box.createVerticalStrut(10));

        List<Projeto> projetos = ProjetoDAO.listaProjeto();
        for (Projeto projeto : projetos) {
            projetosPanel.add(criarProjetoPanel(projeto));
            projetosPanel.add(Box.createVerticalStrut(10));
        }

        return projetosPanel;
    }

    private JPanel criarProjetoPanel(Projeto projeto) {
        JPanel projetoPanel = new JPanel(new BorderLayout());
        projetoPanel.setBackground(Color.WHITE);
        projetoPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
    
        JLabel nomeLabel = new JLabel(projeto.getNome());
        JLabel descricaoLabel = new JLabel(projeto.getDescricao());
    
        double meta = projeto.getMetaFinanceira().doubleValue();
        double arrecadado = projeto.getArrecadacao().doubleValue();
        JLabel metaLabel = new JLabel(String.format("Meta: R$ %.2f | Arrecadado: R$ %.2f", meta, arrecadado));
    
        // Cálculo da porcentagem arrecadada
        double percentual = (arrecadado / meta) * 100;
        Color corIndicador;
        if (percentual < 50) {
            corIndicador = Color.RED;
        } else if (percentual < 100) {
            corIndicador = Color.ORANGE;
        } else {
            corIndicador = Color.GREEN;
        }
    
        JLabel indicadorLabel = new JLabel("●");
        indicadorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        indicadorLabel.setForeground(corIndicador);
    
        JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        metaPanel.setBackground(Color.WHITE);
        metaPanel.add(metaLabel);
        metaPanel.add(indicadorLabel);
    
        infoPanel.add(nomeLabel);
        infoPanel.add(descricaoLabel);
        infoPanel.add(metaPanel);
    
        projetoPanel.add(infoPanel, BorderLayout.CENTER);
    
        JPanel acoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        acoesPanel.setBackground(Color.WHITE);
    
        JButton alterarButton = new JButton("Alterar");
        JButton excluirButton = new JButton("Excluir");
    
        alterarButton.addActionListener(e -> {
            PopupAlterarProjeto popupAlterarProjeto = new PopupAlterarProjeto(this, projeto);
            popupAlterarProjeto.setVisible(true);
        });
    
        excluirButton.addActionListener(e -> {
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o projeto " + projeto.getNome() + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );
    
            if (confirmacao == JOptionPane.YES_OPTION) {
                System.out.println("Tentando excluir projeto com ID: " + projeto.getId());
                ProjetoDAO.deletarProjeto(projeto.getId());
                atualizarListaProjetos(ProjetoDAO.listaProjeto());
                JOptionPane.showMessageDialog(this, "Projeto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    
        acoesPanel.add(alterarButton);
        acoesPanel.add(excluirButton);
        projetoPanel.add(acoesPanel, BorderLayout.EAST);
    
        return projetoPanel;
    }    

    public void atualizarListaProjetos(List<Projeto> projetos) {
        centerPanel.removeAll();

        JPanel filtrosPanel = criarBalaoFiltros();
        centerPanel.add(filtrosPanel);
        centerPanel.add(Box.createVerticalStrut(20));

        JPanel projetosPanel = new JPanel();
        projetosPanel.setLayout(new BoxLayout(projetosPanel, BoxLayout.Y_AXIS));
        projetosPanel.setBackground(new Color(240, 240, 240));
        projetosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Projeto projeto : projetos) {
            projetosPanel.add(criarProjetoPanel(projeto));
            projetosPanel.add(Box.createVerticalStrut(10));
        }

        centerPanel.add(projetosPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void redirecionarParaProjetosScreen() {
        dispose();
        ProjetosScreen projetosScreen = new ProjetosScreen();
        projetosScreen.setVisible(true);
    }
}