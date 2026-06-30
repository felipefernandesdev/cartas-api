package com.hearthstone.cartas_api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "baralhos")
public class Baralho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome e obrigatorio")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Classe e obrigatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClasseCarta classe;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "baralho_cartas",
        joinColumns = @JoinColumn(name = "baralho_id"),
        inverseJoinColumns = @JoinColumn(name = "carta_id")
    )
    private List<Carta> cartas = new ArrayList<>();

    public Baralho() {
    }

    public Baralho(String nome, ClasseCarta classe) {
        this.nome = nome;
        this.classe = classe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ClasseCarta getClasse() {
        return classe;
    }

    public void setClasse(ClasseCarta classe) {
        this.classe = classe;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}
