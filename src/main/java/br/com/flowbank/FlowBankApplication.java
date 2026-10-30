package br.com.flowbank;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class FlowBankApplication {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame janela = new JFrame("FlowBank");

            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel mensagem = new JLabel(
                "Estrutura inicial do FlowBank",
                SwingConstants.CENTER
            );

            janela.add(mensagem);

            janela.setSize(900, 600);
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
        });
    }
}