package com.example.mkt.controller;

import com.example.mkt.documentation.AddressControllerDoc;
import com.example.mkt.dto.address.AddressInputDTO;
import com.example.mkt.dto.address.AddressOutputDTO;
import com.example.mkt.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class AddressController implements AddressControllerDoc {

    private final AddressService addressService;

    @GetMapping()
    public ResponseEntity<List<AddressOutputDTO>> findAll(){
        return new ResponseEntity<>(addressService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/cliente/{idClient}")
    public ResponseEntity<List<AddressOutputDTO>> findByIdCliente(@PathVariable Integer idClient){
        return new ResponseEntity<>(addressService.findByIdCliente(idClient), HttpStatus.OK);
    }
    @PostMapping("/{idClient}")
    public ResponseEntity<AddressOutputDTO> save(@RequestBody @Valid AddressInputDTO addressInputDTO, @PathVariable Integer idClient){
        return new ResponseEntity<>(addressService.save(addressInputDTO, idClient), HttpStatus.CREATED);
    }

}
