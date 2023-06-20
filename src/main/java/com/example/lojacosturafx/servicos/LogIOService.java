package com.example.lojacosturafx.servicos;

import com.example.lojacosturafx.entidades.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogIOService {
    private static @Getter @Setter Boolean logged;
    private static @Getter @Setter Long LoggedID;
    private static @Getter @Setter Usuario loggedUser;
}
