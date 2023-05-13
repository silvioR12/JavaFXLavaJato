package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.ClienteDAO;
import javafx.model.dao.ServicoDAO;
import javafx.model.dao.VeiculoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Agendamento;
import javafx.model.domain.Cliente;
import javafx.model.domain.Servico;
import javafx.model.domain.Veiculo;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;


public class FXMLCadastroAgendamentoDialogController implements Initializable {
    /*Criação do comboBOX*/
    @FXML
    private ComboBox comboBoxAgendamentoClienteCadastrado;
    @FXML
    private ComboBox comboBoxAgendamentoVeiculoCadastrado;
    @FXML
    private ComboBox comboBoxAgendamentoServicoCadastrado;
    @FXML
    private DatePicker datePickerAgendamentoDia;
    
    @FXML
    private ComboBox comboBoxAgendamentoHorarioCadastrado;
    @FXML
    private ComboBox comboBoxAgendamentoValorCadastrado;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    private List<Veiculo> listVeiculos;
    private ObservableList<Veiculo> observableListVeiculos;
    
    private List<Servico> listTipoServico;
    private ObservableList<Servico> observableListTipoServicos;

    //VERIFICAR SE ISSO AQUI VAI DAR CERTO;
    private List<String> listHorarios = Arrays.asList("8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18");
    private ObservableList<String> observableListHorarios;
   
    private List<Servico> listValorServicos;
    private ObservableList<Servico> observableListValorServicos;
    
   
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
   
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Agendamento agendamento;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       clienteDAO.setConnection(connection);
       veiculoDAO.setConnection(connection);
       servicoDAO.setConnection(connection);
       
       
       carregarComboBoxClientes();
       carregarComboBoxVeiculos();
       carregarComboBoxServicos();
       carregarComboBoxHorarios();
       carregarComboBoxAgendamentoValorCadastrado();
    }

    public void carregarComboBoxClientes() {
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxAgendamentoClienteCadastrado.setItems(observableListClientes);
    }
    
    public void carregarComboBoxVeiculos() {
        listVeiculos = veiculoDAO.listar();
        observableListVeiculos = FXCollections.observableArrayList(listVeiculos);
        comboBoxAgendamentoVeiculoCadastrado.setItems(observableListVeiculos);
    }
    
    public void carregarComboBoxServicos() {
        listTipoServico = servicoDAO.listar();
        observableListTipoServicos = FXCollections.observableArrayList(listTipoServico);
        comboBoxAgendamentoServicoCadastrado.setItems(observableListTipoServicos);
    }
    
    public void carregarComboBoxHorarios() {
        observableListHorarios = FXCollections.observableArrayList(listHorarios);
        comboBoxAgendamentoHorarioCadastrado.setItems(observableListHorarios);
    }
    
    public void carregarComboBoxAgendamentoValorCadastrado() {
        listValorServicos = servicoDAO.listar();
        observableListValorServicos = FXCollections.observableArrayList(listValorServicos);
        comboBoxAgendamentoValorCadastrado.setItems(observableListValorServicos);
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

    public Agendamento getAgendamento() {
        return agendamento;
    }

    
    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        comboBoxAgendamentoClienteCadastrado.getSelectionModel().select(agendamento.getCliente());
        comboBoxAgendamentoVeiculoCadastrado.getSelectionModel().select(agendamento.getVeiculo());
        comboBoxAgendamentoServicoCadastrado.getSelectionModel().select(agendamento.getServico());
        datePickerAgendamentoDia.setValue(agendamento.getData_agendamento());
        comboBoxAgendamentoHorarioCadastrado.getSelectionModel().select(agendamento.getHorario());
        comboBoxAgendamentoValorCadastrado.getSelectionModel().select(agendamento.getValor());
    }
    
    @FXML 
    public void handleButtonConfirmar() {
        if(validarEntradaDeDados()){
            agendamento.setCliente((Cliente) comboBoxAgendamentoClienteCadastrado.getSelectionModel().getSelectedItem());
            agendamento.setVeiculo((Veiculo) comboBoxAgendamentoVeiculoCadastrado.getSelectionModel().getSelectedItem());
            agendamento.setServico((Servico) comboBoxAgendamentoServicoCadastrado.getSelectionModel().getSelectedItem());
            agendamento.setData_agendamento(datePickerAgendamentoDia.getValue());
            
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
        
        if (comboBoxAgendamentoClienteCadastrado.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente não selecionado!\n";
        }
        if (comboBoxAgendamentoVeiculoCadastrado.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Veiculo não selecionado!\n";
        }
        if (comboBoxAgendamentoServicoCadastrado.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Servico não selecionado!\n";
        }
        if (datePickerAgendamentoDia.getValue() == null) {
            errorMessage += "Data não inserida!\n";
        }
        if (comboBoxAgendamentoHorarioCadastrado.getSelectionModel().getSelectedItem() ==  null) {
            errorMessage += "Horario não selecionado!\n";
        }
        if (comboBoxAgendamentoValorCadastrado.getSelectionModel().getSelectedItem() ==  null ) {
            errorMessage += "Valor não selecionado!\n";
        }
      
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //Mostrar mensagem de erro:
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Erro no agendamento");
            alert.setHeaderText("Campos inválidos. Volte e corrija!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    
}
