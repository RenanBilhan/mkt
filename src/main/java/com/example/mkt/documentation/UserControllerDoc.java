package com.example.mkt.documentation;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.user.LoggedinUserDTO;
import com.example.mkt.dto.user.UserOutputDTO;
import com.example.mkt.entity.enums.Role;
import com.example.mkt.exceptions.BussinessRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

public interface UserControllerDoc {

    @Operation(summary = "List all users.", description = "Retrive an ArrayList with all users of the database in DTOs (the ID in the DTO can be used to access the entity, if necessary).")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the users list."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the users list."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> findAll();

    @Operation(summary = "Retrive the user.", description = "Retrive a user who's id corresponds to the id sent on the arguments.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the user."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the user."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UserOutputDTO> getById(@PathVariable @Positive Integer idUsuario);

    @Operation(summary = "Retrive the user.", description = "Retrive a user who has logged in the application.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the user."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the user."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/logado")
    public ResponseEntity<LoggedinUserDTO> getLogedUser() throws BussinessRuleException;

    @Operation(summary = "Retrive the user id.", description = "Retrive a user id who has logged in the application.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return the user id."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to access the user id."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @GetMapping("/logado/id")
    public ResponseEntity<Integer> getIdLogedUser() throws BussinessRuleException;

    @Operation(summary = "Save a new user.", description = "Save a new user on the database. You can send the user information by the LoginInputDTO and send the role by the parameters.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Return a UserOutputDTO with the information and the user ID."),
                    @ApiResponse(responseCode = "400", description = "It wasn't possible to save the user."),
                    @ApiResponse(responseCode = "403", description = "You have no permission to save a user."),
                    @ApiResponse(responseCode = "500", description = "The system threw an exception.")
            }
    )
    @PostMapping
    public ResponseEntity<UserOutputDTO> save(@RequestBody @Valid LoginInputDTO loginInputDTO, Role cargo);
}
