package com.example.mkt.service;

import com.example.mkt.dto.estoque.EstoqueInputDTO;
import com.example.mkt.dto.estoque.EstoqueOutputDTO;
import com.example.mkt.entity.EstoqueEntity;
import com.example.mkt.entity.ProdutoEntity;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.repository.EstoqueRepository;
import com.example.mkt.repository.ProdutoRepository;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public List<EstoqueOutputDTO> findAll(){
        return estoqueRepository.findAll().stream()
                .map(EstoqueOutputDTO::new)
                .toList();
    }

    public EstoqueOutputDTO findById(Integer idEstoque){
        return estoqueRepository.findById(idEstoque).map(EstoqueOutputDTO::new)
                .orElseThrow(() -> new EntitiesNotFoundException("Estoque não encontrado."));
    }

    public EstoqueOutputDTO save(Integer idProduto, EstoqueInputDTO estoqueInputDTO){

        ProdutoEntity produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntitiesNotFoundException("Produto não encontrado"));

        EstoqueEntity novoEstoque = new EstoqueEntity();
        novoEstoque.setProduto(produto);
        BeanUtils.copyProperties(estoqueInputDTO, novoEstoque);

        EstoqueEntity estoque = estoqueRepository.save(novoEstoque);

        EstoqueOutputDTO retorno = ConversorMapper.converter(estoque, EstoqueOutputDTO.class);
        return retorno;
    }

    public EstoqueOutputDTO update(Integer idProduto, EstoqueInputDTO estoqueInputDTO){
        ProdutoEntity produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntitiesNotFoundException("Produto não encontrado"));

        EstoqueEntity estoque = estoqueRepository.findAll().stream()
                        .filter(estoqueEntity -> estoqueEntity.getProduto().getIdProduto()==idProduto)
                        .toList()
                        .get(0);
        BeanUtils.copyProperties(estoqueInputDTO, estoque);

        EstoqueEntity estoqueAtualizado = estoqueRepository.save(estoque);

        EstoqueOutputDTO retorno = ConversorMapper.converter(estoque, EstoqueOutputDTO.class);
        return retorno;
    }
}
