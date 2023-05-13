package javafx.model.domain;

import java.io.Serializable;

public class Veiculo implements Serializable {

    private int cdVeiculo;
    private String tipoVeiculo;
    private String placa;
    private String modelo;
    private String marca;
    private String categoria;
    private Cliente cliente;


    public Veiculo() {
    }

    public Veiculo(int cdVeiculo, String tipoVeiculo, String placa, String modelo, String marca, String categoria) {
        this.cdVeiculo = cdVeiculo;
        this.tipoVeiculo = tipoVeiculo;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.categoria = categoria;
    }

    public int getCdVeiculo() {
        return cdVeiculo;
    }

    public void setCdVeiculo(int cdVeiculo) {
        this.cdVeiculo = cdVeiculo;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public String toString() {
        return this.placa;
    }
    
}
