package com.example.mkt.documentation;

import com.example.mkt.dto.product.ProductInputDTO;
import com.example.mkt.dto.product.ProductOutputDTO;
import com.example.mkt.dto.product.ProductUpdateDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductControllerDoc {
    @Operation(summary = "List all products.", description = "Retrive an ArrayList with all products of the database in DTOs (the ID in the DTO can be used to access the entity, if necessary).")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the products list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the products list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping
    public ResponseEntity<List<ProductOutputDTO>> findAll();

    @Operation(summary = "Retrive a product.", description = "Retrive a product in a DTO which's id corresponds to the id sent in the parameters. (the ID in the DTO can be used to access the entity, if necessary).")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the product."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the product."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/{idProduto}")
    public ResponseEntity<ProductOutputDTO> findById(@PathVariable Integer idProduto);

    @Operation(summary = "Save a new product.", description = "Save a product in the database. You can send the produtoInputDTO by the parameters.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a ProductOutputDTO with the product's information."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to save the product."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to save that product."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping
    public ResponseEntity<ProductOutputDTO> save(ProductInputDTO produtoInputDTO) throws BussinessRuleException;

    @Operation(summary = "Update a product information.", description = "Update a product information in the database. You can send the ProductUpdateDTO and the product id by the parameters.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a ProductOutputDTO with the updated information."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to update the product."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to update that product."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PutMapping("/{idProduto}")
    public ResponseEntity<ProductOutputDTO> update(@PathVariable Integer idProduto, ProductUpdateDTO produtoUpdateDTO);

    @Operation(summary = "Delete a product.", description = "Delete a product which's id corresponds to the id sent by the parameters.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Não há retorno."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @DeleteMapping("/{idproduto}")
    public ResponseEntity<Void> delete(@PathVariable Integer idProduto);
}
