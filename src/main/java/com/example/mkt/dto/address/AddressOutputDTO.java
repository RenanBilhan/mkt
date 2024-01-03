package com.example.mkt.dto.address;

import com.example.mkt.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressOutputDTO {

    private Integer idEndereco;

    private String logradouro;

    private String numero;

    private String complemento;

    private String cep;

    private String cidade;

    private String estado;

    private String uf;

    public AddressOutputDTO(AddressEntity enderecoEntity) {
        BeanUtils.copyProperties(enderecoEntity, this);
    }
}
