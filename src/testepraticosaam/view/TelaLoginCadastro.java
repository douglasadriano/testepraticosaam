package testepraticosaam.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import testepraticosaam.dao.UsuarioDAO;
import testepraticosaam.model.Usuario;
import testepraticosaam.util.CriptografiaUtil;
import testepraticosaam.util.MensagemUtil;
import testepraticosaam.view.components.RoundedPasswordField;
import testepraticosaam.view.components.RoundedTextField;

public class TelaLoginCadastro extends JFrame {

    private CardLayout layout;
    private JPanel painelPrincipal;
    private JPanel painelLogin;
    private JPanel painelCadastro;

    private RoundedTextField txtEmailLogin;
    private RoundedPasswordField txtSenhaLogin;

    private RoundedTextField txtNomeCadastro;
    private RoundedTextField txtEmailCadastro;
    private RoundedPasswordField txtSenhaCadastro;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public TelaLoginCadastro() {
        setTitle("Sistema de Acesso");
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        layout = new CardLayout();
        painelPrincipal = new JPanel(layout);

        criarPainelLogin();
        criarPainelCadastro();

        painelPrincipal.add(painelLogin, "login");
        painelPrincipal.add(painelCadastro, "cadastro");

        add(painelPrincipal);
        layout.show(painelPrincipal, "login");
    }

    private JPanel criarPainelBase() {
        JPanel painel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(224, 242, 255);
                Color c2 = new Color(179, 217, 255);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        return painel;
    }

    private void criarPainelLogin() {
        painelLogin = criarPainelBase();

        JLabel lblTitulo = new JLabel("Bem-vindo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(0, 102, 204));

        txtEmailLogin = new RoundedTextField("E-mail");
        txtEmailLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        txtSenhaLogin = new RoundedPasswordField("Senha");
        txtSenhaLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton btnLogin = estilizarBotao("Entrar");
        btnLogin.addActionListener(e -> fazerLogin());

        // Pressionar Enter para acionar o botão Entrar
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        painelLogin.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "loginEnter");
        painelLogin.getActionMap().put("loginEnter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin.doClick();
            }
        });

        JButton btnIrCadastro = new JButton("Não tem conta? Cadastre-se");
        estilizarLink(btnIrCadastro);
        btnIrCadastro.addActionListener(e -> layout.show(painelPrincipal, "cadastro"));

        painelLogin.add(lblTitulo);
        painelLogin.add(Box.createVerticalStrut(20));
        painelLogin.add(txtEmailLogin);
        painelLogin.add(Box.createVerticalStrut(15));
        painelLogin.add(txtSenhaLogin);
        painelLogin.add(Box.createVerticalStrut(25));
        painelLogin.add(btnLogin);
        painelLogin.add(Box.createVerticalStrut(15));
        painelLogin.add(btnIrCadastro);
    }

    private void criarPainelCadastro() {
        painelCadastro = criarPainelBase();

        JLabel lblTitulo = new JLabel("Criar Conta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(0, 102, 204));

        txtNomeCadastro = new RoundedTextField("Nome Completo");
        txtNomeCadastro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        txtEmailCadastro = new RoundedTextField("E-mail");
        txtEmailCadastro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        txtSenhaCadastro = new RoundedPasswordField("Senha");
        txtSenhaCadastro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton btnCadastrar = estilizarBotao("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarUsuario());

        JButton btnIrLogin = new JButton("Já tem conta? Faça login");
        estilizarLink(btnIrLogin);
        btnIrLogin.addActionListener(e -> layout.show(painelPrincipal, "login"));

        painelCadastro.add(lblTitulo);
        painelCadastro.add(Box.createVerticalStrut(20));
        painelCadastro.add(txtNomeCadastro);
        painelCadastro.add(Box.createVerticalStrut(15));
        painelCadastro.add(txtEmailCadastro);
        painelCadastro.add(Box.createVerticalStrut(15));
        painelCadastro.add(txtSenhaCadastro);
        painelCadastro.add(Box.createVerticalStrut(25));
        painelCadastro.add(btnCadastrar);
        painelCadastro.add(Box.createVerticalStrut(15));
        painelCadastro.add(btnIrLogin);
    }

    private JButton estilizarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setBackground(new Color(0, 153, 255));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void estilizarLink(JButton botao) {
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setForeground(new Color(0, 102, 204));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void fazerLogin() {
        String email = txtEmailLogin.getText().trim();
        String senha = new String(txtSenhaLogin.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            MensagemUtil.atencao(this, "Preencha todos os campos.");
            return;
        }

        Usuario usuario = usuarioDAO.login(email, CriptografiaUtil.sha256(senha));
        if (usuario != null) {
            dispose(); // Fecha tela de login
            new TelaPrincipal(usuario.getNome()).setVisible(true);
        } else {
            MensagemUtil.erro(this, "E-mail ou senha inválidos.");
        }
    }

    private void cadastrarUsuario() {
        String nome = txtNomeCadastro.getText().trim();
        String email = txtEmailCadastro.getText().trim();
        String senha = new String(txtSenhaCadastro.getPassword()).trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            MensagemUtil.atencao(this, "Todos os campos são obrigatórios.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            MensagemUtil.erro(this, "E-mail inválido.");
            return;
        }

        Usuario usuario = new Usuario(nome, email, CriptografiaUtil.sha256(senha));
        if (usuarioDAO.salvar(usuario)) {
            MensagemUtil.info(this, "Usuário cadastrado com sucesso!");
            layout.show(painelPrincipal, "login");
            limparCamposCadastro();
        } else {
            MensagemUtil.erro(this, "Erro ao cadastrar. Usuário já existe!");
        }
    }

    private void limparCamposCadastro() {
        txtNomeCadastro.setText("");
        txtEmailCadastro.setText("");
        txtSenhaCadastro.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLoginCadastro().setVisible(true));
    }
}