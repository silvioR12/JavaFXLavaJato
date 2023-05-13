
package javafx.model.domain;

import java.io.Serializable;

/*Classe cliente e seus atributos*/
/*Feito a criação dos atributos Nome,rg,telefone,cidade,email,datanascimentpo e genero*/

public class Cliente implements Serializable {

    private int cdCliente;
    private String nome;
    private String rg;
    private String telefone;
    private String cidade;
    private String email;
    private String dataNascimento;
    private String genero;

    public Cliente(){
    }
    
    public Cliente(int cdCliente, String nome, String rg, String telefone, String cidade, String email, String dataNascimento, String genero) {
        this.cdCliente = cdCliente;
        this.nome = nome;
        this.rg = rg;
        this.telefone = telefone;
        this.cidade = cidade;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public int getCdCliente() {
        return cdCliente;
    }

    public void setCdCliente(int cdCliente) {
        this.cdCliente = cdCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}
