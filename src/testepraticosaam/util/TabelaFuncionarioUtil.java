package testepraticosaam.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import testepraticosaam.model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class TabelaFuncionarioUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void carregarFuncionarios(List<Funcionario> funcionarios, DefaultTableModel modelo, String filtroStatus) {
        modelo.setRowCount(0);

        for (Funcionario f : funcionarios) {
            if ("Ativos".equals(filtroStatus) && !f.isStatus()) continue;
            if ("Inativos".equals(filtroStatus) && f.isStatus()) continue;

            modelo.addRow(new Object[]{
                f.getId(),
                f.getNome(),
                sdf.format(f.getDataAdmissao()),
                String.format("%.2f", f.getSalario()),
                f.isStatus() ? "Ativo" : "Inativo"
            });
        }
    }

    public static void exportarParaCSV(JTable tabela, DefaultTableModel modelo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como CSV");
        if (fileChooser.showSaveDialog(tabela) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getAbsolutePath().toLowerCase().endsWith(".csv")) {
                    file = new File(file.getAbsolutePath() + ".csv");
                }

                FileWriter writer = new FileWriter(file, false);

                // Cabeçalho
                for (int i = 0; i < tabela.getColumnCount(); i++) {
                    writer.write(tabela.getColumnName(i));
                    if (i < tabela.getColumnCount() - 1) writer.write(";");
                }
                writer.write(System.lineSeparator());

                // Linhas visíveis
                for (int row = 0; row < tabela.getRowCount(); row++) {
                    int modelRow = tabela.convertRowIndexToModel(row);
                    for (int col = 0; col < tabela.getColumnCount(); col++) {
                        Object value = modelo.getValueAt(modelRow, col);
                        writer.write((value != null ? value.toString() : ""));
                        if (col < tabela.getColumnCount() - 1) writer.write(";");
                    }
                    writer.write(System.lineSeparator());
                }

                writer.flush();
                writer.close();
                MensagemUtil.info(tabela, "Exportado com sucesso: " + file.getName());

            } catch (Exception e) {
                MensagemUtil.erro(tabela, "Erro ao exportar CSV: " + e.getMessage());
            }
        }
    }

    public static void exportarParaPDF(JTable tabela, DefaultTableModel modelo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como PDF");
        if (fileChooser.showSaveDialog(tabela) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getAbsolutePath().toLowerCase().endsWith(".pdf")) {
                    file = new File(file.getAbsolutePath() + ".pdf");
                }

                Document doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream(file));
                doc.open();

                PdfPTable pdfTable = new PdfPTable(tabela.getColumnCount());
                pdfTable.setWidthPercentage(100);

                for (int i = 0; i < tabela.getColumnCount(); i++) {
                    PdfPCell cell = new PdfPCell(new Phrase(tabela.getColumnName(i)));
                    cell.setBackgroundColor(Color.LIGHT_GRAY);
                    pdfTable.addCell(cell);
                }

                for (int row = 0; row < tabela.getRowCount(); row++) {
                    int modelRow = tabela.convertRowIndexToModel(row);
                    for (int col = 0; col < tabela.getColumnCount(); col++) {
                        Object value = modelo.getValueAt(modelRow, col);
                        pdfTable.addCell(value != null ? value.toString() : "");
                    }
                }

                doc.add(new Paragraph("Funcionários", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
                doc.add(new Paragraph(" "));
                doc.add(pdfTable);
                doc.close();

                MensagemUtil.info(tabela, "Exportado com sucesso: " + file.getName());

            } catch (Exception e) {
                MensagemUtil.erro(tabela, "Erro ao exportar PDF: " + e.getMessage());
            }
        }
    }
}
