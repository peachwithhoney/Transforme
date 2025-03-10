package view;

import java.awt.*; 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import view.popups.PopupCadastrarUsuarios; 
import view.popups.PopupCadastroProjetos; 

public class ProjetosScreen extends JFrame {

    public ProjetosScreen() {
        setTitle("Transforme+ - Projetos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = criarHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
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
        ImageIcon logoIcon = new ImageIcon("caminho/para/logo_pequeno.png");
        JLabel logoLabel = new JLabel(logoIcon);
        footerPanel.add(logoLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Método para criar o header
    private JPanel criarHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(28, 95, 138)); 
        headerPanel.setPreferredSize(new Dimension(1000, 60)); 

        // Ícone de casa no canto esquerdo
        ImageIcon casaIcon = new ImageIcon("caminho/para/casa_icone.png"); 
        JLabel casaLabel = new JLabel(casaIcon);
        casaLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        headerPanel.add(casaLabel, BorderLayout.WEST);

        // Texto "Cadastro" e "Projetos" no centro com dropdown
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setBackground(new Color(28, 95, 138)); 

        // Dropdown para "Cadastro"
        JLabel cadastroLabel = new JLabel("Cadastro");
        cadastroLabel.setForeground(Color.WHITE);
        cadastroLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPopupMenu cadastroMenu = new JPopupMenu();
        JMenuItem usuariosItem = new JMenuItem("Usuários");
        JMenuItem projetosItem = new JMenuItem("Projetos");

        // Abrir popup de cadastro de usuários ao clicar em "Usuários"
        usuariosItem.addActionListener((actionEvent) -> {
            PopupCadastrarUsuarios popupUsuarios = new PopupCadastrarUsuarios(this);
            popupUsuarios.setVisible(true);
        });

        // Abrir popup de cadastro de projetos ao clicar em "Projetos"
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

        // Dropdown para "Projetos"
        JLabel projetosLabel = new JLabel("Projetos");
        projetosLabel.setForeground(Color.WHITE);
        projetosLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPopupMenu projetosMenu = new JPopupMenu();
        JMenuItem projeto1Item = new JMenuItem("Projeto 1");
        JMenuItem projeto2Item = new JMenuItem("Projeto 2");
        projetosMenu.add(projeto1Item);
        projetosMenu.add(projeto2Item);

        projetosLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                projetosMenu.show(projetosLabel, 0, projetosLabel.getHeight());
            }
        });

        menuPanel.add(cadastroLabel);
        menuPanel.add(projetosLabel);
        headerPanel.add(menuPanel, BorderLayout.CENTER);

        // Ícone de usuário no canto direito com dropdown para logout
        ImageIcon usuarioIcon = new ImageIcon("caminho/para/usuario_icone.png"); 
        JLabel usuarioLabel = new JLabel(usuarioIcon);
        usuarioLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 

        JPopupMenu logoutMenu = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Logout");
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
        JButton limparButton = new JButton("Limpar Filtros");
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

        projetosPanel.add(criarProjetoPanel("Projeto 1", "Descrição 1", "5000"));
        projetosPanel.add(Box.createVerticalStrut(10));
        projetosPanel.add(criarProjetoPanel("Projeto 2", "Descrição 2", "12000"));

        return projetosPanel;
    }

    private JPanel criarProjetoPanel(String nome, String descricao, String meta) {
        JPanel projetoPanel = new JPanel(new BorderLayout());
        projetoPanel.setBackground(Color.WHITE);
        projetoPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(new JLabel(nome));
        infoPanel.add(new JLabel(descricao));
        infoPanel.add(new JLabel("Meta: R$ " + meta));
        projetoPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel acoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        acoesPanel.setBackground(Color.WHITE);
        acoesPanel.add(new JButton("Alterar"));
        acoesPanel.add(new JButton("Excluir"));
        projetoPanel.add(acoesPanel, BorderLayout.EAST);

        return projetoPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProjetosScreen projetosScreen = new ProjetosScreen();
            projetosScreen.setVisible(true);
        });
    }
}