package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import DAO.*;
import classes.*;
import view.popups.PopupCadastrarUsuarios;

public class UsuariosScreen extends JFrame {

    public UsuariosScreen() {
        // Configurações da janela
        setTitle("Transforme+ - Usuários");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = criarHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel central com os balões
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Primeiro balão: Filtros
        JPanel filtrosPanel = criarBalaoFiltros();
        centerPanel.add(filtrosPanel);
        centerPanel.add(Box.createVerticalStrut(20));

        // Segundo balão: Lista de usuários
        JPanel usuariosPanel = criarBalaoUsuarios();
        centerPanel.add(usuariosPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Rodapé com o logo
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon logoIcon = new ImageIcon("caminho/para/logo_pequeno.png");
        JLabel logoLabel = new JLabel(logoIcon);
        footerPanel.add(logoLabel);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
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

        projetosItem.addActionListener((actionEvent) -> {
            PopupCadastrarUsuarios popup = new PopupCadastrarUsuarios(this);
            popup.setVisible(true);
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
        logoutItem.addActionListener(e -> {
            // Fecha a tela de usuários
            dispose();
            // Abre a tela de login
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
        logoutMenu.add(logoutItem);

        usuarioLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               