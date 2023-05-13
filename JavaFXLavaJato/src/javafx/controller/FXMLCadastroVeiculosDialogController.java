/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.domain.Veiculo;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLCadastroVeiculosDialogController implements Initializable {

    /*cRIAÇÃO DOS FXML*/
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

/*Criação dos textFields do FXML CadastrosClientesDialog*/    
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
    
    /*Criação dos botões */
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Veiculo veiculo;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        this.textFieldVeiculoTipoVeiculo.setText(veiculo.getTipoVeiculo());
        this.textFieldVeiculoPlaca.setText(veiculo.getPlaca());
        this.textFieldVeiculoModelo.setText(veiculo.getModelo());
        this.textFieldVeiculoMarca.setText(veiculo.getMarca());
        this.textFieldVeiculoCategoria.setText(veiculo.getCategoria());
    }
    
    @FXML 
    public void handleButtonConfirmar() {
        if(validarEntradaDeDados()){
            
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
        
        if (textFieldVeiculoTipoVeiculo.getText() ==  null || textFieldVeiculoTipoVeiculo.getText().length() == 0 ) {
            errorMessage += "Tipo Veículo inválido!\n";
        }
        if (textFieldVeiculoPlaca.getText() ==  null || textFieldVeiculoPlaca.getText().length() == 0 ) {
            errorMessage += "Placa Inválida!\n";
        }
        if (textFieldVeiculoModelo.getText() ==  null || textFieldVeiculoModelo.getText().length() == 0 ) {
            errorMessage += "Modelo Inválido!\n";
        }
        if (textFieldVeiculoMarca.getText() ==  null || textFieldVeiculoMarca.getText().length() == 0 ) {
            errorMessage += "Marca Inválida!\n";
        }
        if (textFieldVeiculoCategoria.getText() ==  null || textFieldVeiculoCategoria.getText().length() == 0 ) {
            errorMessage += "Categoria Inválida!\n";
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
