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
public class FXMLTelaPrincipalCadastrosController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageViewClientes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
   @FXML
   public void handleButtonCadastrosClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrosClientes.fxml"));
        anchorPane.getChildren().setAll(a);
   }
   
   @FXML
   public void handleButtonCadastrosVeiculos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrosVeiculos.fxml"));
        anchorPane.getChildren().setAll(a);
   }
   
   @FXML
   public void handleButtonCadastrosServicos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrosServicos.fxml"));
        anchorPane.getChildren().setAll(a);
   }
   
}
    

