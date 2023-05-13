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
import javafx.model.dao.ServicoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Servico;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Róger Corrente
 */
public class FXMLCadastrosServicosController implements Initializable {

    @FXML 
    private TableView<Servico> tableViewServicos;
    @FXML
    private TableColumn <Servico, String> tableColumnServicoTipoServico;
    @FXML
    private TableColumn <Servico, Double> tableColumnServicoValorAVista;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelServicoCodigo;   
    @FXML
    private Label labelServicoTipoDeServico;
    @FXML
    private ScrollPane scrollPaneServicoDescricao;
    @FXML
    private Label labelServicoTempoEstimado;
    @FXML
    private Label labelServicoValorAVista;
    @FXML
    private Label labelServicoValorAPrazo;
    
    private List<Servico> listServicos;
    private ObservableList<Servico> observableListServicos;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicoDAO.setConnection(connection);
        carregarTableViewServicos();
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewServicos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewServicos(newValue));
    }    
    
    
    public void carregarTableViewServicos() {
        tableColumnServicoTipoServico.setCellValueFactory(new PropertyValueFactory<>("tipoServico"));
        tableColumnServicoValorAVista.setCellValueFactory(new PropertyValueFactory<>("valorAVista"));

        listServicos = servicoDAO.listar();

        observableListServicos = FXCollections.observableArrayList(listServicos);
        tableViewServicos.setItems(observableListServicos);
    } 
    
    public void selecionarItemTableViewServicos(Servico servico) {
    if (servico != null) {
        labelServicoCodigo.setText(String.valueOf(servico.getCdServico()));
        labelServicoTipoDeServico.setText(servico.getTipoServico());
        Label labelDescricao = new Label(servico.getDescricao()); //Usando ScrollPane
        scrollPaneServicoDescricao.setContent(labelDescricao); // Usando ScrollPane
        labelServicoTempoEstimado.setText(String.valueOf(servico.getTempoEstimado()));
        labelServicoValorAVista.setText(String.valueOf(servico.getValorAVista()));
        labelServicoValorAPrazo.setText(String.valueOf(servico.getValorAprazo()));
        } else {
            labelServicoCodigo.setText("");
            labelServicoTipoDeServico.setText("");
            scrollPaneServicoDescricao.setContent(new Label(""));
            labelServicoTempoEstimado.setText("");
            labelServicoValorAVista.setText("");
            labelServicoValorAPrazo.setText("");
        }
    }
    
   /*Botão inserir*/
   @FXML 
    public void handleButtonInserir() throws IOException {
        Servico servico = new Servico();
        boolean buttonConfirmarClicked = showFXMLCadastroServicosDialog(servico);
        if (buttonConfirmarClicked) {
            servicoDAO.inserir(servico);
            carregarTableViewServicos();
        }
    }
    
    /*Botão Alterar*/
    @FXML
    public void handleButtonAlterar() throws IOException {
        Servico servico = tableViewServicos.getSelectionModel().getSelectedItem();
        if (servico != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroServicosDialog(servico);
            if (buttonConfirmarClicked) {
                servicoDAO.alterar(servico);
                carregarTableViewServicos();
            }
        } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um servico na Tabela");
            alert.show();
        }
    }
    /*Botão Excluir*/
    
    @FXML
     public void handleButtonRemover() throws IOException {
         Servico servico = tableViewServicos.getSelectionModel().getSelectedItem();
         if (servico != null) {
             servicoDAO.remover(servico);
             carregarTableViewServicos();
         } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um servico na Tabela");
            alert.show();
         }
     }
     
     /*showFXMLCadastroServicoDialogController*/
     public boolean showFXMLCadastroServicosDialog(Servico servico) throws IOException{
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(FXMLCadastroServicosDialogController.class.getResource("/javafx/view/FXMLCadastroServicosDialog.fxml"));
         AnchorPane page = (AnchorPane) loader.load();
         
         /*Criando um estágio de dialog*/
         Stage dialogStage = new Stage();
         dialogStage.setTitle("Cadastro de Servicos");
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);
         
         /*Setando o servico na controller*/
         FXMLCadastroServicosDialogController controller = loader.getController();
         controller.setDialogStage(dialogStage);
         controller.setServico(servico);
         
         /*Mostra o dialog e espera até que o usuário o feche*/
         dialogStage.showAndWait();
         
         return controller.isButtonConfirmarClicked();
     }
}
