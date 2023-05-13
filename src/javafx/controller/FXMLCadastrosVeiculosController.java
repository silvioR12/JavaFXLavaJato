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
import javafx.model.dao.VeiculoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Veiculo;
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
 * @author 20211si029
 */
public class FXMLCadastrosVeiculosController implements Initializable {
    
    @FXML 
    private TableView<Veiculo> tableViewVeiculos;
    @FXML
    private TableColumn <Veiculo, String> tableColumnVeiculoModelo;
    @FXML
    private TableColumn<Veiculo, String> tableColumnVeiculoPlaca;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelVeiculoCodigo;   
    @FXML
    private Label labelVeiculoTipoVeiculo;
    @FXML
    private Label labelVeiculoPlaca;
    @FXML
    private Label labelVeiculoModelo;
    @FXML
    private Label labelVeiculoMarca;
    @FXML
    private Label labelVeiculoCategoria;
    @FXML 
    private Label labelVeiculoCliente; 
    

    private List<Veiculo> listVeiculos;
    private ObservableList<Veiculo> observableListVeiculos;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veiculoDAO.setConnection(connection);
        carregarTableViewVeiculos();
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewVeiculos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVeiculos(newValue));
    }    
    
    
    public void carregarTableViewVeiculos() {
        tableColumnVeiculoModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tableColumnVeiculoPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));

        listVeiculos = veiculoDAO.listar();

        observableListVeiculos = FXCollections.observableArrayList(listVeiculos);
        tableViewVeiculos.setItems(observableListVeiculos);
    } 
    
    public void selecionarItemTableViewVeiculos(Veiculo veiculo) {
        if (veiculo != null) {
            labelVeiculoCodigo.setText(String.valueOf(veiculo.getCdVeiculo()));
            labelVeiculoTipoVeiculo.setText(veiculo.getTipoVeiculo());
            labelVeiculoPlaca.setText(veiculo.getPlaca());
            labelVeiculoModelo.setText(veiculo.getModelo());
            labelVeiculoMarca.setText(veiculo.getMarca());
            labelVeiculoCategoria.setText(veiculo.getCategoria());
            labelVeiculoCliente.setText(veiculo.getCliente().toString());

        } else {
            labelVeiculoCodigo.setText("");
            labelVeiculoTipoVeiculo.setText("");
            labelVeiculoPlaca.setText("");
            labelVeiculoModelo.setText("");
            labelVeiculoMarca.setText("");
            labelVeiculoCategoria.setText("");
            labelVeiculoCliente.setText("");
        }
    }
    
    /*Botão inserir*/
   @FXML 
    public void handleButtonInserir() throws IOException {
        Veiculo veiculo = new Veiculo();
        boolean buttonConfirmarClicked = showFXMLCadastroVeiculosDialog(veiculo);
        if (buttonConfirmarClicked) {
            veiculoDAO.inserir(veiculo);
            carregarTableViewVeiculos();
        }
    }
    
    /*Botão Alterar*/
    
    @FXML
    public void handleButtonAlterar() throws IOException {
        Veiculo veiculo = tableViewVeiculos.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroVeiculosDialog(veiculo);
            if (buttonConfirmarClicked) {
                veiculoDAO.alterar(veiculo);
                carregarTableViewVeiculos();
            }
        } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um veiculo na Tabela");
            alert.show();
        }
    }
    /*Botão Excluir*/
    
    @FXML
     public void handleButtonRemover() throws IOException {
         Veiculo veiculo = tableViewVeiculos.getSelectionModel().getSelectedItem();
         if (veiculo != null) {
             veiculoDAO.remover(veiculo);
             carregarTableViewVeiculos();
         } else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um veiculo na Tabela");
            alert.show();
         }
     }
     
     /*showFXMLCadastroClienteDialogController*/
     
     public boolean showFXMLCadastroVeiculosDialog(Veiculo veiculo) throws IOException{
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(FXMLCadastroVeiculosDialogController.class.getResource("/javafx/view/FXMLCadastroVeiculosDialog.fxml"));
         AnchorPane page = (AnchorPane) loader.load();
         
         /*Criando um estágio de dialog*/
         Stage dialogStage = new Stage();
         dialogStage.setTitle("Cadastro de Veiculos");
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);
         
         /*Setando o cliente no controller*/
         FXMLCadastroVeiculosDialogController controller = loader.getController();
         controller.setDialogStage(dialogStage);
         controller.setVeiculo(veiculo);
         
         /*Mostra o dialog e espera até que o usuário o feche*/
         dialogStage.showAndWait();
         
         return controller.isButtonConfirmarClicked();
     }
    
    
}
