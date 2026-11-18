
package br.com.flowbank.model;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conta {

    private String titular;
    private BigDecimal saldo;
    private String numeroDaConta;

    private ArrayList<Movimentacao> historico = new ArrayList<>();


    public Conta(String nome) {
        validarTitular(nome);
        this.saldo = new BigDecimal("0.00");
    }


    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        validarTitular(titular);
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(String numeroDaConta) {
        validarNumeroDaConta(numeroDaConta);
    }

    public ArrayList<Movimentacao> getHistorico() {
        return new ArrayList<>(historico);
    }


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

    private void validarTitular (String titular){
        if (titular == null) {  
            throw new IllegalArgumentException(
                "O nome do titular é obrigatório.");
        } 

         if (titular.isBlank()){
             throw new IllegalArgumentException(
                "O nome do titular é obrigatório.");
         }
    
           this.titular = titular.trim();
    }

    private void validarNumeroDaConta (String numeroDaConta){
        if (numeroDaConta == null){
              throw new IllegalArgumentException(
                "O número da conta é obrigatório.");
        }

        if (numeroDaConta.isBlank()){
               throw new IllegalArgumentException(
                "O número da conta é obrigatório.");
        }

        this.numeroDaConta = numeroDaConta.trim();
    }


    public void depositar(BigDecimal valor) {

        valor = validarValor(valor);

        this.saldo = this.saldo.add(valor);

        historico.add(
            new Movimentacao("Depósito", valor, null)
        );
    }


    public void sacar(BigDecimal valor) {

        valor = validarValor(valor);

        if (valor.compareTo(saldo) > 0) {
            throw new IllegalArgumentException(
                "Saldo insuficiente para realizar o saque."
            );
        }

        this.saldo = this.saldo.subtract(valor);

        historico.add(
           new Movimentacao("Saque", valor, null)
        );
    }


  public void mostrarExtrato() {

    if (historico.isEmpty()) {
        System.out.println("Nenhuma movimentação encontrada.");
        return;
    }

    for (Movimentacao movimentacao : historico) {

        System.out.println("Tipo: " + movimentacao.getTipo());
        System.out.println("Valor: R$ " + movimentacao.getValor());
        System.out.println("Data e hora: " + movimentacao.getDataHora());

        if (movimentacao.getContaRelacionada() != null) {
            System.out.println(
                "Conta relacionada: " + movimentacao.getContaRelacionada()
            );
        }

        System.out.println("-------------------------");
    }
}

    public void transferir(Conta destino, BigDecimal valor) {

        valor = validarValor(valor);

        if (destino == null) {
            throw new IllegalArgumentException(
                "A conta de destino é inválida."
            );
        }

        if (this == destino) {
            throw new IllegalArgumentException(
                "Não é possível transferir para a própria conta."
            );
        }

        if (valor.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException(
                "Saldo insuficiente para realizar a transferência."
            );
        }

        this.saldo = this.saldo.subtract(valor);

        destino.saldo = destino.saldo.add(valor);

        this.historico.add(
            new Movimentacao(
            "Transferência enviada",
             valor, 
             destino.getNumeroDaConta())
        );

        destino.historico.add(
           new Movimentacao(
            "Transferência recebida", 
            valor,
           this.getNumeroDaConta())
        );
    }
}
