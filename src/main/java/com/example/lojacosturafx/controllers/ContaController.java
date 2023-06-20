package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.servicos.LogIOService;
import com.example.lojacosturafx.servicos.OrcamentoService;
import com.example.lojacosturafx.servicos.PedidoService;
import com.example.lojacosturafx.servicos.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@RequiredArgsConstructor
@Component
public class ContaController implements Initializable{
    private final JavaFxApplication javaFxApplication;
    private final OrcamentoService orcamentoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;
    @FXML
    private Hyperlink deletarConta;

    @FXML
    private Button homeScreenButton;

    @FXML
    private Label loggedUserEmail;

    @FXML
    private Label loggedUsername;

    @FXML
    private Hyperlink logoutLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggedUserDetails();
    }

    void LoggedUserDetails(){
        loggedUsername.setText(LogIOService.getLoggedUser().getNomeUsuario());
        loggedUserEmail.setText(LogIOService.getLoggedUser().getEmailUsuario());
    }
        

    @FXML 
    void DeletarContaClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deletar Conta");
        alert.setContentText("Tem certeza que deseja deletar a conta?");

        ButtonType confirmButton = new ButtonType("Deletar");
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);


        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {

            if (pedidoService.findAll().size() > 0) {
                pedidoService.findAll().stream()
                        .filter((pedido) ->
                                orcamentoService.findById(pedido.getOrcamento()).isPresent()
                                        && orcamentoService.findById(pedido.getOrcamento()).get().getUsuarioId()
                                        .equals(LogIOService.getLoggedID()))
                        .forEach(pedidoService::delete);
            }

            if (orcamentoService.findAll().size() > 0) {
                orcamentoService.findAll().stream()
                        .filter((orcamento) -> orcamento.getUsuarioId().equals(LogIOService.getLoggedID()))
                        .forEach(orcamentoService::delete);
            }

            usuarioService.findAll().stream()
                    .filter((usuario) -> usuario.getId().equals(LogIOService.getLoggedID()))
                    .forEach(usuarioService::delete);

            LogIOService.setLogged(false);
            LogIOService.setLoggedID(null);
            LogIOService.setLoggedUser(null);

            javaFxApplication.publicarContextoPagina("login");
        }
    }

    @FXML
    void HomeScreenButtonClick(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    @FXML
    void LogoutLinkClick(ActionEvent event) {
        LogIOService.setLogged(false);
        LogIOService.setLoggedUser(null);
        javaFxApplication.publicarContextoPagina("login");
    }
}

