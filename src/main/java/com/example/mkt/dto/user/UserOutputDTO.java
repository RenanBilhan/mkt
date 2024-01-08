package com.example.mkt.dto.user;

import com.example.mkt.entity.RoleEntity;
import com.example.mkt.entity.UserEntity;
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
public class UserOutputDTO {

    private Integer idUser;
    private String login;
    private String password;
    private Set<RoleEntity> role = new HashSet<>();

    public UserOutputDTO(UserEntity entity) {
        BeanUtils.copyProperties(entity, this);
        this.setRole(entity.getCargos());
    }
}
