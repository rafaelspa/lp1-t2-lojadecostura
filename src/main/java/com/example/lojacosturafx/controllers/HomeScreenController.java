package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.servicos.LogIOService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HomeScreenController {

    private final JavaFxApplication javaFxApplication;

    @FXML
    public Button ColorButton;

    @FXML
    private Button AddonButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button budgetButton;

    @FXML
    private Button clientButton;

    @FXML
    private Button fabricButton;

    @FXML
    private Hyperlink logoutLink;

    @FXML
    private Button modelsButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button piecesButton;

    @FXML
    void AddonAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("adicionais");
    }

    @FXML
    void accountAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("conta");
    }

    @FXML
    void budgetAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("orcamento");
    }

    @FXML
    void clientAction(MouseEvent event) {
        javaFxApplication.publicarContextoPagina("clientes");
    }

    @FXML
    void fabricAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("tecidos");
    }

    @FXML
    void logoutAction(ActionEvent event) {
        LogIOService.setLogged(false);
        LogIOService.setLoggedUser(null);
        javaFxApplication.publicarContextoPagina("login");
    }

    @FXML
    void modelsAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("modelos");
    }

    @FXML
    void orderAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("pedidos");
    }

    @FXML
    void piecesAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("pecas");
    }

    @FXML
    public void ColorAction(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("cores");
    }
}