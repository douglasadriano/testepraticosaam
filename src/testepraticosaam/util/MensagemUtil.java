package testepraticosaam.util;


import java.awt.Component;
import javax.swing.JOptionPane;

public class MensagemUtil {

    public static void info(Component parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void erro(Component parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void atencao(Component parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean confirmar(Component parent, String mensagem) {
        int opcao = JOptionPane.showConfirmDialog(parent, mensagem, "Confirmação", JOptionPane.YES_NO_OPTION);
        return opcao == JOptionPane.YES_OPTION;
    }
}