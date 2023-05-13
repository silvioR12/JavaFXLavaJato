package javafx.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Cliente;
import java.sql.*;


public class ClienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }
    

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
/*Tentativa número 1 */


    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome, rg, telefone, cidade, email, data_nascimento, genero) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getRg());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getDataNascimento());
            stmt.setString(7, cliente.getGenero());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome=?, rg=?, telefone=?, cidade=?, email=?, data_nascimento=?, genero=? WHERE cdCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getRg());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getDataNascimento());
            stmt.setString(7, cliente.getGenero());
            stmt.setInt(8, cliente.getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    
        /*Problema para remover cliente. Pois , existe uma chave estrangeira que aponta
    de veicu*/
   public boolean remover(Cliente cliente) {
    String sql = "DELETE FROM clientes WHERE cdCliente=?";
    try {
        // Verifica se o cliente tem registros relacionados na outra tabela
        String consulta = "SELECT COUNT(*) FROM clientes WHERE cdCliente=?";
        PreparedStatement stmtConsulta = connection.prepareStatement(consulta);
        stmtConsulta.setInt(1, cliente.getCdCliente());
        ResultSet resultado = stmtConsulta.executeQuery();
        if (resultado.next() && resultado.getInt(1) > 0) {
            // Se o cliente tiver registros relacionados, exclui os registros antes de excluir o cliente
            String sql2 = "DELETE FROM veiculos WHERE cdCliente=?";
            PreparedStatement stmtsql2 = connection.prepareStatement(sql2);
            stmtsql2.setInt(1, cliente.getCdCliente());
            stmtsql2.executeUpdate();
        }
        
        // Exclui o cliente da tabela
        PreparedStatement stmtClientes = connection.prepareStatement(sql);
        stmtClientes.setInt(1, cliente.getCdCliente());
        stmtClientes.executeUpdate();
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}


    public List<Cliente> listar() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setCdCliente(resultado.getInt("cdCliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setRg(resultado.getString("rg"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setCidade(resultado.getString("cidade"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setDataNascimento(resultado.getString("data_nascimento"));
                cliente.setGenero(resultado.getString("genero"));
                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM clientes WHERE cdCliente=?";
        Cliente retorno = new Cliente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getCdCliente());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                cliente.setNome(resultado.getString("nome"));
                cliente.setRg(resultado.getString("rg"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setCidade(resultado.getString("cidade"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setDataNascimento(resultado.getString("data_nascimento"));
                cliente.setGenero(resultado.getString("genero"));
                retorno = cliente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
