/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testepraticosaam;

import javax.swing.SwingUtilities;
import testepraticosaam.view.TelaLoginCadastro;

public class TestePraticoSisaudicon {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLoginCadastro login = new TelaLoginCadastro();
            login.setVisible(true);
        });
    }
}
