/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.model.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Agendamento;
import javafx.model.domain.Cliente;
import javafx.model.domain.Servico;
import javafx.model.domain.Veiculo;


public class AgendamentoDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
     public boolean inserir(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos(cdCliente, cdVeiculo, cdServico, data_agendamento, hora_agendamento, valor_total) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, agendamento.getCliente().getCdCliente());
            stmt.setInt(2, agendamento.getVeiculo().getCdVeiculo());
            stmt.setInt(3, agendamento.getServico().getCdServico());
            stmt.setDate(4, Date.valueOf(agendamento.getData_agendamento()));
            stmt.setString(5, agendamento.getHorario());
            stmt.setDouble(6, agendamento.getValor());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
     public boolean alterar(Agendamento agendamento) {
        String sql = "UPDATE agendamentos SET cdCliente=?, cdVeiculo=?, cdServico=?, data_agendamento=?, hora_agendamento=?, valor_total=? WHERE cdAgendamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agendamento.getCliente().getCdCliente());
            stmt.setInt(2, agendamento.getVeiculo().getCdVeiculo());
            stmt.setInt(3, agendamento.getServico().getCdServico());
            stmt.setDate(4, Date.valueOf(agendamento.getData_agendamento()));
            stmt.setString(5,agendamento.getHorario());
            stmt.setDouble(6, agendamento.getValor());
            stmt.setInt(7, agendamento.getCdAgendamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
     public boolean remover(Agendamento agendamento) {
        String sql = "DELETE FROM agendamentos WHERE cdAgendamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agendamento.getCdAgendamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
        
     /*Método de listar*/
    public List<Agendamento> listar() {
    String sql = "SELECT * FROM agendamentos";
    List<Agendamento> retorno = new ArrayList<>();
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Agendamento agendamento = new Agendamento();
            Cliente cliente = new Cliente();
            Veiculo veiculo = new Veiculo();
            Servico servico = new Servico();
            agendamento.setCdAgendamento(resultado.getInt("cdAgendamento"));
            cliente.setCdCliente(resultado.getInt("cdCliente"));
            veiculo.setCdVeiculo(resultado.getInt("cdVeiculo"));
            servico.setCdServico(resultado.getInt("cdServico"));
            agendamento.setData_agendamento(resultado.getDate("data_agendamento").toLocalDate());
            agendamento.setHorario((resultado.getString("hora_agendamento")));
            agendamento.setValor(Double.parseDouble(resultado.getString("valor_total")));

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.setConnection(connection);
            cliente = clienteDAO.buscar(cliente);
            agendamento.setCliente(cliente);

            VeiculoDAO veiculoDAO = new VeiculoDAO();
            veiculoDAO.setConnection(connection);
            veiculo = veiculoDAO.buscar(veiculo);
            agendamento.setVeiculo(veiculo);

            ServicoDAO servicoDAO = new ServicoDAO();
            servicoDAO.setConnection(connection);
            servico = servicoDAO.buscar(servico);
            agendamento.setServico(servico);

            retorno.add(agendamento);
        }
    } catch (SQLException ex) {
        Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return retorno;
}
     
     public Agendamento buscar(Agendamento agendamento) {
        String sql = "SELECT * FROM agendamentos WHERE cdAgendamento=?";
        Agendamento retorno = new Agendamento();
        Cliente cliente = new Cliente();
        Veiculo veiculo = new Veiculo();
        Servico servico = new Servico();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agendamento.getCdAgendamento());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                cliente.setCdCliente(resultado.getInt("cdCliente"));
                veiculo.setCdVeiculo(resultado.getInt("cdVeiculo"));
                servico.setCdServico(resultado.getInt("cdServico"));
                agendamento.setData_agendamento(resultado.getDate("data_agendamento").toLocalDate());
                agendamento.setHorario(resultado.getString("hora_agendamento"));
                agendamento.setValor(resultado.getDouble("valor_total"));
                
                
                retorno.setCliente(cliente);
                retorno.setServico(servico);
                retorno.setVeiculo(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
     
}
