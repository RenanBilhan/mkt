package com.example.mkt.controller;

import com.example.mkt.dto.endereco.EnderecoInputDTO;
import com.example.mkt.dto.endereco.EnderecoOutputDTO;
import com.example.mkt.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity<List<EnderecoOutputDTO>> getAll(){
        return new ResponseEntity<>(enderecoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<EnderecoOutputDTO>> findByIdCliente(@PathVariable Integer idCliente){
        return new ResponseEntity<>(enderecoService.findByIdCliente(idCliente), HttpStatus.OK);
    }
    @PostMapping("/{idCliente}")
    public ResponseEntity<EnderecoOutputDTO> save(@RequestBody @Valid EnderecoInputDTO enderecoInputDTO, @PathVariable Integer idCliente){
        return new ResponseEntity<>(enderecoService.save(enderecoInputDTO, idCliente), HttpStatus.CREATED);
    }

}
