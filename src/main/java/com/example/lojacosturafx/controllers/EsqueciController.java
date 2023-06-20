package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Usuario;
import com.example.lojacosturafx.servicos.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class EsqueciController {
    public final UsuarioService usuarioService;
    public final JavaFxApplication javaFxApplication;
    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    @FXML
    private Label lblSenha;

    @FXML
    private TextField tfEmail;

    @FXML
    void onCliqueEnviar(MouseEvent event) throws Exception {
        Usuario usuario = usuarioService.findByEmail(tfEmail.getText().toString());
        if (usuario == null) {
            throw new Exception("Não existe esse usuario");
        }
        lblSenha.setText(usuario.getSenhaUsuario());
        lblSenha.setVisible(true);
    }

    @FXML
    void onCliqueVolta(MouseEvent event) throws IOException {
//        javaFxApplication.mudarPagina("login");
        javaFxApplication.publicarContextoPagina("login");
    }

}
