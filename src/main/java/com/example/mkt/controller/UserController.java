package com.example.mkt.controller;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.user.LoggedinUserDTO;
import com.example.mkt.dto.user.UserOutputDTO;
import com.example.mkt.entity.enums.Role;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.RoleRepository;
import com.example.mkt.service.UserService;
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
public class UserController {
    private final UserService usuarioService;
    private final RoleRepository cargoRepository;

    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> findAll(){
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UserOutputDTO> getById(@PathVariable @Positive Integer idUsuario){
        return new ResponseEntity<>(usuarioService.getById(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/logado")
    public ResponseEntity<LoggedinUserDTO> getLogedUser() throws BussinessRuleException {
        return new ResponseEntity<>(usuarioService.getLogedUser(), HttpStatus.OK);
    }

    @GetMapping("/logado/id")
    public ResponseEntity<Integer> getIdLogedUser() throws BussinessRuleException {
        return new ResponseEntity<>(usuarioService.getIdLogedUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserOutputDTO> save(@RequestBody @Valid LoginInputDTO loginInputDTO, Role cargo){
        return new ResponseEntity<>(usuarioService.registerAdmin(loginInputDTO, cargo), HttpStatus.CREATED);
    }
}
