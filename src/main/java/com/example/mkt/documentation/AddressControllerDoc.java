package com.example.mkt.documentation;

import com.example.mkt.dto.address.AddressOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface AddressControllerDoc {

    @Operation(summary = "List all address.", description = "List all address of the database.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the address list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the addresses list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping
    public ResponseEntity<List<AddressOutputDTO>> getAll();
}
