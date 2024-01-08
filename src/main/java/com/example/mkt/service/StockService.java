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

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public List<StockOutputDTO> findAll(){
        return stockRepository.findAll().stream()
                .map(StockOutputDTO::new)
                .toList();
    }

    public StockOutputDTO findById(Integer idStock){
        return stockRepository.findById(idStock).map(StockOutputDTO::new)
                .orElseThrow(() -> new EntitiesNotFoundException("Stock not found."));
    }

    public StockOutputDTO save(Integer idProduct, StockInputDTO stockInputDTO){

        ProductEntity product = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntitiesNotFoundException("Product not found."));

        StockEntity newStock = new StockEntity();
        newStock.setProduct(product);
        BeanUtils.copyProperties(stockInputDTO, newStock);

        StockEntity stock = stockRepository.save(newStock);

        StockOutputDTO stockRetorno = ConversorMapper.converter(stock, StockOutputDTO.class);
        return stockRetorno;
    }

    public StockOutputDTO update(Integer idProduct, StockInputDTO stockInputDTO){
        ProductEntity product = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntitiesNotFoundException("Product not found."));

        StockEntity stock = stockRepository.findAll().stream()
                        .filter(estoqueEntity -> estoqueEntity.getProduct().getIdProduct()==idProduct)
                        .toList()
                        .get(0);
        BeanUtils.copyProperties(stockInputDTO, stock);

        StockEntity updatedStock = stockRepository.save(stock);

        StockOutputDTO stockReturn = ConversorMapper.converter(stock, StockOutputDTO.class);
        return stockReturn;
    }

    public List<StockOutputDTO> reduceStock(Integer idOrder) throws BussinessRuleException {

        OrderEntity order = orderRepository.findById(idOrder).orElseThrow(() -> new EntitiesNotFoundException("Order not found."));

        List<OrderStockEntity> orderStocks = order.getItems();

        List<StockOutputDTO> outputDTOS = new ArrayList<>();

        List<OrderStockEntity> outOfStockItems = orderStocks.stream().filter(stock -> verifyOutOfStock(stock))
                .toList();

        if(outOfStockItems.size()>0){
            for (OrderStockEntity stock: outOfStockItems){
                System.out.println("Unable to complete the order because the item "+ stock.getEstoque().getProduct().getNameProduct() + " on size "+ stock.getEstoque().getSize() + " is out of Stock.");
            }

            throw new BussinessRuleException("Products out of Stock.");
        }

        for (OrderStockEntity orderStock : orderStocks){
            if (verifyOutOfStock(orderStock)){
                throw new BussinessRuleException("Out of stock.");
            }

            StockEntity stockToUpdate = orderStock.getEstoque();

            stockToUpdate.setQuantity(stockToUpdate.getQuantity()- orderStock.getQuantity());
            StockEntity updatedStock = stockRepository.save(stockToUpdate);

            outputDTOS.add(ConversorMapper.converter(updatedStock, StockOutputDTO.class));
        }
        return outputDTOS;
    }

    public boolean verifyOutOfStock(OrderStockEntity orderStock) {

        return orderStock.getQuantity() > orderStock.getEstoque().getQuantity();

    }
}
