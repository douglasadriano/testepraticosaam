package testepraticosaam;

import javax.swing.SwingUtilities;
import testepraticosaam.view.TelaLoginCadastro;

public class TestePraticoSAAM {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLoginCadastro login = new TelaLoginCadastro();
            login.setVisible(true);
        });
    }
}
