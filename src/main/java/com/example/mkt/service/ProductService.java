package com.example.mkt.service;

import com.example.mkt.dto.product.ProductInputDTO;
import com.example.mkt.dto.product.ProductOutputDTO;
import com.example.mkt.dto.product.ProductUpdateDTO;
import com.example.mkt.entity.ProductEntity;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.ProductRepository;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository produtoRepository;

    public List<ProductOutputDTO> findAll(){
        return produtoRepository.findAll().stream()
                .map(ProductOutputDTO::new)
                .toList();
    }

    public ProductOutputDTO findById(Integer idProduto){

        return ConversorMapper.converter(produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntitiesNotFoundException("Produto não encontrado.")),
                ProductOutputDTO.class);

    }

    public ProductOutputDTO save(ProductInputDTO produtoInputDTO) throws BussinessRuleException {

        ProductEntity novoProduto = new ProductEntity();

        BeanUtils.copyProperties(produtoInputDTO, novoProduto);

        ProductEntity produtoSalvo = produtoRepository.save(novoProduto);

        if(produtoSalvo == null){
            throw new BussinessRuleException("Produto inválido");
        }

        return ConversorMapper.converter(produtoSalvo, ProductOutputDTO.class);
    }

    public ProductOutputDTO update(Integer idProduto, ProductUpdateDTO produtoUpdateDTO) throws BussinessRuleException {

        if(produtoUpdateDTO == null){
            throw new BussinessRuleException("Insira as informações a serem atualizadas.");
        }

        ProductEntity produtoAtualizar = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntitiesNotFoundException("Produto não encontrado."));

        if(produtoUpdateDTO.getNomeProduto() != null){
           produtoAtualizar.setNomeProduto(produtoUpdateDTO.getNomeProduto());
        }
        if(produtoUpdateDTO.getPreco() != null){
           produtoAtualizar.setPreco(produtoUpdateDTO.getPreco());
        }
        if(produtoUpdateDTO.getDescricao() != null){
           produtoAtualizar.setDescricao(produtoUpdateDTO.getDescricao());
        }

        ProductEntity produtoAtualizado = produtoRepository.save(produtoAtualizar);

        return ConversorMapper.converter(produtoAtualizado, ProductOutputDTO.class);

    }

    public void delete(Integer idProduto){

        Optional<ProductEntity> produtoDeletar = produtoRepository.findById(idProduto);

        if(produtoDeletar.isPresent()){
            produtoRepository.delete(produtoDeletar.get());
        }

    }
}
