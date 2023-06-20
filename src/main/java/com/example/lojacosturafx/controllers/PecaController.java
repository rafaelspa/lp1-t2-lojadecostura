package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Medida;
import com.example.lojacosturafx.entidades.Peca;
import com.example.lojacosturafx.servicos.MedidaService;
import com.example.lojacosturafx.servicos.PecaService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// fonte: https://github.com/miduraa11/javafx-mysql-crud/blob/master/src/library/Books.java
// https://www.youtube.com/watch?v=HU9otCYkNaY

@RequiredArgsConstructor
@Component
public class PecaController implements Initializable {

    private final PecaService pecaService;
    private final MedidaService medidaService;
    private final JavaFxApplication javaFxApplication;
    private URL url;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPrecoBase;

    @FXML
    private TextField tfIdMedida;

    @FXML
    private TextField tfNomeMedida;

    @FXML
    private TextField tfTamanhoMedida;

    @FXML
    private TableView<Peca> tvPecas;

    @FXML
    private TableView<Medida> tvMedidas;

    @FXML
    private TableColumn<Peca, Long> tcId;

    @FXML
    private TableColumn<Peca, String> tcNome;

    @FXML
    private TableColumn<Peca, Double> tcPrecoBase;

    @FXML
    private TableColumn<Medida, Long> tcIdMedida;

    @FXML
    private TableColumn<Medida, String> tcNomeMedida;

    @FXML
    private TableColumn<Medida, Double> tcTamanhoMedida;
    
    @FXML
    private void insertPecaButton() {
        pecaService.create(new Peca(tfNome.getText(), Double.parseDouble(tfPrecoBase.getText())));
        showPeca();
        clearFTPeca();
    }

    @FXML
    private void updatePecaButton() throws Exception {
        Peca peca = pegarPecaDaLinha();
        pecaService.update(peca.getId(), tfNome.getText(), Double.parseDouble(tfPrecoBase.getText()));
        showPeca();
        clearFTPeca();
    }

    @FXML
    private void deletePecaButton() {
        Peca peca = pegarPecaDaLinha();
        pecaService.delete(peca.getId());
        showPeca();
        clearFTPeca();
    }

    @FXML
    private void insertMedidaButton() throws Exception {
        Peca peca = pegarPecaDaLinha();
        Medida medida = new Medida(tfNomeMedida.getText(), Double.parseDouble(tfTamanhoMedida.getText()));
        medidaService.create(medida);
        pecaService.addMedida(peca.getId(), medida);
        showPeca();
        showMedida();
        selecionaPeca(peca.getId());
        clearFTMedida();
    }

    @FXML
    private void updateMedidaButton() throws Exception {
        Medida medida = pegarMedidaDaLinha();
        medidaService.update(medida.getId(), tfNomeMedida.getText(), Double.parseDouble(tfTamanhoMedida.getText()));
        Peca peca = pegarPecaDaLinha();
        tvMedidas.setItems(null);
        initialize(url, null);
        clearFTMedida();
    }

    @FXML
    private void deleteMedidaButton() {
        Medida medida = pegarMedidaDaLinha();
        medidaService.delete(medida.getId());
        Peca peca = pegarPecaDaLinha();
        tvMedidas.setItems(null);
        initialize(url, null);
        clearFTMedida();
    }

    @FXML
    private void onCliqueHome(MouseEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    public ObservableList<Peca> getPecaList(){
        ObservableList<Peca> pecaList = FXCollections.observableArrayList();
        List<Peca> pecas = pecaService.findAll();
        if (!pecas.isEmpty()) {
            pecaList.addAll(pecas);
        }
        return pecaList;
    }

    public void showPeca() {
        ObservableList<Peca> lista = getPecaList();

        tcId.setCellValueFactory(new PropertyValueFactory<Peca, Long>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<Peca, String>("nome"));
        tcPrecoBase.setCellValueFactory(new PropertyValueFactory<Peca, Double>("precoBase"));

        tvPecas.setItems(lista);
    }

    public ObservableList<Medida> getMedidaPecaList(){
        Peca peca = pegarPecaDaLinha();
        ObservableList<Medida> medidaClienteList = FXCollections.observableArrayList();
        if (peca != null) {
            List<Medida> medidas = new ArrayList<>();
            medidas.addAll(peca.getMedidas());
            if (!medidas.isEmpty()) {
                medidaClienteList.addAll(medidas);
            }
        }
        return medidaClienteList;
    }

    private void showMedida() {
        ObservableList<Medida> listaMedidas = getMedidaPecaList();

        tcIdMedida.setCellValueFactory(new PropertyValueFactory<Medida, Long>("id"));
        tcNomeMedida.setCellValueFactory(new PropertyValueFactory<Medida, String>("nome"));
        tcTamanhoMedida.setCellValueFactory(new PropertyValueFactory<Medida, Double>("tamanho"));

        tvMedidas.setItems(listaMedidas);
    }

    public void clearFTPeca() {
        if (tfId != null) tfId.setText("");
        tfNome.setText("");
        tfPrecoBase.setText("");
    }

    private void clearFTMedida() {
        if (tfIdMedida != null) tfIdMedida.setText("");
        tfNomeMedida.setText("");
        tfTamanhoMedida.setText("");
    }

    public Peca pegarPecaDaLinha() {
        return tvPecas.getSelectionModel().getSelectedItem();
    }

    public Medida pegarMedidaDaLinha() {
        return tvMedidas.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onCliqueTvPeca(MouseEvent event) {
        showMedida();
    }

    public void selecionaPeca(Long id) {
        tvPecas.requestFocus();
        tvPecas.getSelectionModel().select(id.intValue() - 1);
        tvPecas.getFocusModel().focus(id.intValue() - 1);
        onCliqueTvPeca(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPeca();
        this.url = url;
    }
}
