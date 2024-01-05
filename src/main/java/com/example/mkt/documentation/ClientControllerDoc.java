package com.example.mkt.documentation;

import com.example.mkt.dto.client.ClientInputDTO;
import com.example.mkt.dto.client.ClientOutputDTO;
import com.example.mkt.dto.message.StatusMessage;
import com.example.mkt.entity.enums.PersonGender;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.exceptions.FormatNotValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;

public interface ClientControllerDoc {

    @Operation(summary = "List all clients.", description = "Retrive an ArrayList with all clients of the database in DTOs (the ID in the DTO can be used to access the entity, if necessary).")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the clients list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the clients list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping()
    public ResponseEntity<List<ClientOutputDTO>> findAll();

    @Operation(summary = "Retrive the client.", description = "Retrive a client who's id corresponds to the id sent on the arguments.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the client."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the client."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/{idClient}")
    public ResponseEntity<ClientOutputDTO> findById(@PathVariable @Positive Integer idClient);

    @Operation(summary = "Retrive the photo of a client.", description = "Retrive the photo of a client who's id corresponds to the id passed on the arguments.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the client's photo."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the client's photo."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/foto/{idClient}")
    public ResponseEntity<String> findClienteFoto(@PathVariable @Positive Integer idClient) throws BussinessRuleException;

    @Operation(summary = "Save a new client.", description = "Save a new client on the database. You can send the client information by the ClientInputDTO and the user id. Must create a User first on the UserController.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a ClientOutputDTO with the information and the client ID, which is not the same of the user id."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to save the client."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to save a client."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping("/usuario/{idUser}")
    public ResponseEntity<ClientOutputDTO> save(PersonGender personGender, @RequestBody @Valid ClientInputDTO clientInputDTO, @PathVariable Integer idUser);

    @Operation(summary = "Update a client information.", description = "Update a client information in the database. You can send the ClientInputDTO and the client id by the arguments.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a ClientOutputDTO with the updated information."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to update the client."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to update that client."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PutMapping("{idClient}")
    public ResponseEntity<ClientOutputDTO> update(PersonGender personGender, @PathVariable Integer idClient, @RequestBody @Valid ClientInputDTO clientInputDTO);

    @Operation(summary = "Update a client photo.", description = "Update a client photo in the database. You can send the client photo by a MultipartFile and the client id.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return an StatusMessage."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to update this photo."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to update the photos of that client."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PutMapping("/{idClient}/foto")
    public ResponseEntity<StatusMessage> updateFoto(@RequestBody MultipartFile photo, @PathVariable @Positive(message = "Formato de ID não aceito.") Integer idClient) throws FormatNotValid, IOException;
}
