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
import javafx.model.domain.Servico;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Róger Corrente
 */
public class FXMLCadastroServicosDialogController implements Initializable {

    /*cRIAÇÃO DOS FXML*/
    @FXML
    private Label labelServicoTipoServico;
    @FXML
    private Label labelServicoDescricao;
    @FXML
    private Label labelServicoTempoEstimado;
    @FXML
    private Label labelServicoValorAVista;
    @FXML
    private Label labelServicoValorAPrazo;
    
    /*Criação dos textFields do FXML CadastrosClientesDialog*/    
    @FXML
    private TextField  textFieldServicoTipoServico;
    @FXML
    private TextArea  textAreaServicoDescricao; 
    @FXML
    private TextField  textFieldServicoTempoEstimado;
    @FXML
    private TextField  textFieldServicoValorAVista;
    @FXML
    private TextField  textFieldServicoValorAPrazo;
   
    /*Criação dos botões */
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Servico servico;
    
    
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
        this.textFieldServicoTipoServico.setText(servico.getTipoServico());
        this.textAreaServicoDescricao.setText(servico.getDescricao());
        this.textFieldServicoTempoEstimado.setText(servico.getTempoEstimado());
        this.textFieldServicoValorAVista.setText(String.valueOf(servico.getValorAVista()));
        this.textFieldServicoValorAPrazo.setText(String.valueOf(servico.getValorAprazo()));
    }
    
    @FXML 
    public void handleButtonConfirmar() {
        
        if(validarEntradaDeDados()){
            servico.setTipoServico(textFieldServicoTipoServico.getText());
            servico.setDescricao(textAreaServicoDescricao.getText());
            servico.setTempoEstimado(textFieldServicoTempoEstimado.getText());
            servico.setValorAVista(Double.parseDouble(textFieldServicoValorAVista.getText()));
            servico.setValorAprazo(Double.parseDouble(textFieldServicoValorAPrazo.getText()));
            
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
        
        if (textFieldServicoTipoServico.getText() ==  null || textFieldServicoTipoServico.getText().length() == 0 ) {
            errorMessage += "Tipo de Servico vazio!\n";
        }
        if (textAreaServicoDescricao.getText() ==  null || textAreaServicoDescricao.getText().length() == 0 ) {
            errorMessage += "Descricao vazio!\n";
        }
        if (textFieldServicoTempoEstimado.getText()==  null || textFieldServicoTempoEstimado.getText().length() == 0 ) {
            errorMessage += "Tempo Estimado vazio!\n";
        }
        if (textFieldServicoValorAVista.getText() ==  null || textFieldServicoValorAVista.getText().length() == 0 ) {
            errorMessage += "Valor a vista vazio!\n";
        }
        if (textFieldServicoValorAPrazo.getText() ==  null || textFieldServicoValorAPrazo.getText().length() == 0 ) {
            errorMessage += "Valor a prazo vazio!\n";
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
