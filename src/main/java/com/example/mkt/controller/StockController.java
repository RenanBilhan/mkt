package com.example.mkt.controller;

import com.example.mkt.dto.stock.StockInputDTO;
import com.example.mkt.dto.stock.StockOutputDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estoque")
public class StockController {

    private final StockService estoqueService;

    @GetMapping
    public ResponseEntity<List<StockOutputDTO>> findAll(){
        return new ResponseEntity<>(estoqueService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idEstoque}")
    public ResponseEntity<StockOutputDTO> findById(@PathVariable Integer idEstoque){
        return new ResponseEntity<>(estoqueService.findById(idEstoque), HttpStatus.OK);
    }

    @PostMapping("/{idProduto}")
    public ResponseEntity<StockOutputDTO> save(@PathVariable Integer idProduto, @RequestBody StockInputDTO estoqueInputDTO){
        return new ResponseEntity<>(estoqueService.save(idProduto, estoqueInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<StockOutputDTO> update(@PathVariable Integer idProduto, @RequestBody StockInputDTO estoqueInputDTO){
        return new ResponseEntity<>(estoqueService.update(idProduto, estoqueInputDTO), HttpStatus.OK);
    }

    @PutMapping("/reduceStock/{idOrder}")
    public ResponseEntity<List<StockOutputDTO>> reduceStock(@PathVariable Integer idOrder) throws BussinessRuleException {
        return new ResponseEntity<>(estoqueService.reduceStock(idOrder), HttpStatus.OK);
    }

}
