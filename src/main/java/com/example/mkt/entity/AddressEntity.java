package com.example.mkt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ENDERECO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Integer idAddress;

    @Column(name = "LOGRADOURO")
    private String street;

    @Column(name = "NUMERO")
    private String number;

    @Column(name = "COMPLEMENTO")
    private String complement;

    @Column(name = "CEP")
    private String zipCode;

    @Column(name = "CIDADE")
    private String city;

    @Column(name = "ESTADO")
    private String state;

    @Column(name = "UF")
    private String federalUnity;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "CLIENTE_ENDERECO",
            joinColumns = @JoinColumn(name = "ID_ENDERECO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CLIENTE")
    )
    private Set<ClientEntity> clients = new HashSet<>();
}
