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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author 20211si025
 */
public class FXMLControllerTelaPrincipal implements Initializable {
    
    @FXML
    private ImageView imageViewLogo;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button buttonCadastros;
    @FXML
    private Button buttonProcessos;
    @FXML
    private Button buttonGraficos;
    @FXML
    private Button buttonRelatorios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
   @FXML
   public void handleButtonCadastros() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLTelaPrincipalClientes.fxml"));
        anchorPane.getChildren().setAll(a);
   }
   
   @FXML
   public void handleMenuItemCadastrosVeiculos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrosVeiculos.fxml"));
        anchorPane.getChildren().setAll(a);
   }
   
   @FXML
   public void handleMenuItemCadastrosServicos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrosServicos.fxml"));
        anchorPane.getChildren().setAll(a);
   }
}
