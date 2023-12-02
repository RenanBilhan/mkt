package com.example.mkt.controller;

import com.example.mkt.dto.produto.ProdutoInputDTO;
import com.example.mkt.dto.produto.ProdutoOutputDTO;
import com.example.mkt.dto.produto.ProdutoUpdateDTO;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoOutputDTO>> findAll(){
        return new ResponseEntity<>(produtoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoOutputDTO> findById(@PathVariable Integer idProduto){
        return new ResponseEntity<>(produtoService.findById(idProduto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProdutoOutputDTO> save(ProdutoInputDTO produtoInputDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.save(produtoInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoOutputDTO> update(@PathVariable Integer idProduto, ProdutoUpdateDTO produtoUpdateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.update(idProduto, produtoUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idproduto}")
    public ResponseEntity<Void> delete(@PathVariable Integer idProduto){
        produtoService.delete(idProduto);
        return ResponseEntity.noContent().build();
    }

}
