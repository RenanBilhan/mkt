package com.example.mkt.controller;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.usuario.UsuarioOutputDTO;
import com.example.mkt.entity.UsuarioEntity;
import com.example.mkt.entity.enums.Cargo;
import com.example.mkt.security.TokenService;
import com.example.mkt.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/auth")
public class AuthController {
    public final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping
    public String auth (@RequestBody @Valid LoginInputDTO loginInputDTO) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginInputDTO.getLogin(),
                loginInputDTO.getSenha()
        );

        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        UsuarioEntity usuarioValidado = (UsuarioEntity) authentication.getPrincipal();

        if(usuarioValidado.getCargos().stream().toList().get(0).getNome().equals("ROLE_DESATIVADO")){
            throw new Exception("Usuario desativado");
        }
        return tokenService.generateToken(usuarioValidado);
    }

//    @GetMapping("/usuario-logado")
//    public UsuarioLogadoDTO recuperarUsuarioLogado() throws RegraDeNegocioException {
//
//
//        return usuarioService.getLoggedUser();
//    }
}
