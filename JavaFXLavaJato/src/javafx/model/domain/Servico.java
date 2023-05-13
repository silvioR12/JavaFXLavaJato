package javafx.model.domain;

import java.io.Serializable;

public class Servico implements Serializable {

    private int cdServico;
    private String tipoServico;
    private String descricao;
    private String tempoEstimado;
    private double valorAVista;
    private double valorAprazo;

    public Servico() {
    }

    /** Criando classe Servico com base nos valores do banco de dados
     *
     * @param cdServico    1
     * @param tipoServico  Lavagem ecológica
     * @param descricao    Lavagem feita com pano e aspirador
     * @param tempoEstimado 1h e 30 min
     * @param valorAVista   50
     * @param valorAprazo   80
     */
    
    
    public Servico(int cdServico, String tipoServico, String descricao, String tempoEstimado, double valorAVista, double valorAprazo) {
        this.cdServico = cdServico;
        this.tipoServico = tipoServico;
        this.descricao = descricao;
        this.tempoEstimado = tempoEstimado;
        this.valorAVista = valorAVista;
        this.valorAprazo = valorAprazo;
    }

    public int getCdServico() {
        return cdServico;
    }

    public void setCdServico(int cdServico) {
        this.cdServico = cdServico;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(String tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public double getValorAVista() {
        return valorAVista;
    }

    public void setValorAVista(double valorAVista) {
        this.valorAVista = valorAVista;
    }

    public double getValorAprazo() {
        return valorAprazo;
    }

    public void setValorAprazo(double valorAprazo) {
        this.valorAprazo = valorAprazo;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
}
