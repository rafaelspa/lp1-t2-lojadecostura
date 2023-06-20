package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Cliente;
import com.example.lojacosturafx.entidades.Medida;
import com.example.lojacosturafx.servicos.ClienteService;
import com.example.lojacosturafx.servicos.MedidaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// fonte: https://github.com/miduraa11/javafx-mysql-crud/blob/master/src/library/Books.java
// https://www.youtube.com/watch?v=HU9otCYkNaY

@RequiredArgsConstructor
@Component
public class ClienteController implements Initializable {

    private final ClienteService clienteService;
    private final MedidaService medidaService;
    private final JavaFxApplication javaFxApplication;
    private URL url;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfIdMedida;

    @FXML
    private TextField tfNomeMedida;

    @FXML
    private TextField tfTamanhoMedida;

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    private TableView<Medida> tvMedidas;

    @FXML
    private TableColumn<Cliente, Long> tcId;

    @FXML
    private TableColumn<Cliente, String> tcNome;

    @FXML
    private TableColumn<Cliente, String> tcTelefone;

    @FXML
    private TableColumn<Cliente, String> tcEmail;

    @FXML
    private TableColumn<Medida, Long> tcIdMedida;

    @FXML
    private TableColumn<Medida, String> tcNomeMedida;

    @FXML
    private TableColumn<Medida, Double> tcTamanhoMedida;
    
    @FXML
    private void insertClienteButton() {
        clienteService.create(new Cliente(tfNome.getText(), tfTelefone.getText(), tfEmail.getText()));
        showCliente();
        clearFTCliente();
    }

    @FXML
    private void updateClienteButton() throws Exception {
        Cliente cliente = pegarClienteDaLinha();
        clienteService.update(cliente.getId(), tfNome.getText(), tfTelefone.getText(), tfEmail.getText());
        showCliente();
        clearFTCliente();
    }

    @FXML
    private void deleteClienteButton() {
        Cliente cliente = pegarClienteDaLinha();
        clienteService.delete(cliente.getId());
        showCliente();
        clearFTCliente();
    }

    @FXML
    private void insertMedidaButton() throws Exception {
        Cliente cliente = pegarClienteDaLinha();
        Medida medida = new Medida(tfNomeMedida.getText(), Double.parseDouble(tfTamanhoMedida.getText()));
        medidaService.create(medida);
        clienteService.addMedida(cliente.getId(), medida);
        showCliente();
        showMedida();
        selecionaCliente(cliente.getId());
        clearFTMedida();
    }

    @FXML
    private void updateMedidaButton() throws Exception {
        Medida medida = pegarMedidaDaLinha();
        medidaService.update(medida.getId(), tfNomeMedida.getText(), Double.parseDouble(tfTamanhoMedida.getText()));
        tvMedidas.setItems(null);
        initialize(url, null);
        clearFTMedida();
    }

    @FXML
    private void deleteMedidaButton() {
        Medida medida = pegarMedidaDaLinha();
        medidaService.delete(medida.getId());
        tvMedidas.setItems(null);
        initialize(url, null);
        clearFTMedida();
    }

    @FXML
    private void onCliqueHome(MouseEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    public ObservableList<Cliente> getClienteList(){
        ObservableList<Cliente> clienteList = FXCollections.observableArrayList();
        List<Cliente> clientes = clienteService.findAll();
        if (!clientes.isEmpty()) {
            clienteList.addAll(clientes);
        }
        return clienteList;
    }

    public void showCliente() {
        ObservableList<Cliente> lista = getClienteList();

        tcId.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        tcTelefone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));

        tvClientes.setItems(lista);
    }

    public ObservableList<Medida> getMedidaClienteList(){
        Cliente cliente = pegarClienteDaLinha();
        ObservableList<Medida> medidaClienteList = FXCollections.observableArrayList();
        if (cliente != null) {
            List<Medida> medidas = new ArrayList<>(cliente.getMedidas());
            if (!medidas.isEmpty()) {
                medidaClienteList.addAll(medidas);
            }
        }
        return medidaClienteList;
    }

    private void showMedida() {
        ObservableList<Medida> listaMedidas = getMedidaClienteList();

        tcIdMedida.setCellValueFactory(new PropertyValueFactory<Medida, Long>("id"));
        tcNomeMedida.setCellValueFactory(new PropertyValueFactory<Medida, String>("nome"));
        tcTamanhoMedida.setCellValueFactory(new PropertyValueFactory<Medida, Double>("tamanho"));

        tvMedidas.setItems(listaMedidas);
    }

    public void clearFTCliente() {
        if (tfId != null) tfId.setText("");
        tfNome.setText("");
        tfTelefone.setText("");
        tfEmail.setText("");
    }

    private void clearFTMedida() {
        if (tfIdMedida != null) tfIdMedida.setText("");
        tfNomeMedida.setText("");
        tfTamanhoMedida.setText("");
    }

    public Cliente pegarClienteDaLinha() {
        return tvClientes.getSelectionModel().getSelectedItem();
    }

    public Medida pegarMedidaDaLinha() {
        return tvMedidas.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onCliqueTvCliente(MouseEvent event) {
        showMedida();
    }

    @FXML
    public void onCliqueOrcamento(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("orcamento");
    }

    public void selecionaCliente(Long id) {
        tvClientes.requestFocus();
        tvClientes.getSelectionModel().select(id.intValue() - 1);
        tvClientes.getFocusModel().focus(id.intValue() - 1);
        onCliqueTvCliente(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCliente();
        this.url = url;
    }
}
