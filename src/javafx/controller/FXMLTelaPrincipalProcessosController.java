/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author muril
 */
public class FXMLTelaPrincipalProcessosController implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageViewProcessosAgendamento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    //*@FXML
    public void handleButtonCadastrosAgendamentos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastroAgendamentos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
 }
    
