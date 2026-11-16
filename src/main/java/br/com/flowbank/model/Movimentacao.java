
package br.com.flowbank.model;

import java.math.BigDecimal;

public class Movimentacao {

    private String tipo;
    private BigDecimal valor;

    public Movimentacao(String tipo, BigDecimal valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
