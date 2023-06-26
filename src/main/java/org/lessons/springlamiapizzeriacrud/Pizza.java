package org.lessons.springlamiapizzeriacrud;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;
    private String descrizione;
    private String fotoUrl;
    @Column(nullable = false)
    private BigDecimal prezzo;



    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

}
