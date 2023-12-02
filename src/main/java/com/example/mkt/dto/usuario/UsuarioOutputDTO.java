package com.example.mkt.dto.usuario;

import com.example.mkt.entity.CargoEntity;
import com.example.mkt.entity.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioOutputDTO {

    private Integer idUsuario;
    private String login;
    private String senha;
    private Set<CargoEntity> cargo = new HashSet<>();

    public UsuarioOutputDTO(UsuarioEntity entity) {
        BeanUtils.copyProperties(entity, this);
        this.setCargo(entity.getCargos());
    }
}
