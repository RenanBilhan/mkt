package com.example.mkt.dto.endereco;

import com.example.mkt.dto.cliente.ClienteOutputDTO;
import com.example.mkt.entity.ClienteEntity;
import com.example.mkt.entity.EnderecoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoOutputDTO {

    private Integer idEndereco;

    private String logradouro;

    private String numero;

    private String complemento;

    private String cep;

    private String cidade;

    private String estado;

    private String uf;

    public EnderecoOutputDTO(EnderecoEntity enderecoEntity) {
        BeanUtils.copyProperties(enderecoEntity, this);
    }
}
