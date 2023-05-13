/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.AgendamentoDAO;
import javafx.model.dao.ClienteDAO;
import javafx.model.dao.ServicoDAO;
import javafx.model.dao.VeiculoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Agendamento;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author silvi
 */
public class FXMLCadastroAgendamentosController implements Initializable {

    @FXML 
    private TableView<Agendamento> tableViewAgendamentos;
    @FXML
    private TableColumn <Agendamento, String> tableColumnAgendamentoData;
    @FXML
    private TableColumn<Agendamento, String> tableColumnAgendamentoHorario;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelAgendamentoCodigo;   
    @FXML
    private Label labelAgendamentoCliente;
    @FXML
    private Label labelAgendamentoVeiculo;
    @FXML
    private Label labelAgendamentoServico;
    @FXML
    private Label labelAgendamentoDia;
    @FXML
    private Label labelAgendamentoHorario;
    @FXML
    private Label labelAgendamentoValor;

    private List<Agendamento> listAgendamentos;
    private ObservableList<Agendamento> observableListAgendamentos;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agendamentoDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        veiculoDAO.setConnection(connection);
        servicoDAO.setConnection(connection);
        
        
        
        
        carregarTableViewAgendamentos();
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewAgendamentos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewAgendamentos(newValue));
    }

    public void carregarTableViewAgendamentos() {
        tableColumnAgendamentoData.setCellValueFactory(new PropertyValueFactory<>("data_agendamento"));
        tableColumnAgendamentoHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        listAgendamentos = agendamentoDAO.listar();

        observableListAgendamentos = FXCollections.observableArrayList(listAgendamentos);
        tableViewAgendamentos.setItems(observableListAgendamentos);
    } 
    
    public void selecionarItemTableViewAgendamentos(Agendamento agendamento) {
        if (agendamento != null) {
            labelAgendamentoCodigo.setText(String.valueOf(agendamento.getCdAgendamento()));
            labelAgendamentoCliente.setText(agendamento.getCliente().toString());
            labelAgendamentoVeiculo.setText(agendamento.getVeiculo().toString());
            labelAgendamentoServico.setText(agendamento.getServico().toString());
            labelAgendamentoDia.setText(String.valueOf(agendamento.getData_agendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelAgendamentoHorario.setText(agendamento.getHorario());
            labelAgendamentoValor.setText(String.valueOf(agendamento.getValor()));
        } else {
            labelAgendamentoCodigo.setText("");
            labelAgendamentoCliente.setText("");
            labelAgendamentoVeiculo.setText("");
            labelAgendamentoServico.setText("");
            labelAgendamentoDia.setText("");
            labelAgendamentoHorario.setText("");
            labelAgendamentoValor.setText("");
        }
    }
    
    /*Botão inserir*/
    @FXML 
    public void handleButtonInserir() throws IOException {
        Agendamento agendamento = new Agendamento();
        boolean buttonConfirmarClicked = showFXMLCadastroAgendamentosDialog(agendamento);
        if (buttonConfirmarClicked) {
            agendamentoDAO.inserir(agendamento);
            carregarTableViewAgendamentos();
        }
    }

    /*Botão Alterar*/
    @FXML
    public void handleButtonAlterar() throws IOException {
        Agendamento agendamento = tableViewAgendamentos.getSelectionModel().getSelectedItem();
        if (agendamento != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroAgendamentosDialog(agendamento);
            if (buttonConfirmarClicked) {
                agendamentoDAO.alterar(agendamento);
                carregarTableViewAgendamentos();
            }
        } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um agendamento na Tabela");
            alert.show();
        }
    }
    
    /*Botão Excluir*/
    @FXML
     public void handleButtonRemover() throws IOException {
         Agendamento agendamento = tableViewAgendamentos.getSelectionModel().getSelectedItem();
         if (agendamento != null) {
             agendamentoDAO.remover(agendamento);
             carregarTableViewAgendamentos();
         } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um agendamento na Tabela");
            alert.show();
         }
     }
     
     /*showFXMLCadastroServicoDialogController*/
     public boolean showFXMLCadastroAgendamentosDialog(Agendamento agendamento) throws IOException{
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(FXMLCadastroAgendamentoDialogController.class.getResource("/javafx/view/FXMLCadastroAgendamentoDialog.fxml"));
         AnchorPane page = (AnchorPane) loader.load();
         
         /*Criando um estágio de dialog*/
         Stage dialogStage = new Stage();
         dialogStage.setTitle("Cadastro de Agendamentos");
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);
         
         /*Setando o servico na controller*/
         FXMLCadastroAgendamentoDialogController controller = loader.getController();
         controller.setDialogStage(dialogStage);
         //controller.setAgendamento(agendamento);
         
         /*Mostra o dialog e espera até que o usuário o feche*/
         dialogStage.showAndWait();
         
         return controller.isButtonConfirmarClicked();
     }
    
    
    
}
