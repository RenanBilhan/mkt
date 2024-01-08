package com.example.mkt.documentation;

import com.example.mkt.dto.stock.StockOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Operation(summary = "Retrive the client.", description = "Retrive a client who's id corresponds to the id sent on the arguments.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the client."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the client."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/{idEstoque}")
    public ResponseEntity<StockOutputDTO> findById(@PathVariable Integer idEstoque);
}
