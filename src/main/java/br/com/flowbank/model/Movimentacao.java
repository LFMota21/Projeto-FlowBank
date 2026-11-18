
package br.com.flowbank.model;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Movimentacao {

    private LocalDateTime dataHora;
    private String contaRelacionada;
    private String tipo;
    private BigDecimal valor;

    public Movimentacao(String tipo, BigDecimal valor, String contaRelacionada) {
        this.tipo = tipo;
        this.valor = valor;
        this.contaRelacionada = contaRelacionada;
        this.dataHora = LocalDateTime.now();
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }
    public LocalDateTime getDataHora() {
    return dataHora;
}

public String getContaRelacionada() {
    return contaRelacionada;
}
}
