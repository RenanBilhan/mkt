package com.example.mkt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "PRODUTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO")
    private Integer idProduct;

    @Column(name = "NOME_PRODUTO")
    private String nameProduct;

    @Column(name = "PRECO")
    private Double price;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "IMAGEM", columnDefinition = "bytea")
    private byte[] image;
}
