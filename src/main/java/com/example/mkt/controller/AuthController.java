package com.example.mkt.controller;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.entity.UserEntity;
import com.example.mkt.security.TokenService;
import com.example.mkt.service.UserService;
import lombok.RequiredArgsConstructor;
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
    private final UserService usuarioService;

    @PostMapping
    public String auth (@RequestBody @Valid LoginInputDTO loginInputDTO) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginInputDTO.getLogin(),
                loginInputDTO.getSenha()
        );

        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        UserEntity validatedUser = (UserEntity) authentication.getPrincipal();

        if(validatedUser.getCargos().stream().toList().get(0).getNome().equals("ROLE_DESATIVADO")){
            throw new Exception("Unactive user");
        }
        return tokenService.generateToken(validatedUser);
    }

//    @GetMapping("/usuario-logado")
//    public UsuarioLogadoDTO recuperarUsuarioLogado() throws RegraDeNegocioException {
//
//
//        return usuarioService.getLoggedUser();
//    }
}
