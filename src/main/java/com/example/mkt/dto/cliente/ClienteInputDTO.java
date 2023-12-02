package com.example.mkt.dto.cliente;

import com.example.mkt.entity.EnderecoEntity;
import com.example.mkt.entity.UsuarioEntity;
import com.example.mkt.entity.enums.GeneroPessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteInputDTO {

    @NotNull
    @NotEmpty
    @Schema(description = "Nome do cliente", example = "Renan Bilhan", required = true)
    private String nomeCliente;

    @NotNull
    @NotEmpty
    @Email
    @Schema(description = "Email do cliente", example = "renan@mail.com", required = true)
    private String emailCliente;

    @CPF
    @NotNull
    @NotEmpty
    @Schema(description = "CPF do cliente", example = "00000000000", required = true)
    private String cpf;

    @NotNull
    @DateTimeFormat
    private LocalDate dataNascimento;

    @NotNull
    @NotEmpty
    @Schema(description = "Telefone de contato do cliente", example = "000000000", required = false)
    @Size(min = 11, max = 11, message = "Telefone deve conter 11 digitos")
    @Pattern(regexp = "[0-9]+", message = "Deve conter apenas números.")
    private String telefone;
}
