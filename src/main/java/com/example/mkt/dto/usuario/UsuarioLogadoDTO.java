package com.example.mkt.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioLogadoDTO {

    private Integer idUsuario;

    private String login;

    private boolean enabled;
}