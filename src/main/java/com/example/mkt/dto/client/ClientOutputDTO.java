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

    private Integer idClient;
    private String nameClient;
    private String emailClient;
    private String cpf;
    private LocalDate dateOfBirth;
    private String gender;
    private Set<AddressEntity> addresses = new HashSet<>();
    private String photoClientString;
    private String phoneNumber;

    public ClientOutputDTO(ClientEntity entity){
        BeanUtils.copyProperties(entity, this);
        String fotoString;
        if(entity.getPhotoClient() == null){
            fotoString = null;
        }else{
            fotoString = ConversorImage.converterParaBase(entity.getPhotoClient());
        }
        this.setPhotoClientString(fotoString);
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
