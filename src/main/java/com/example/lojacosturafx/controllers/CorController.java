package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Cor;
import com.example.lojacosturafx.servicos.CorService;
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
public class CorController implements Initializable {

    private final CorService corService;
    private final JavaFxApplication javaFxApplication;

    @FXML
    private TextField tfRed;

    @FXML
    private TextField tfGreen;

    @FXML
    private TextField tfBlue;

    @FXML
    private TextField tfHexCode;

    @FXML
    private TableView<Cor> tvCores;

    @FXML
    public TableColumn<Cor, Long> tcId;

    @FXML
    private TableColumn<Cor, Integer> tcRed;

    @FXML
    private TableColumn<Cor, Integer> tcGreen;

    @FXML
    private TableColumn<Cor, Integer> tcBlue;

    @FXML
    private TableColumn<Cor, String> tcHexCode;

    @FXML
    private void insertCorButton() {
        corService.create(new Cor(tfHexCode.getText(), Integer.parseInt(tfRed.getText()), Integer.parseInt(tfGreen.getText()), Integer.parseInt(tfBlue.getText())));
        showCor();
        clearFTCor();
    }

    @FXML
    private void updateCorButton() throws Exception {
        Cor cor = pegarCorDaLinha();
        corService.update(cor.getId(), tfHexCode.getText(), Integer.parseInt(tfRed.getText()), Integer.parseInt(tfGreen.getText()), Integer.parseInt(tfBlue.getText()));
        showCor();
        clearFTCor();
    }

    @FXML
    private void deleteCorButton() {
        Cor cor = pegarCorDaLinha();
        corService.delete(cor);
        showCor();
        clearFTCor();
    }

    @FXML
    private void onCliqueHome(MouseEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    public ObservableList<Cor> getCorList() {
        ObservableList<Cor> corList = FXCollections.observableArrayList();
        List<Cor> cores = corService.findAll();
        if (!cores.isEmpty()) {
            corList.addAll(cores);
        }
        return corList;
    }

    public void showCor() {
        ObservableList<Cor> lista = getCorList();

        tcId.setCellValueFactory(new PropertyValueFactory<Cor, Long>("id"));
        tcHexCode.setCellValueFactory(new PropertyValueFactory<Cor, String>("hexCode"));
        tcRed.setCellValueFactory(new PropertyValueFactory<Cor, Integer>("redValue"));
        tcGreen.setCellValueFactory(new PropertyValueFactory<Cor, Integer>("greenValue"));
        tcBlue.setCellValueFactory(new PropertyValueFactory<Cor, Integer>("blueValue"));

        tvCores.setItems(lista);
    }

    public void clearFTCor() {
        tfHexCode.setText("");
        tfRed.setText("");
        tfGreen.setText("");
        tfBlue.setText("");
    }

    public Cor pegarCorDaLinha() {
        return tvCores.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCor();
    }
}
