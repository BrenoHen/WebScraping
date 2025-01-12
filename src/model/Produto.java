package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Produto {
    private String nomeDoTitulo;
    private String rentabilidadeAnual;
    private Double investimentoMinimo;
    private Double precoUnitario;
    private Date vencimento;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Produto(){};

    public Produto(String nomeDoTitulo, String rentabilidadeAnual, Double investimentoMinimo, Double precoUnitario, Date vencimento) {
        this.nomeDoTitulo = nomeDoTitulo;
        this.rentabilidadeAnual = rentabilidadeAnual;
        this.investimentoMinimo = investimentoMinimo;
        this.precoUnitario = precoUnitario;
        this.vencimento = vencimento;
    }

    public String getNomeDoTitulo(String dadosNomeTitulo) {


        return nomeDoTitulo;
    }

    public void setNomeDoTitulo(String nomeDoTitulo) {
        this.nomeDoTitulo = nomeDoTitulo;
    }

    public String getRentabilidadeAnual() {
        return rentabilidadeAnual;
    }

    public void setRentabilidadeAnual(String rentabilidadeAnual) {
        this.rentabilidadeAnual = rentabilidadeAnual;
    }

    public Double getInvestimentoMinimo() {
        return investimentoMinimo;
    }

    public void setInvestimentoMinimo(Double investimentoMinimo) {
        this.investimentoMinimo = investimentoMinimo;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nomeDoTitulo='" + nomeDoTitulo + '\'' +
                ", rentabilidadeAnual='" + rentabilidadeAnual + '\'' +
                ", investimentoMinimo=" + investimentoMinimo +
                ", precoUnitario=" + precoUnitario +
                ", vencimento=" + sdf.format(vencimento) +
                '}';
    }
}
