
package testepraticosaam.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(String nomeUsuario) {
        setTitle("Sistema - Tela Principal");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel lblBemVindo = new JLabel("Bem-vindo, " + nomeUsuario + "!", JLabel.CENTER);
        lblBemVindo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBemVindo.setForeground(new Color(0, 102, 204));

        JButton btnFuncionarios = new JButton("Funcionários");
        btnFuncionarios.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnFuncionarios.setBackground(new Color(0, 153, 255));
        btnFuncionarios.setForeground(Color.WHITE);
        btnFuncionarios.setFocusPainted(false);
        btnFuncionarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFuncionarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFuncionarios.setMaximumSize(new Dimension(200, 50));

        btnFuncionarios.addActionListener(e -> new TelaFuncionario().setVisible(true));

        painel.add(lblBemVindo);
        painel.add(Box.createVerticalStrut(50));
        painel.add(btnFuncionarios);

        add(painel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal("Usuário Teste").setVisible(true));
    }
}