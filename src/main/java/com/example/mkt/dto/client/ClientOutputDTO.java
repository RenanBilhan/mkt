package com.example.mkt.dto.client;

import com.example.mkt.entity.ClientEntity;
import com.example.mkt.entity.AddressEntity;
import com.example.mkt.util.ConversorImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientOutputDTO {

    private Integer idCliente;
    private String nomeCliente;
    private String emailCliente;
    private String cpf;
    private LocalDate dataNascimento;
    private String genero;
    private Set<AddressEntity> enderecos = new HashSet<>();
    private String fotoClienteString;
    private String telefone;

    public ClientOutputDTO(ClientEntity entity){
        BeanUtils.copyProperties(entity, this);
        String fotoString;
        if(entity.getFotoCliente() == null){
            fotoString = null;
        }else{
            fotoString = ConversorImage.converterParaBase(entity.getFotoCliente());
        }
        this.setFotoClienteString(fotoString);
    }


//    public ClienteOutputDTO(ClienteEntity entity) {
//        this.idCliente = entity.getIdCliente();
//        this.nomeCliente = entity.getNomeCliente();
//        this.emailCliente = entity.getEmailCliente();
//        this.cpf = entity.getCpf();
//        this.dataNascimento = entity.getDataNascimento();
//        this.genero = entity.getGenero();
//        this.enderecos = entity.getEnderecos();
//        this.fotoCliente = entity.getFotoCliente();
//        this.telefone = entity.getTelefone();
//    }
}
