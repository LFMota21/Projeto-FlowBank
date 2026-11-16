
package br.com.flowbank.model;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conta {

    private String titular;
    private BigDecimal saldo;
    private String numeroDaConta;

    private ArrayList<Movimentacao> historico = new ArrayList<>();

    // CONSTRUTOR

    public Conta(String nome) {
        this.titular = nome;
        this.saldo = new BigDecimal("0.00");
    }

    // GETTERS E SETTERS

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(String numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public ArrayList<Movimentacao> getHistorico() {
        return new ArrayList<>(historico);
    }

    // VALIDAÇÃO DOS VALORES MONETÁRIOS

    private BigDecimal validarValor(BigDecimal valor) {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "O valor da operação deve ser maior que zero."
            );
        }

        try {
            return valor.setScale(2, RoundingMode.UNNECESSARY);

        } catch (ArithmeticException erro) {
            throw new IllegalArgumentException(
                "O valor deve ter no máximo duas casas decimais."
            );
        }
    }

    // DEPÓSITO

    public void depositar(BigDecimal valor) {

        valor = validarValor(valor);

        this.saldo = this.saldo.add(valor);

        historico.add(
            new Movimentacao("Depósito", valor)
        );
    }

    // SAQUE

    public void sacar(BigDecimal valor) {

        valor = validarValor(valor);

        if (valor.compareTo(saldo) > 0) {
            throw new IllegalArgumentException(
                "Saldo insuficiente para realizar o saque."
            );
        }

        this.saldo = this.saldo.subtract(valor);

        historico.add(
            new Movimentacao("Saque", valor)
        );
    }

    // EXTRATO

    public void mostrarExtrato() {

        if (historico.isEmpty()) {
            System.out.println("Nenhuma movimentação encontrada.");
            return;
        }

        for (Movimentacao movimentacao : historico) {

            System.out.println(
                "Tipo: " + movimentacao.getTipo()
            );

            System.out.println(
                "Valor: R$ " + movimentacao.getValor()
            );

            System.out.println("-------------------------");
        }
    }

    // TRANSFERÊNCIA ENTRE CONTAS

    public void transferir(Conta destino, BigDecimal valor) {

        valor = validarValor(valor);

        // Verifica se a conta de destino existe.
        if (destino == null) {
            throw new IllegalArgumentException(
                "A conta de destino é inválida."
            );
        }

        // Impede transferência para a própria conta.
        if (this == destino) {
            throw new IllegalArgumentException(
                "Não é possível transferir para a própria conta."
            );
        }

        // Verifica se o saldo é suficiente.
        if (valor.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException(
                "Saldo insuficiente para realizar a transferência."
            );
        }

        // Retira o dinheiro da conta de origem.
        this.saldo = this.saldo.subtract(valor);

        // Adiciona o dinheiro à conta de destino.
        destino.saldo = destino.saldo.add(valor);

        // Registra a transferência enviada.
        this.historico.add(
            new Movimentacao("Transferência enviada", valor)
        );

        // Registra a transferência recebida.
        destino.historico.add(
            new Movimentacao("Transferência recebida", valor)
        );
    }
}
