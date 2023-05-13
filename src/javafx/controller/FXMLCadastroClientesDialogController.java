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
import javafx.model.domain.Cliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastroClientesDialogController implements Initializable {
    
    /*cRIAÇÃO DOS FXML*/
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
    private Label labelClienteData;
    @FXML
    private Label labelClienteGenero;

/*Criação dos textFields do FXML CadastrosClientesDialog*/    
    @FXML
    private TextField  textFieldClienteNome;
    @FXML
    private TextField  textFieldClienteRG;
    @FXML
    private TextField  textFieldClienteTelefone;
    @FXML
    private TextField  textFieldClienteCidade;
    @FXML
    private TextField  textFieldClienteEmail;
    @FXML
    private TextField  textFieldClienteDataNascimento;
    @FXML
    private TextField  textFieldClienteGenero;
    
    /*Criação dos botões */
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Cliente cliente;
    
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

  
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.textFieldClienteNome.setText(cliente.getNome());
        this.textFieldClienteRG.setText(cliente.getRg());
        this.textFieldClienteTelefone.setText(cliente.getTelefone());
        this.textFieldClienteCidade.setText(cliente.getCidade());
        this.textFieldClienteEmail.setText(cliente.getEmail());
        this.textFieldClienteDataNascimento.setText(cliente.getDataNascimento());
        this.textFieldClienteGenero.setText(cliente.getGenero());
    }
    
    /*Implementação dos eventos: Confirmar e cancelar*/
    @FXML 
    public void handleButtonConfirmar() {
        
        if(validarEntradaDeDados()){
            cliente.setNome(textFieldClienteNome.getText());
            cliente.setRg(textFieldClienteRG.getText());
            cliente.setTelefone(textFieldClienteTelefone.getText()); 
            cliente.setCidade(textFieldClienteCidade.getText());
            cliente.setEmail(textFieldClienteEmail.getText());
            cliente.setDataNascimento(textFieldClienteDataNascimento.getText());
            cliente.setGenero(textFieldClienteGenero.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }     
    }
    
    /*Evento cancelar*/
    @FXML
    public void handleButtonCancelar(){
        dialogStage.close();
    }
    
    
    
    /*Método para validar se foi inserido algum valor ou não*/
    
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (textFieldClienteNome.getText() ==  null || textFieldClienteNome.getText().length() == 0 ) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldClienteRG.getText() ==  null || textFieldClienteRG.getText().length() == 0 ) {
            errorMessage += "RG inválido!\n";
        }
        if (textFieldClienteTelefone.getText() ==  null || textFieldClienteTelefone.getText().length() == 0 ) {
            errorMessage += "Telefone inválido!\n";
        }
        if (textFieldClienteCidade.getText() ==  null || textFieldClienteCidade.getText().length() == 0 ) {
            errorMessage += "Cidade inválida!\n";
        }
        if (textFieldClienteEmail.getText() ==  null || textFieldClienteEmail.getText().length() == 0 ) {
            errorMessage += "Email inválido!\n";
        }
        if (textFieldClienteDataNascimento.getText() ==  null || textFieldClienteDataNascimento.getText().length() == 0 ) {
            errorMessage += "Data de nascimento inválida!\n";
        }
        if (textFieldClienteGenero.getText() ==  null || textFieldClienteGenero.getText().length() == 0 ) {
            errorMessage += "Gênero  inválido!\n";
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
