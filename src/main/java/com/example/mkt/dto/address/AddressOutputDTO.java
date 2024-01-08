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

    private Integer idAddress;

    private String Street;

    private String number;

    private String complement;

    private String zipCode;

    private String city;

    private String state;

    private String federalUnity;

    public AddressOutputDTO(AddressEntity enderecoEntity) {
        BeanUtils.copyProperties(enderecoEntity, this);
    }
}
