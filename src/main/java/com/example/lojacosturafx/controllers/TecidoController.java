package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Tecido;
import com.example.lojacosturafx.servicos.TecidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

@RequiredArgsConstructor
@Component

public class
TecidoController implements Initializable {

    private final TecidoService tecidoService;
    private final JavaFxApplication javaFxApplication;

    @FXML
    private Button btnHome;

    @FXML
    private Button deleteTecidoButton;

    @FXML
    private Button insertTecidoButton;

    @FXML
    private TableColumn<Tecido, Long> tcId;

    @FXML
    private TableColumn<Tecido, String> tcNome;

    @FXML
    private TableColumn<Tecido, Double> tcPrecoMetro;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPrecoMetro;

    @FXML
    private TableView<Tecido> tvTecidos;

    @FXML
    private Button updateTecidoButton;

    @FXML
    void deleteTecidoButton(ActionEvent event) {
        Tecido tecido = pegarTecidoDaLinha();
        tecidoService.delete(tecido.getId());
        showTecido();
        clearFTTecido();
    }

    @FXML
    void insertTecidoButton(ActionEvent event) {
        tecidoService.create(new Tecido(tfNome.getText(), Double.parseDouble(tfPrecoMetro.getText())));
        showTecido();
        clearFTTecido();
    }

    @FXML
    void onClickHome(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    @FXML
    void updateTecidoButton(ActionEvent event) throws Exception{
        Tecido tecido = pegarTecidoDaLinha();
        tecidoService.update(tecido.getId(), tfNome.getText(), Double.parseDouble(tfPrecoMetro.getText()));
        showTecido();
        clearFTTecido();
    }

    @FXML
    public void onCliqueHome(MouseEvent mouseEvent) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    public ObservableList<Tecido> getTecidoList(){
        ObservableList<Tecido> listaTecido = FXCollections.observableArrayList();
        List<Tecido> tecidos = tecidoService.findAll();
        if (!tecidos.isEmpty()) {
            listaTecido.addAll(tecidos);
        }
        return listaTecido;
    }

    public void showTecido(){
        ObservableList<Tecido> lista = getTecidoList();

        tcId.setCellValueFactory(new PropertyValueFactory<Tecido, Long>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<Tecido, String>("nome"));
        tcPrecoMetro.setCellValueFactory(new PropertyValueFactory<Tecido, Double>("precoMetro"));

        tvTecidos.setItems(lista);
    }

    public Tecido pegarTecidoDaLinha() {
        return (Tecido) tvTecidos.getSelectionModel().getSelectedItem();
    }

    public void clearFTTecido() {
        tfNome.setText("");
        tfPrecoMetro.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTecido();
    }
}
