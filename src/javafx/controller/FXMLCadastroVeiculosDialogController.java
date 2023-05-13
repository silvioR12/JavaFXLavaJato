/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.ClienteDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Cliente;
import javafx.model.domain.Veiculo;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLCadastroVeiculosDialogController implements Initializable {

    /*Criação dos textFields do FXML CadastrosClientesDialog*/   
    @FXML
    private ComboBox comboBoxVeiculoCliente;
    @FXML
    private TextField  textFieldVeiculoTipoVeiculo;
    @FXML
    private TextField  textFieldVeiculoPlaca;
    @FXML
    private TextField  textFieldVeiculoModelo;
    @FXML
    private TextField  textFieldVeiculoMarca;
    @FXML
    private TextField  textFieldVeiculoCategoria;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Veiculo veiculo;
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        carregarComboBoxClientes();
    }    

    public void carregarComboBoxClientes() {
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxVeiculoCliente.setItems(observableListClientes);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        comboBoxVeiculoCliente.getSelectionModel().select(veiculo.getCliente());
        this.textFieldVeiculoTipoVeiculo.setText(veiculo.getTipoVeiculo());
        this.textFieldVeiculoPlaca.setText(veiculo.getPlaca());
        this.textFieldVeiculoModelo.setText(veiculo.getModelo());
        this.textFieldVeiculoMarca.setText(veiculo.getMarca());
        this.textFieldVeiculoCategoria.setText(veiculo.getCategoria());
    }
    
    @FXML 
    public void handleButtonConfirmar() {
        if(validarEntradaDeDados()){
            veiculo.setCliente((Cliente) comboBoxVeiculoCliente.getSelectionModel().getSelectedItem());
            veiculo.setTipoVeiculo(textFieldVeiculoTipoVeiculo.getText());
            veiculo.setPlaca(textFieldVeiculoPlaca.getText());
            veiculo.setModelo(textFieldVeiculoModelo.getText()); 
            veiculo.setMarca(textFieldVeiculoMarca.getText());
            veiculo.setCategoria(textFieldVeiculoCategoria.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();  
        }    
    }
    
    /*Evento cancelar*/
    
    public void handleButtonCancelar(){
        dialogStage.close();
    }
    
       /*Método para validar se foi inserido algum valor ou não*/
    
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (comboBoxVeiculoCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente não selecionado!\n";
        }
        if (textFieldVeiculoTipoVeiculo.getText() ==  null || textFieldVeiculoTipoVeiculo.getText().length() == 0 ) {
            errorMessage += "Tipo Veículo vazio!\n";
        }
        if (textFieldVeiculoPlaca.getText() ==  null || textFieldVeiculoPlaca.getText().length() == 0 ) {
            errorMessage += "Placa vazio!\n";
        }
        if (textFieldVeiculoModelo.getText() ==  null || textFieldVeiculoModelo.getText().length() == 0 ) {
            errorMessage += "Modelo vazio!\n";
        }
        if (textFieldVeiculoMarca.getText() ==  null || textFieldVeiculoMarca.getText().length() == 0 ) {
            errorMessage += "Marca vazio!\n";
        }
        if (textFieldVeiculoCategoria.getText() ==  null || textFieldVeiculoCategoria.getText().length() == 0 ) {
            errorMessage += "Categoria vazio!\n";
        }
      
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //Mostrar mensagem de erro:
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos. Volte e corrija!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
}
