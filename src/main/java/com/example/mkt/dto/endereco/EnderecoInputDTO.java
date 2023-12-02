package com.example.mkt.dto.endereco;

import com.example.mkt.entity.ClienteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoInputDTO {

    @NotNull(message = "Esse campo deve ser preenchido.")
    @NotEmpty
    @Size(min = 3, max = 255, message = "Deve conter no mínimo 3 caracteres e no máximo 255.")
    private String logradouro;

    @NotNull(message = "Esse campo deve ser preenchido.")
    @Pattern(regexp = "^[0-9]$", message = "Esse campo deve conter apenas numeros.")
    @Size(min = 1, max = 9, message = "O numero precisa ter no mínimo um digito e no máximo 9.")
    private String numero;

    @Size(min = 0, max = 255)
    private String complemento;

    @NotNull(message = "Esse campo deve ser preenchido.")
    @Size(min = 8, max = 8)
    @Pattern(regexp = "^[0-9]{8}$", message = "Esse campo deve conter apenas numeros.")
    private String cep;

    @NotNull(message = "Esse campo deve ser preenchido.")
    @NotEmpty
    @Size(min = 3, max = 255)
    private String cidade;

    @NotNull(message = "Esse campo deve ser preenchido.")
    @NotEmpty
    @Size(min = 3, max = 255)
    private String estado;

    @NotNull(message = "Esse campo deve ser preenchido.")
    @NotEmpty
    @Size(min = 2, max = 2)
    private String uf;
}
