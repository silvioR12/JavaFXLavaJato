/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.ClienteDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Cliente;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXMLCadastrosClientesController implements Initializable {
    
 
    @FXML 
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn <Cliente, String> tableColumnClienteNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteRG;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelClienteCodigo;   
    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteRG;
    @FXML
    private Label labelClienteTelefone;
    @FXML
    private Label labelClienteCidade;
    @FXML
    private Label labelClienteEmail;
    @FXML
    private Label labelClienteDataNascimento;
    @FXML
    private Label labelClienteGenero;

    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        carregarTableViewClientes();
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));
    }   
    
    public void carregarTableViewClientes() {
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteRG.setCellValueFactory(new PropertyValueFactory<>("rg"));

        listClientes = clienteDAO.listar();

        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableViewClientes.setItems(observableListClientes);
    } 
    
    public void selecionarItemTableViewClientes(Cliente cliente) {
        if (cliente != null) {
            labelClienteCodigo.setText(String.valueOf(cliente.getCdCliente()));
            labelClienteNome.setText(cliente.getNome());
            labelClienteRG.setText(cliente.getRg());
            labelClienteTelefone.setText(cliente.getTelefone());
            labelClienteCidade.setText(cliente.getCidade());
            labelClienteEmail.setText(cliente.getEmail());
            labelClienteDataNascimento.setText(cliente.getDataNascimento());
            labelClienteGenero.setText(cliente.getGenero());
        } else {
            labelClienteCodigo.setText("");
            labelClienteNome.setText("");
            labelClienteRG.setText("");
            labelClienteTelefone.setText("");
            labelClienteCidade.setText("");
            labelClienteEmail.setText("");
            labelClienteDataNascimento.setText("");
            labelClienteGenero.setText("");
        }
    }
    
    
    /*Botão inserar*/
   @FXML 
    public void handleButtonInserir() throws IOException {
        Cliente cliente = new Cliente();
        boolean buttonConfirmarClicked = showFXMLCadastroClientesDialog(cliente);
        if (buttonConfirmarClicked) {
            clienteDAO.inserir(cliente);
            carregarTableViewClientes();
        }
    }
    
    /*Botão Alterar*/
    
    @FXML
    public void handleButtonAlterar() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroClientesDialog(cliente);
            if (buttonConfirmarClicked) {
                clienteDAO.alterar(cliente);
                carregarTableViewClientes();
            }
        } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela");
            alert.show();
        }
    }
    /*Botão Excluir*/
    
    @FXML
     public void handleButtonRemover() throws IOException {
         Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
         if (cliente != null) {
             clienteDAO.remover(cliente);
             carregarTableViewClientes();
         } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela");
            alert.show();
         }
     }
     
     /*showFXMLCadastroClienteDialogController*/
     
     public boolean showFXMLCadastroClientesDialog(Cliente cliente) throws IOException{
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(FXMLCadastroClientesDialogController.class.getResource("/javafx/view/FXMLCadastroClientesDialog.fxml"));
         AnchorPane page = (AnchorPane) loader.load();
         
         /*Criando um estágio de dialog*/
         Stage dialogStage = new Stage();
         dialogStage.setTitle("Cadastro de Clientes");
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);
         
         /*Setando o cliente na controller*/
         
         FXMLCadastroClientesDialogController controller = loader.getController();
         controller.setDialogStage(dialogStage);
         controller.setCliente(cliente);
         
         /*Mostra o dialog e espera até que o usuário o feche*/
         
         dialogStage.showAndWait();
         
         return controller.isButtonConfirmarClicked();
     }
    
}
