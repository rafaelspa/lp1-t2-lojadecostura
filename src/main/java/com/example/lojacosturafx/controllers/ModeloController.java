package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Modelo;
import com.example.lojacosturafx.servicos.ModeloService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.ResourceBundle;

// fonte: https://github.com/miduraa11/javafx-mysql-crud/blob/master/src/library/Books.java
// https://www.youtube.com/watch?v=HU9otCYkNaY

@RequiredArgsConstructor
@Component
public class ModeloController implements Initializable {

    private final ModeloService modeloService;
    private final JavaFxApplication javaFxApplication;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfMultiplicador;

    @FXML
    private TableView<Modelo> tvModelos;

    @FXML
    private TableColumn<Modelo, Long> tcId;

    @FXML
    private TableColumn<Modelo, String> tcNome;

    @FXML
    private TableColumn<Modelo, Double> tcMultiplicador;
    
    @FXML
    private void insertModeloButton() {
        modeloService.create(new Modelo(tfNome.getText(), Double.parseDouble(tfMultiplicador.getText())));
        showModelo();
        clearFTModelo();
    }

    @FXML
    private void updateModeloButton() throws Exception {
        Modelo modelo = pegarModeloDaLinha();
        modeloService.update(modelo.getId(), tfNome.getText(), Double.parseDouble(tfMultiplicador.getText()));
        showModelo();
        clearFTModelo();
    }

    @FXML
    private void deleteModeloButton() {
        Modelo modelo = pegarModeloDaLinha();
        modeloService.delete(modelo.getId());
        showModelo();
        clearFTModelo();
    }

    @FXML
    private void onCliqueHome(MouseEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    public ObservableList<Modelo> getModeloList(){
        ObservableList<Modelo> listaModelo = FXCollections.observableArrayList();
        List<Modelo> modelos = modeloService.findAll();
        if (!modelos.isEmpty()) {
            listaModelo.addAll(modelos);
        }
        return listaModelo;
    }

    public void showModelo() {
        ObservableList<Modelo> lista = getModeloList();

        tcId.setCellValueFactory(new PropertyValueFactory<Modelo, Long>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<Modelo, String>("nome"));
        tcMultiplicador.setCellValueFactory(new PropertyValueFactory<Modelo, Double>("multiplicador"));

        tvModelos.setItems(lista);
    }

    public void clearFTModelo() {
        if (tfId != null) tfId.setText("");
        tfNome.setText("");
        tfMultiplicador.setText("");
    }

    public Modelo pegarModeloDaLinha() {
        return tvModelos.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showModelo();
    }
}
