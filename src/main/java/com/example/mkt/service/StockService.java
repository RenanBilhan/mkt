package com.example.mkt.service;

import com.example.mkt.dto.stock.StockInputDTO;
import com.example.mkt.dto.stock.StockOutputDTO;
import com.example.mkt.entity.StockEntity;
import com.example.mkt.entity.OrderEntity;
import com.example.mkt.entity.OrderStockEntity;
import com.example.mkt.entity.ProductEntity;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.StockRepository;
import com.example.mkt.repository.OrderRepository;
import com.example.mkt.repository.ProductRepository;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository estoqueRepository;
    private final ProductRepository produtoRepository;
    private final OrderRepository pedidoRepository;

    public List<StockOutputDTO> findAll(){
        return estoqueRepository.findAll().stream()
                .map(StockOutputDTO::new)
                .toList();
    }

    public StockOutputDTO findById(Integer idEstoque){
        return estoqueRepository.findById(idEstoque).map(StockOutputDTO::new)
                .orElseThrow(() -> new EntitiesNotFoundException("Estoque não encontrado."));
    }

    public StockOutputDTO save(Integer idProduto, StockInputDTO estoqueInputDTO){

        ProductEntity produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntitiesNotFoundException("Produto não encontrado"));

        StockEntity novoEstoque = new StockEntity();
        novoEstoque.setProduto(produto);
        BeanUtils.copyProperties(estoqueInputDTO, novoEstoque);

        StockEntity estoque = estoqueRepository.save(novoEstoque);

        StockOutputDTO retorno = ConversorMapper.converter(estoque, StockOutputDTO.class);
        return retorno;
    }

    public StockOutputDTO update(Integer idProduto, StockInputDTO estoqueInputDTO){
        ProductEntity produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntitiesNotFoundException("Produto não encontrado"));

        StockEntity estoque = estoqueRepository.findAll().stream()
                        .filter(estoqueEntity -> estoqueEntity.getProduto().getIdProduto()==idProduto)
                        .toList()
                        .get(0);
        BeanUtils.copyProperties(estoqueInputDTO, estoque);

        StockEntity estoqueAtualizado = estoqueRepository.save(estoque);

        StockOutputDTO retorno = ConversorMapper.converter(estoque, StockOutputDTO.class);
        return retorno;
    }

    public List<StockOutputDTO> reduceStock(Integer idOrder) throws BussinessRuleException {

        OrderEntity order = pedidoRepository.findById(idOrder).orElseThrow(() -> new EntitiesNotFoundException("Order not found."));

        List<OrderStockEntity> orderStocks = order.getItens();

        List<StockOutputDTO> outputDTOS = new ArrayList<>();

        List<OrderStockEntity> outOfStockItems = orderStocks.stream().filter(stock -> verifyOutOfStock(stock))
                .toList();

        if(outOfStockItems.size()>0){
            for (OrderStockEntity stock: outOfStockItems){
                System.out.println("Unable to carry on complete the order because the item "+ stock.getEstoque().getProduto().getNomeProduto() + " on size "+ stock.getEstoque().getTamanho() + " is out of Stock.");
            }

            throw new BussinessRuleException("Products out of Stock.");
        }

        for (OrderStockEntity orderStock : orderStocks){
            if (verifyOutOfStock(orderStock)){
                throw new BussinessRuleException("Out of stock.");
            }

            StockEntity stockToUpdate = orderStock.getEstoque();

            stockToUpdate.setQuantidade(stockToUpdate.getQuantidade()- orderStock.getQuantidade());
            StockEntity updatedStock = estoqueRepository.save(stockToUpdate);

            outputDTOS.add(ConversorMapper.converter(updatedStock, StockOutputDTO.class));
        }
        return outputDTOS;
    }

    public boolean verifyOutOfStock(OrderStockEntity orderStock) {

        return orderStock.getQuantidade() > orderStock.getEstoque().getQuantidade();

    }
}
