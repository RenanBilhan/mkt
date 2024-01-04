package com.example.mkt.documentation;

import com.example.mkt.dto.address.AddressInputDTO;
import com.example.mkt.dto.address.AddressOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface AddressControllerDoc {

    @Operation(summary = "List all address.", description = "Retrive a ArrayList with all address of the database in DTOs (the ID in the DTO can be used to access the entity, if necessary) on a ResponseEntity.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the address list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the addresses list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping
    public ResponseEntity<List<AddressOutputDTO>> getAll();

    @Operation(summary = "List all address of the client.", description = "Retrive a ArrayList with all address of the database that are related to a client (pass the client ID in the arguments) in DTOs (the ID in the DTO can be used to access the entity, if necessary) on a ResponseEntity.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the clients address list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the addresses list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/cliente/{idClient}")
    public ResponseEntity<List<AddressOutputDTO>> findByIdCliente(@PathVariable Integer idClient);

    @Operation(summary = "Save a new address.", description = "Save a new address on the database. You can pass the address information by the AddressInputDTO and th owner of the address on the arguments. The owner must be registered on the sistem as a client.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return an AddressOutputDTO with the address information and ID."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to save the address."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to save an address."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )

    @PostMapping("/{idClient}")
    public ResponseEntity<AddressOutputDTO> save(@RequestBody @Valid AddressInputDTO addressInputDTO, @PathVariable Integer idClient);
}
