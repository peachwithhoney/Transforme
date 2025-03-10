package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import view.popups.PopupCadastroProjetos;

public class UsuariosScreen extends JFrame {

    public UsuariosScreen() {
        // Configurações da janela
        setTitle("Transforme+ - Usuários");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null); // Centraliza a janela na tela

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
            PopupCadastroProjetos popup = new PopupCadastroProjetos(this);
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

    // Método para criar o balão de filtros
    private JPanel criarBalaoFiltros() {
        JPanel filtrosPanel = new JPanel();
        filtrosPanel.setLayout(new BoxLayout(filtrosPanel, BoxLayout.Y_AXIS));
        filtrosPanel.setBackground(new Color(240, 240, 240));
        filtrosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        // Título "Usuários"
        JLabel tituloLabel = new JLabel("Usuários");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(28, 95, 138)); 
        tituloLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        filtrosPanel.add(tituloLabel);
        filtrosPanel.add(Box.createVerticalStrut(10));

        // Campos de filtro
        JPanel camposPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        camposPanel.setBackground(new Color(240, 240, 240)); 
        JTextField nomeField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        camposPanel.add(new JLabel("Nome:"));
        camposPanel.add(nomeField);
        camposPanel.add(new JLabel("Email:"));
        camposPanel.add(emailField);

        filtrosPanel.add(camposPanel);
        filtrosPanel.add(Box.createVerticalStrut(10)); 

        // Botões "Filtrar" e "Limpar Filtros"
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        botoesPanel.setBackground(new Color(240, 240, 240)); 

        JButton filtrarButton = new JButton("Filtrar");
        filtrarButton.setBackground(new Color(39, 164, 242)); 
        filtrarButton.setForeground(Color.WHITE);
        filtrarButton.setFocusPainted(false);

        JButton limparButton = new JButton("Limpar Filtros");
        limparButton.setBackground(Color.GRAY);
        limparButton.setForeground(Color.WHITE);
        limparButton.setFocusPainted(false);

        botoesPanel.add(filtrarButton);
        botoesPanel.add(limparButton);

        filtrosPanel.add(botoesPanel);

        return filtrosPanel;
    }

    // Método para criar o balão de usuários
    private JPanel criarBalaoUsuarios() {
        JPanel usuariosPanel = new JPanel();
        usuariosPanel.setLayout(new BoxLayout(usuariosPanel, BoxLayout.Y_AXIS));
        usuariosPanel.setBackground(new Color(240, 240, 240)); 
        usuariosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        // Título "Nome e Email"
        JLabel tituloLabel = new JLabel("Nome e Email");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(28, 95, 138)); 
        tituloLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usuariosPanel.add(tituloLabel);
        usuariosPanel.add(Box.createVerticalStrut(10)); 

        // Lista de usuários
        usuariosPanel.add(criarUsuarioPanel("Nome 1", "email1@example.com"));
        usuariosPanel.add(Box.createVerticalStrut(10)); 
        usuariosPanel.add(criarUsuarioPanel("Nome 2", "email2@example.com"));

        return usuariosPanel;
    }

    // Método para criar um painel de usuário
    private JPanel criarUsuarioPanel(String nome, String email) {
        JPanel usuarioPanel = new JPanel(new BorderLayout());
        usuarioPanel.setBackground(Color.WHITE);
        usuarioPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Painel esquerdo para nome e email
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel nomeLabel = new JLabel(nome);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(nomeLabel);

        JLabel emailLabel = new JLabel(email);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoPanel.add(emailLabel);

        usuarioPanel.add(infoPanel, BorderLayout.CENTER);

        // Painel direito para botões de ação
        JPanel acoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        acoesPanel.setBackground(Color.WHITE);

        JButton alterarButton = new JButton("Alterar");
        alterarButton.setBackground(new Color(39, 164, 242)); 
        alterarButton.setForeground(Color.WHITE);
        alterarButton.setFocusPainted(false);

        JButton excluirButton = new JButton("Excluir");
        excluirButton.setBackground(Color.RED);
        excluirButton.setForeground(Color.WHITE);
        excluirButton.setFocusPainted(false);

        acoesPanel.add(alterarButton);
        acoesPanel.add(excluirButton);

        usuarioPanel.add(acoesPanel, BorderLayout.EAST);

        return usuarioPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuariosScreen usuariosScreen = new UsuariosScreen();
            usuariosScreen.setVisible(true);
        });
    }
}