package com.example.mkt.documentation;

import com.example.mkt.dto.order.OrderInputDTO;
import com.example.mkt.dto.order.OrderOutputDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

public interface OrderControllerDoc {

    @Operation(summary = "List all orders.", description = "Retrive an ArrayList with all orders of the database in DTOs (the ID in the DTO can be used to access the entity, if necessary).")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the order list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the order list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping
    public ResponseEntity<List<OrderOutputDTO>> findAll();

    @Operation(summary = "Save a new order.", description = "Save a new order on the database. You can pass the order information by the OrderInputDTO and the client id. Must create a Client first on the ClientController.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return an OrderOutputDTO with the information and the client ID."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to save the order."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to save a order."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping("/{idCliente}")
    public ResponseEntity<OrderOutputDTO> save(@PathVariable Integer idCliente, @RequestBody OrderInputDTO pedidoInputDTO) throws BussinessRuleException, MessagingException;

}
