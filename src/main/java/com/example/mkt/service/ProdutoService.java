package com.example.mkt.service;

import com.example.mkt.dto.produto.ProdutoInputDTO;
import com.example.mkt.dto.produto.ProdutoOutputDTO;
import com.example.mkt.dto.produto.ProdutoUpdateDTO;
import com.example.mkt.entity.ProdutoEntity;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.repository.ProdutoRepository;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.support.RestGatewaySupport;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<ProdutoOutputDTO> findAll(){
        return produtoRepository.findAll().stream()
                .map(ProdutoOutputDTO::new)
                .toList();
    }

    public ProdutoOutputDTO findById(Integer idProduto){

        return ConversorMapper.converter(produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntitiesNotFoundException("Produto não encontrado.")),
                ProdutoOutputDTO.class);

    }

    public ProdutoOutputDTO save(ProdutoInputDTO produtoInputDTO) throws RegraDeNegocioException {

        ProdutoEntity novoProduto = new ProdutoEntity();

        BeanUtils.copyProperties(produtoInputDTO, novoProduto);

        ProdutoEntity produtoSalvo = produtoRepository.save(novoProduto);

        if(produtoSalvo == null){
            throw new RegraDeNegocioException("Produto inválido");
        }

        return ConversorMapper.converter(produtoSalvo, ProdutoOutputDTO.class);
    }

    public ProdutoOutputDTO update(Integer idProduto, ProdutoUpdateDTO produtoUpdateDTO) throws RegraDeNegocioException {

        if(produtoUpdateDTO == null){
            throw new RegraDeNegocioException("Insira as informações a serem atualizadas.");
        }

        ProdutoEntity produtoAtualizar = produtoRepository.findById(idProduto)
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

        ProdutoEntity produtoAtualizado = produtoRepository.save(produtoAtualizar);

        return ConversorMapper.converter(produtoAtualizado, ProdutoOutputDTO.class);

    }

    public void delete(Integer idProduto){

        Optional<ProdutoEntity> produtoDeletar = produtoRepository.findById(idProduto);

        if(produtoDeletar.isPresent()){
            produtoRepository.delete(produtoDeletar.get());
        }

    }
}
