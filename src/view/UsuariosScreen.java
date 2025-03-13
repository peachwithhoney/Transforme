package view;

import DAO.UsuarioDAO;
import classes.Usuario;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
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

        
        add(mainPanel);
    }

    // Header
    private JPanel criarHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(28, 95, 138));
        headerPanel.setPreferredSize(new Dimension(1000, 60));

        // Ícone de casa no canto esquerdo
        ImageIcon casaIcon = new ImageIcon("caminho/para/casa_icone.png");
        JLabel casaLabel = new JLabel(casaIcon);
        casaLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(casaLabel, BorderLayout.WEST);

        // Texto "Cadastro" e "Projetos" no centro
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setBackground(new Color(28, 95, 138));

        // Link para "Cadastro" 
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

        // Link para "Projetos" 
        JLabel projetosLabel = new JLabel("Projetos");
        projetosLabel.setForeground(Color.WHITE);
        projetosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        projetosLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        projetosLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                redirecionarParaProjetosScreen();
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
            redirecionarParaLoginScreen();
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

    // Método para criar o balão de filtros
    private JPanel criarBalaoFiltros() {
        JPanel filtrosPanel = new JPanel();
        filtrosPanel.setBackground(new Color(240, 240, 240));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        filtrosPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Campo de filtro por nome
        JTextField filtroNomeField = new JTextField(20);
        filtroNomeField.setToolTipText("Filtrar por nome");
        filtrosPanel.add(new JLabel("Nome:"));
        filtrosPanel.add(filtroNomeField);

        // Campo de filtro por email
        JTextField filtroEmailField = new JTextField(20);
        filtroEmailField.setToolTipText("Filtrar por email");
        filtrosPanel.add(new JLabel("Email:"));
        filtrosPanel.add(filtroEmailField);

        // Botão de aplicar filtro
        JButton aplicarFiltroButton = new JButton("Aplicar Filtro");
        aplicarFiltroButton.addActionListener(e -> {
            String nome = filtroNomeField.getText();
            String email = filtroEmailField.getText();
            List<Usuario> usuariosFiltrados = UsuarioDAO.filtrarUsuarios(nome, email);
            atualizarListaUsuarios(usuariosFiltrados);
        });
        filtrosPanel.add(aplicarFiltroButton);

        return filtrosPanel;
    }

    // Método para criar o balão de lista de usuários
    private JPanel criarBalaoUsuarios() {
        JPanel usuariosPanel = new JPanel();
        usuariosPanel.setBackground(new Color(240, 240, 240));
        usuariosPanel.setBorder(BorderFactory.createTitledBorder("Lista de Usuários"));
        usuariosPanel.setLayout(new BoxLayout(usuariosPanel, BoxLayout.Y_AXIS));

        // Busca a lista de usuários do banco de dados
        List<Usuario> usuarios = UsuarioDAO.listarUsuarios();

        // Cria a lista de usuários
        DefaultListModel<String> listaUsuariosModel = new DefaultListModel<>();
        for (Usuario usuario : usuarios) {
            listaUsuariosModel.addElement(usuario.getNome() + " - " + usuario.getEmail());
        }

        JList<String> listaUsuarios = new JList<>(listaUsuariosModel);
        listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaUsuarios.setBackground(Color.WHITE);

        
        JScrollPane scrollPane = new JScrollPane(listaUsuarios);
        usuariosPanel.add(scrollPane);

        return usuariosPanel;
    }

    // Atualizar a lista de usuários na interface
    private void atualizarListaUsuarios(List<Usuario> usuarios) {
        DefaultListModel<String> listaUsuariosModel = new DefaultListModel<>();
        for (Usuario usuario : usuarios) {
            listaUsuariosModel.addElement(usuario.getNome() + " - " + usuario.getEmail());
        }

        JList<String> listaUsuarios = new JList<>(listaUsuariosModel);
        listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaUsuarios.setBackground(Color.WHITE);

        // Atualiza o painel de usuários
        JPanel usuariosPanel = (JPanel) getContentPane().getComponent(1);
        usuariosPanel.removeAll();
        usuariosPanel.add(new JScrollPane(listaUsuarios));
        usuariosPanel.revalidate();
        usuariosPanel.repaint();
    }

    // Redirecionar para a tela de projetos
    public void redirecionarParaProjetosScreen() {
        dispose(); 
        ProjetosScreen projetosScreen = new ProjetosScreen();
        projetosScreen.setVisible(true);
    }

    
    public void redirecionarParaLoginScreen() {
        dispose(); 
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
    }
}