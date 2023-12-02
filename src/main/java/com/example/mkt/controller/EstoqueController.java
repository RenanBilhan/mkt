package com.example.mkt.controller;

import com.example.mkt.dto.estoque.EstoqueInputDTO;
import com.example.mkt.dto.estoque.EstoqueOutputDTO;
import com.example.mkt.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<EstoqueOutputDTO>> findAll(){
        return new ResponseEntity<>(estoqueService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idEstoque}")
    public ResponseEntity<EstoqueOutputDTO> findById(@PathVariable Integer idEstoque){
        return new ResponseEntity<>(estoqueService.findById(idEstoque), HttpStatus.OK);
    }

    @PostMapping("/{idProduto}")
    public ResponseEntity<EstoqueOutputDTO> save(@PathVariable Integer idProduto, @RequestBody EstoqueInputDTO estoqueInputDTO){
        return new ResponseEntity<>(estoqueService.save(idProduto, estoqueInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<EstoqueOutputDTO> update(@PathVariable Integer idProduto, @RequestBody EstoqueInputDTO estoqueInputDTO){
        return new ResponseEntity<>(estoqueService.update(idProduto, estoqueInputDTO), HttpStatus.OK);
    }

}
