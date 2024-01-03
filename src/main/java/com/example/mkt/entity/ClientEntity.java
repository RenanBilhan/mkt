package com.example.mkt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "CLIENTE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTE_SEQ")
    @SequenceGenerator(name = "CLIENTE_SEQ", sequenceName = "seq_cliente", allocationSize = 1)
    @Column(name = "ID_CLIENTE")
    private Integer idCliente;

    @Column(name = "NOME_CLIENTE")
    private String nomeCliente;

    @Column(name = "EMAIL")
    private String emailCliente;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "GENERO_CLIENTE")
    private String genero;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "CLIENTE_ENDERECO",
            joinColumns = @JoinColumn(name = "ID_CLIENTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_ENDERECO")
    )
    private Set<AddressEntity> enderecos = new HashSet<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private UserEntity usuarioEntity;

    @Lob
    @Column(name = "FOTO_CLIENTE")
    private byte[] fotoCliente;

    @Column(name = "TELEFONE")
    private String telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderEntity> pedidos;

}
