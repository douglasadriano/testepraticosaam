package testepraticosaam.view.components;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import javax.swing.text.DefaultFormatterFactory;

public class RoundedFormattedTextField extends JFormattedTextField {
    private String placeholder;

    public RoundedFormattedTextField(String mascara, String placeholder) {
        super();
        this.placeholder = placeholder;
        setOpaque(false);
        setBorder(null);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Color.DARK_GRAY);
        setCaretColor(Color.BLACK);
        configurarMascaramentoDinamico(mascara);
    }

    private void configurarMascaramentoDinamico(String mascara) {
        try {
            MaskFormatter formatter = new MaskFormatter(mascara);
            formatter.setPlaceholderCharacter(' '); // espaço
            formatter.setValueContainsLiteralCharacters(true);
            DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
            setFormatterFactory(factory);

            // remove a máscara visual inicial
            setFocusLostBehavior(JFormattedTextField.PERSIST);
            setText(""); // limpa a visualização inicial
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

        if (getText().trim().isEmpty() && !isFocusOwner()) {
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
}