package com.example.mkt.dto.cliente;

import com.example.mkt.dto.usuario.UsuarioOutputDTO;
import com.example.mkt.entity.ClienteEntity;
import com.example.mkt.entity.EnderecoEntity;
import com.example.mkt.entity.UsuarioEntity;
import com.example.mkt.util.ConversorImagem;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ClienteOutputDTO {

    private Integer idCliente;
    private String nomeCliente;
    private String emailCliente;
    private String cpf;
    private LocalDate dataNascimento;
    private String genero;
    private Set<EnderecoEntity> enderecos = new HashSet<>();
    private String fotoClienteString;
    private String telefone;

    public ClienteOutputDTO(ClienteEntity entity){
        BeanUtils.copyProperties(entity, this);
        String fotoString;
        if(entity.getFotoCliente() == null){
            fotoString = null;
        }else{
            fotoString = ConversorImagem.converterParaBase(entity.getFotoCliente());
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
