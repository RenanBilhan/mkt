package com.example.mkt.controller;

import com.example.mkt.documentation.ClientControllerDoc;
import com.example.mkt.dto.client.ClientInputDTO;
import com.example.mkt.dto.client.ClientOutputDTO;
import com.example.mkt.dto.message.StatusMessage;
import com.example.mkt.entity.enums.PersonGender;
import com.example.mkt.exceptions.FormatNotValid;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.service.ClientService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/client")
public class ClientController implements ClientControllerDoc {
    private final ClientService clientService;

    @GetMapping()
    public ResponseEntity<List<ClientOutputDTO>> findAll(){
        return  new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<ClientOutputDTO> findById(@PathVariable @Positive Integer idClient){
        return new ResponseEntity<>(clientService.findById(idClient), HttpStatus.OK);
    }

    @GetMapping("/foto/{idClient}")
    public ResponseEntity<String> findClienteFoto(@PathVariable @Positive Integer idClient) throws BussinessRuleException {
        String foto = clientService.findClientPhoto(idClient);
        return new ResponseEntity<>(foto, HttpStatus.OK);
    }

    @PostMapping("/usuario/{idUser}")
    public ResponseEntity<ClientOutputDTO> save(PersonGender personGender, @RequestBody @Valid ClientInputDTO clientInputDTO, @PathVariable Integer idUser){
        return new ResponseEntity<>(clientService.save(clientInputDTO, idUser, personGender), HttpStatus.CREATED);
    }

    @PutMapping("{idClient}")
    public ResponseEntity<ClientOutputDTO> update(PersonGender personGender, @PathVariable Integer idClient, @RequestBody @Valid ClientInputDTO clientInputDTO){
        return new ResponseEntity<>(clientService.update(idClient, clientInputDTO, personGender), HttpStatus.OK);
    }

    @PutMapping("/{idClient}/foto")
    public ResponseEntity<StatusMessage> updateFoto(@RequestBody MultipartFile photo, @PathVariable @Positive(message = "Formato de ID não aceito.") Integer idClient) throws FormatNotValid, IOException {
        return new ResponseEntity<>(clientService.updatePhotoClient(photo, idClient), HttpStatus.OK);
    }
}
