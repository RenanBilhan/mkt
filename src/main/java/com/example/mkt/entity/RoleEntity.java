package com.example.mkt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "CARGO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQ")
    @SequenceGenerator(name = "CARGO_SEQ", sequenceName = "seq_cargo", allocationSize = 1)
    @Column(name = "ID_CARGO")
    private Integer idCargo;

    @Column(name = "NOME")
    private String nome;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "USUARIO_CARGO",
            joinColumns = @JoinColumn(name = "ID_CARGO"),
            inverseJoinColumns = @JoinColumn(name = "ID_USUARIO")
    )
    private Set<UserEntity> usuarios;



    @Override
    public String getAuthority() {
        return nome.toString();
    }
}
