package com.example.mkt.controller;

import com.example.mkt.dto.cliente.ClienteInputDTO;
import com.example.mkt.dto.cliente.ClienteOutputDTO;
import com.example.mkt.dto.message.StatusMessage;
import com.example.mkt.dto.usuario.UsuarioLogadoDTO;
import com.example.mkt.entity.ClienteEntity;
import com.example.mkt.entity.UsuarioEntity;
import com.example.mkt.entity.enums.GeneroPessoa;
import com.example.mkt.exceptions.FormatNotValid;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<List<ClienteOutputDTO>> findAll(){
        return  new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteOutputDTO> findById(@PathVariable @Positive Integer idCliente){
        return new ResponseEntity<>(clienteService.findById(idCliente), HttpStatus.OK);
    }

    @GetMapping("/foto/{idCliente}")
    public ResponseEntity<String> findClienteFoto(@PathVariable @Positive Integer idCliente) throws RegraDeNegocioException {
        String foto = clienteService.findClienteFoto(idCliente);
        return new ResponseEntity<>(foto, HttpStatus.OK);
    }

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<ClienteOutputDTO> save(GeneroPessoa generoPessoa, @RequestBody @Valid ClienteInputDTO clienteInputDTO, @PathVariable Integer idUsuario){
        return new ResponseEntity<>(clienteService.save(clienteInputDTO, idUsuario, generoPessoa), HttpStatus.CREATED);
    }

    @PutMapping("{idCliente}")
    public ResponseEntity<ClienteOutputDTO> update(GeneroPessoa generoPessoa, Integer idCliente, @RequestBody @Valid ClienteInputDTO clienteInputDTO){
        return new ResponseEntity<>(clienteService.update(idCliente, clienteInputDTO, generoPessoa), HttpStatus.OK);
    }

    @PutMapping("/{idCliente}/foto")
    public ResponseEntity<StatusMessage> updateFoto(@RequestBody MultipartFile foto, @PathVariable @Positive(message = "Formato de ID não aceito.") Integer idCliente) throws FormatNotValid, IOException {
        return new ResponseEntity<>(clienteService.updateFotoCliente(foto, idCliente), HttpStatus.OK);
    }
}
