package testepraticosaam.view.components;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RoundedDateField extends JTextField {
    private String placeholder;

    public RoundedDateField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setBorder(null);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Color.DARK_GRAY);
        setCaretColor(Color.BLACK);

        setDocument(new DateDocument());
        addKeyListener(new DateAutoFormatter());
    }

    @Override
    public Insets getInsets() {
        return new Insets(10, 20, 10, 20);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(240, 248, 255));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
        g2.dispose();

        if (getText().isEmpty() && !isFocusOwner()) {
            g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets insets = getInsets();
            g2.drawString(placeholder, insets.left, getHeight() / 2 + 5);
            g2.dispose();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        g2.dispose();
    }

    // Aceita apenas números e barra, limitado a 10 caracteres
    private static class DateDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            String current = getText(0, getLength());
            if ((current + str).length() > 10) return;
            if (str.matches("[0-9/]*")) {
                super.insertString(offs, str, a);
            }
        }
    }

    // Formata automaticamente com barras (dd/MM/yyyy)
    private static class DateAutoFormatter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            JTextField tf = (JTextField) e.getSource();
            String text = tf.getText().replaceAll("[^0-9]", "");

            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                if (i == 2 || i == 4) {
                    formatted.append('/');
                }
                formatted.append(text.charAt(i));
            }
            tf.setText(formatted.toString());
        }
    }
}