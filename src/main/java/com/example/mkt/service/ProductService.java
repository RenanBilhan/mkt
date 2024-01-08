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

    private final ProductRepository productRepository;

    public List<ProductOutputDTO> findAll(){
        return productRepository.findAll().stream()
                .map(ProductOutputDTO::new)
                .toList();
    }

    public ProductOutputDTO findById(Integer idProduct){

        return ConversorMapper.converter(productRepository.findById(idProduct)
                .orElseThrow(() -> new EntitiesNotFoundException("Product not found.")),
                ProductOutputDTO.class);

    }

    public ProductOutputDTO save(ProductInputDTO productInputDTO) throws BussinessRuleException {

        ProductEntity newProduct = new ProductEntity();

        BeanUtils.copyProperties(productInputDTO, newProduct);

        ProductEntity productSaved = productRepository.save(newProduct);

        if(productSaved == null){
            throw new BussinessRuleException("Invalid product.");
        }

        return ConversorMapper.converter(productSaved, ProductOutputDTO.class);
    }

    public ProductOutputDTO update(Integer idProduct, ProductUpdateDTO productUpdateDTO) throws BussinessRuleException {

        if(productUpdateDTO == null){
            throw new BussinessRuleException("Insert the informations to update.");
        }

        ProductEntity productToUpdate = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntitiesNotFoundException("Product not found."));

        if(productUpdateDTO.getNameProduct() != null){
           productToUpdate.setNameProduct(productUpdateDTO.getNameProduct());
        }
        if(productUpdateDTO.getPrice() != null){
           productToUpdate.setPrice(productUpdateDTO.getPrice());
        }
        if(productUpdateDTO.getDescription() != null){
           productToUpdate.setDescription(productUpdateDTO.getDescription());
        }

        ProductEntity updatedProduct = productRepository.save(productToUpdate);

        return ConversorMapper.converter(updatedProduct, ProductOutputDTO.class);

    }

    public void delete(Integer idProduct){

        Optional<ProductEntity> productToDelete = productRepository.findById(idProduct);

        if(productToDelete.isPresent()){
            productRepository.delete(productToDelete.get());
        }
    }
}
