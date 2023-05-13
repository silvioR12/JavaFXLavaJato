package javafx.model.domain;

import java.io.Serializable;
import java.time.LocalDate;


public class Agendamento implements Serializable {

    private int cdAgendamento;
    private Cliente cliente;
    private Veiculo veiculo;
    private Servico servico;
    private double valor;
    private LocalDate data_agendamento;
    private String horario;


    public Agendamento() {
    }

    public Agendamento(int cdAgendamento, Cliente cliente, Veiculo veiculo, Servico servico, double valor, LocalDate data_agendamento, String horario) {
        this.cdAgendamento = cdAgendamento;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.servico = servico;
        this.valor = valor;
        this.data_agendamento = data_agendamento;
        this.horario = horario;
    }

    public int getCdAgendamento() {
        return cdAgendamento;
    }

    public void setCdAgendamento(int cdAgendamento) {
        this.cdAgendamento = cdAgendamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData_agendamento() {
        return data_agendamento;
    }

    public void setData_agendamento(LocalDate data_agendamento) {
        this.data_agendamento = data_agendamento;
    }
   

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    
    @Override
    public String toString() {
        return String.valueOf(data_agendamento);
    }
 
}