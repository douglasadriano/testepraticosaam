package testepraticosaam.view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import testepraticosaam.dao.FuncionarioDAO;
import testepraticosaam.model.Funcionario;
import testepraticosaam.util.MensagemUtil;
import testepraticosaam.util.TabelaFuncionarioUtil;
import testepraticosaam.view.components.RoundedDateField;
import testepraticosaam.view.components.RoundedTextField;

public class TelaFuncionario extends JFrame {

    private RoundedTextField txtNome;
    private RoundedDateField txtDataAdmissao;
    private RoundedTextField txtSalario;
    private JCheckBox chkStatus;
    private JTextField txtBusca;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;
    private JComboBox<String> filtroStatus;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar, btnExportarPDF, btnExportarCSV;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private int funcionarioSelecionadoId = -1;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public TelaFuncionario() {
        setTitle("Cadastro de Funcionários");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

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
        painel.setLayout(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        painel.add(criarFormulario(), BorderLayout.NORTH);
        painel.add(criarTabela(), BorderLayout.CENTER);
        painel.add(criarFiltroBuscaExport(), BorderLayout.SOUTH);

        add(painel);
        carregarFuncionariosNaTabela();
    }

    private JPanel criarFormulario() {
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setOpaque(false);

        txtNome = new RoundedTextField("Nome");
        txtDataAdmissao = new RoundedDateField("Data Admissão");
        txtSalario = new RoundedTextField("Salário");
        chkStatus = new JCheckBox("Ativo");
        chkStatus.setOpaque(false);

        btnSalvar = criarBotao("Salvar");
        btnEditar = criarBotao("Editar");
        btnExcluir = criarBotao("Excluir");
        btnLimpar = criarBotao("Limpar");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        form.add(txtNome);
        form.add(txtDataAdmissao);
        form.add(txtSalario);
        form.add(chkStatus);
        form.add(new JLabel());
        form.add(painelBotoes);

        btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());

        return form;
    }

    private JPanel criarFiltroBuscaExport() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);

        JPanel esquerda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        esquerda.setOpaque(false);
        txtBusca = new JTextField(20);
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBusca.setToolTipText("Buscar por nome...");
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltroBusca(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltroBusca(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltroBusca(); }
        });
        esquerda.add(new JLabel("Buscar:"));
        esquerda.add(txtBusca);

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        direita.setOpaque(false);
        filtroStatus = new JComboBox<>(new String[]{"Todos", "Ativos", "Inativos"});
        filtroStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filtroStatus.addActionListener(e -> carregarFuncionariosNaTabela());

        btnExportarPDF = criarBotao("Exportar PDF");
        btnExportarCSV = criarBotao("Exportar CSV");

        btnExportarPDF.addActionListener(e -> TabelaFuncionarioUtil.exportarParaPDF(tabela, modeloTabela));
        btnExportarCSV.addActionListener(e -> TabelaFuncionarioUtil.exportarParaCSV(tabela, modeloTabela));

        direita.add(new JLabel("Status:"));
        direita.add(filtroStatus);
        direita.add(btnExportarPDF);
        direita.add(btnExportarCSV);

        painel.add(esquerda, BorderLayout.WEST);
        painel.add(direita, BorderLayout.EAST);
        return painel;
    }

    private JScrollPane criarTabela() {
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Admissão", "Salário", "Status"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        sorter = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(sorter);
        tabela.setRowHeight(25);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabela.getSelectedRow();
                if (row >= 0) {
                    int modelRow = tabela.convertRowIndexToModel(row);
                    funcionarioSelecionadoId = (int) modeloTabela.getValueAt(modelRow, 0);
                    txtNome.setText((String) modeloTabela.getValueAt(modelRow, 1));
                    txtDataAdmissao.setText((String) modeloTabela.getValueAt(modelRow, 2));
                    txtSalario.setText(String.valueOf(modeloTabela.getValueAt(modelRow, 3)));
                    chkStatus.setSelected("Ativo".equals(modeloTabela.getValueAt(modelRow, 4)));
                }
            }
        });

        return new JScrollPane(tabela);
    }

    private void aplicarFiltroBusca() {
        String texto = txtBusca.getText();
        if (sorter != null) {
            sorter.setRowFilter(texto.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + texto, 1));
        }
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setBackground(new Color(0, 153, 255));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void carregarFuncionariosNaTabela() {
        String statusSelecionado = filtroStatus != null ? filtroStatus.getSelectedItem().toString() : "Todos";
        TabelaFuncionarioUtil.carregarFuncionarios(funcionarioDAO.listar(), modeloTabela, statusSelecionado);
        if (sorter != null) {
            sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        }
    }

    private void salvar() {
        try {
            Funcionario f = new Funcionario();
            f.setNome(txtNome.getText().trim());
            f.setDataAdmissao(sdf.parse(txtDataAdmissao.getText().trim()));
            f.setSalario(Double.parseDouble(txtSalario.getText().trim().replace(",", ".")));
            f.setStatus(chkStatus.isSelected());

            if (funcionarioDAO.salvar(f)) {
                MensagemUtil.info(this, "Funcionário cadastrado!");
                carregarFuncionariosNaTabela();
                limpar();
            }
        } catch (Exception ex) {
            MensagemUtil.erro(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void editar() {
        if (funcionarioSelecionadoId == -1) {
            MensagemUtil.atencao(this, "Selecione um funcionário.");
            return;
        }

        try {
            Funcionario f = new Funcionario();
            f.setId(funcionarioSelecionadoId);
            f.setNome(txtNome.getText().trim());
            f.setDataAdmissao(sdf.parse(txtDataAdmissao.getText().trim()));
            f.setSalario(Double.parseDouble(txtSalario.getText().trim().replace(",", ".")));
            f.setStatus(chkStatus.isSelected());

            if (funcionarioDAO.atualizar(f)) {
                MensagemUtil.info(this, "Funcionário atualizado!");
                carregarFuncionariosNaTabela();
                limpar();
            }
        } catch (Exception ex) {
            MensagemUtil.erro(this, "Erro ao editar: " + ex.getMessage());
        }
    }

    private void excluir() {
        if (funcionarioSelecionadoId == -1) {
            MensagemUtil.atencao(this, "Selecione um funcionário.");
            return;
        }

        if (MensagemUtil.confirmar(this, "Deseja excluir este funcionário?")) {
            if (funcionarioDAO.deletar(funcionarioSelecionadoId)) {
                MensagemUtil.info(this, "Funcionário excluído.");
                carregarFuncionariosNaTabela();
                limpar();
            }
        }
    }

    private void limpar() {
        txtNome.setText("");
        txtDataAdmissao.setText("");
        txtSalario.setText("");
        chkStatus.setSelected(false);
        funcionarioSelecionadoId = -1;
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaFuncionario().setVisible(true));
    }
}
