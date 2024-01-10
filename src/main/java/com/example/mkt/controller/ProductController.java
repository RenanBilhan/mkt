package com.example.mkt.controller;

import com.example.mkt.dto.product.ProductInputDTO;
import com.example.mkt.dto.product.ProductOutputDTO;
import com.example.mkt.dto.product.ProductUpdateDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService produtoService;

    @GetMapping
    public ResponseEntity<List<ProductOutputDTO>> findAll(){
        return new ResponseEntity<>(produtoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProductOutputDTO> findById(@PathVariable Integer idProduto){
        return new ResponseEntity<>(produtoService.findById(idProduto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductOutputDTO> save(ProductInputDTO produtoInputDTO) throws BussinessRuleException {
        return new ResponseEntity<>(produtoService.save(produtoInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<ProductOutputDTO> update(@PathVariable Integer idProduto, ProductUpdateDTO produtoUpdateDTO) throws BussinessRuleException {
        return new ResponseEntity<>(produtoService.update(idProduto, produtoUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idproduto}")
    public ResponseEntity<Void> delete(@PathVariable Integer idProduto){
        produtoService.delete(idProduto);
        return ResponseEntity.noContent().build();
    }

}
