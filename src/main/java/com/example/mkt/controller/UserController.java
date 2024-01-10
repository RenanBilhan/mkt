package com.example.mkt.controller;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.user.LoggedinUserDTO;
import com.example.mkt.dto.user.UserOutputDTO;
import com.example.mkt.entity.enums.Role;
import com.example.mkt.exceptions.BussinessRuleException;
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
@RequestMapping("/user")
public class UserController {
    private final UserService usuarioService;

    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> findAll(){
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UserOutputDTO> getById(@PathVariable @Positive Integer idUser){
        return new ResponseEntity<>(usuarioService.getById(idUser), HttpStatus.OK);
    }

    @GetMapping("/loged")
    public ResponseEntity<LoggedinUserDTO> getLogedUser() throws BussinessRuleException {
        return new ResponseEntity<>(usuarioService.getLogedUser(), HttpStatus.OK);
    }

    @GetMapping("/loged/id")
    public ResponseEntity<Integer> getIdLogedUser() throws BussinessRuleException {
        return new ResponseEntity<>(usuarioService.getIdLogedUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserOutputDTO> save(@RequestBody @Valid LoginInputDTO loginInputDTO){
        return new ResponseEntity<>(usuarioService.registerAdmin(loginInputDTO, Role.ROLE_USUARIO), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserOutputDTO> saveByAdmin(@RequestBody @Valid LoginInputDTO loginInputDTO, Role cargo){
        return new ResponseEntity<>(usuarioService.registerAdmin(loginInputDTO, cargo), HttpStatus.CREATED);
    }
}
