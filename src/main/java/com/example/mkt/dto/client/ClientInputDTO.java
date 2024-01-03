package com.example.mkt.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientInputDTO {

    @NotNull
    @NotEmpty
    @Schema(description = "Client name", example = "Orlando Silva", required = true)
    private String nameClient;

    @NotNull
    @NotEmpty
    @Email
    @Schema(description = "Client email", example = "abc@mail.com", required = true)
    private String emailClient;

    @CPF
    @NotNull
    @NotEmpty
    @Schema(description = "Client cpf (SSS in english)", example = "00000000000", required = true)
    private String cpf;

    @NotNull
    @DateTimeFormat
    private LocalDate dateOfBirth;

    @NotNull
    @NotEmpty
    @Schema(description = "Client phone number", example = "000000000", required = false)
    @Size(min = 11, max = 11, message = "Must contain 11 digits.")
    @Pattern(regexp = "[0-9]+", message = "Must contain only numbers.")
    private String phoneNumber;

}
