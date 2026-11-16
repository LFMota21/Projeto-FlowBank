
package br.com.flowbank.model;

import java.util.ArrayList;

public class Conta {

    private String titular;
    private double saldo;
    private String numeroDaConta;

    private ArrayList<Movimentacao> historico = new ArrayList<>();

    public Conta(String nome) {
        this.titular = nome;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
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

    public void depositar(double valor) {

        if (valor > 0) {
            this.saldo += valor;

            historico.add(
                    new Movimentacao("Deposito", valor)
            );

        } else {
            throw new IllegalArgumentException(
                    "O depósito deve ser maior que zero."
            );
        }
    }

    public void sacar(double valor) {

        if (valor <= 0) {
            throw new IllegalArgumentException(
                    "O saque deve ser maior que zero."
            );
        }

        if (valor > saldo) {
            throw new IllegalArgumentException(
                    "O valor nao esta disponivel na conta."
            );
        }

        this.saldo -= valor;

        historico.add(
                new Movimentacao("Saque", valor)
        );
    }

    public void mostrarExtrato() {

        for (Movimentacao movimentacao : historico) {

            System.out.println(
                    "Tipo: " + movimentacao.getTipo()
            );

            System.out.println(
                    "Valor: " + movimentacao.getValor()
            );
        }
    }
}
