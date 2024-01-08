package com.example.mkt.documentation;

import com.example.mkt.dto.stock.StockInputDTO;
import com.example.mkt.dto.stock.StockOutputDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface StockControllerDoc {

    @Operation(summary = "List all stocks.", description = "Retrive an ArrayList with all stocks of the database in DTOs (the ID in the DTO can be used to access the entity, if necessary).")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the stocks list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the stocks list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping
    public ResponseEntity<List<StockOutputDTO>> findAll();

    @Operation(summary = "Retrive the stock.", description = "Retrive a stock who's id corresponds to the id sent on the arguments.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the stock."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the stock."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/{idEstoque}")
    public ResponseEntity<StockOutputDTO> findById(@PathVariable Integer idEstoque);

    @Operation(summary = "Save a new stock.", description = "Save a new stock on the database. You can send the stock information by the StockInputDTO and the product id. Must create a Product first on the ProductController.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a StockOutputDTO."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to save the stock."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to save a stock."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping("/{idProduto}")
    public ResponseEntity<StockOutputDTO> save(@PathVariable Integer idProduto, @RequestBody StockInputDTO estoqueInputDTO);

    @Operation(summary = "Update a stock information.", description = "Update a stock information in the database. You can send the StockInputDTO and the product id by the parameters.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a StockOutputDTO with the updated information."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to update the stock."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to update that stock."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PutMapping("/{idProduto}")
    public ResponseEntity<StockOutputDTO> update(@PathVariable Integer idProduto, @RequestBody StockInputDTO estoqueInputDTO);

    @Operation(summary = "Reduces the quantity of a stock.", description = "Reduces the quantity of a stock, based on a order.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a StockOutputDTO with the updated information."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to update the stock."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to update that stock."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PutMapping("/reduceStock/{idOrder}")
    public ResponseEntity<List<StockOutputDTO>> reduceStock(@PathVariable Integer idOrder) throws BussinessRuleException;
}
