package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Adicional;
import com.example.lojacosturafx.servicos.AdicionalService;
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
public class AdicionalController implements Initializable {

    private final AdicionalService adicionalService;
    private final JavaFxApplication javaFxApplication;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfMultiplicador;

    @FXML
    private TableView<Adicional> tvAdicionais;

    @FXML
    private TableColumn<Adicional, Long> tcId;

    @FXML
    private TableColumn<Adicional, String> tcNome;

    @FXML
    private TableColumn<Adicional, Double> tcMultiplicador;
    
    @FXML
    private void insertAdicionalButton() {
        adicionalService.create(new Adicional(tfNome.getText(), Double.parseDouble(tfMultiplicador.getText())));
        showAdicional();
        clearFTAdicional();
    }

    @FXML
    private void updateAdicionalButton() throws Exception {
        Adicional adicional = pegarAdicionalDaLinha();
        adicionalService.update(adicional.getId(), tfNome.getText(), Double.parseDouble(tfMultiplicador.getText()));
        showAdicional();
        clearFTAdicional();
    }

    @FXML
    private void deleteAdicionalButton() {
        Adicional adicional = pegarAdicionalDaLinha();
        adicionalService.delete(adicional.getId());
        showAdicional();
        clearFTAdicional();
    }

    @FXML
    private void onCliqueHome(MouseEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    public ObservableList<Adicional> getAdicionalList(){
        ObservableList<Adicional> listaAdicional = FXCollections.observableArrayList();
        List<Adicional> adicionais = adicionalService.findAll();
        if (!adicionais.isEmpty()) {
            listaAdicional.addAll(adicionais);
        }
        return listaAdicional;
    }

    public void showAdicional() {
        ObservableList<Adicional> lista = getAdicionalList();

        tcId.setCellValueFactory(new PropertyValueFactory<Adicional, Long>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<Adicional, String>("nome"));
        tcMultiplicador.setCellValueFactory(new PropertyValueFactory<Adicional, Double>("multiplicador"));

        tvAdicionais.setItems(lista);
    }

    public void clearFTAdicional() {
//        if (tfId != null) tfId.setText("");
        tfNome.setText("");
        tfMultiplicador.setText("");
    }

    public Adicional pegarAdicionalDaLinha() {
        return tvAdicionais.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAdicional();
    }
}
