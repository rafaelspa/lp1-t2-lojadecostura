package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Usuario;
import com.example.lojacosturafx.servicos.LogIOService;
import com.example.lojacosturafx.servicos.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoginController {
    public final JavaFxApplication javaFxApplication;
    public final UsuarioService usuarioService;
    public final LogIOService logIOService;

    @FXML
    private Label btnEsqueciASenha;

    @FXML
    private Button btnLogar;

    @FXML
    private Button btnCadastro;

    @FXML
    private Label lblErrors;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private TextField tfEmail;

    @FXML
    void onCliqueCadastro(MouseEvent event) throws IOException {
        javaFxApplication.publicarContextoPagina("cadastro");
    }

    @FXML
    void onCliqueEsqueciASenha(MouseEvent event) throws IOException {
        javaFxApplication.publicarContextoPagina("esqueci-senha");
    }

    @FXML
    void onCliqueLogar(MouseEvent event) throws Exception {
        Usuario usuario = usuarioService.findByEmail(tfEmail.getText());
        if (usuario == null) {
            tfEmail.setText("");
            tfSenha.setText("");
            throw new Exception("Usuario não encontrado");
        }
        if(usuario.getSenhaUsuario().equals(tfSenha.getText())){
            LogIOService.setLogged(true);
            LogIOService.setLoggedID(usuarioService.findByEmail(tfEmail.getText()).getId());
            LogIOService.setLoggedUser(usuarioService.findByEmail(tfEmail.getText()));
            javaFxApplication.publicarContextoPagina(("homeScreen"));
        } else {
            tfEmail.setText("");
            tfSenha.setText("");
            throw new Exception("Senha incorreta");
        }
    }
}
