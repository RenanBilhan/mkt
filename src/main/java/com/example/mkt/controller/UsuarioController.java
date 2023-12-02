package com.example.mkt.controller;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.usuario.UsuarioLogadoDTO;
import com.example.mkt.dto.usuario.UsuarioOutputDTO;
import com.example.mkt.entity.UsuarioEntity;
import com.example.mkt.entity.enums.Cargo;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.repository.CargoRepository;
import com.example.mkt.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final CargoRepository cargoRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioOutputDTO>> findAll(){
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioOutputDTO> getById(@PathVariable @Positive Integer idUsuario){
        return new ResponseEntity<>(usuarioService.getById(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/logado")
    public ResponseEntity<UsuarioLogadoDTO> getLogedUser() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLogedUser(), HttpStatus.OK);
    }

    @GetMapping("/logado/id")
    public ResponseEntity<Integer> getIdLogedUser() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getIdLogedUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioOutputDTO> save(@RequestBody @Valid LoginInputDTO loginInputDTO, Cargo cargo){
        return new ResponseEntity<>(usuarioService.cadastrarAdmin(loginInputDTO, cargo), HttpStatus.CREATED);
    }
}
