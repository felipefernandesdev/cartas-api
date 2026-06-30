package com.hearthstone.cartas_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cartas")
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome e obrigatorio")
    @Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @Size(max = 500, message = "Descricao deve ter no maximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "Ataque e obrigatorio")
    @Min(value = 0, message = "Ataque deve ser no minimo 0")
    @Max(value = 10, message = "Ataque deve ser no maximo 10")
    @Column(nullable = false)
    private Integer ataque;

    @NotNull(message = "Defesa e obrigatoria")
    @Min(value = 0, message = "Defesa deve ser no minimo 0")
    @Max(value = 10, message = "Defesa deve ser no maximo 10")
    @Column(nullable = false)
    private Integer defesa;

    @NotNull(message = "Mana e obrigatoria")
    @Min(value = 0, message = "Mana deve ser no minimo 0")
    @Max(value = 10, message = "Mana deve ser no maximo 10")
    @Column(nullable = false)
    private Integer mana;

    @NotNull(message = "Tipo e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarta tipo;

    @NotNull(message = "Classe e obrigatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClasseCarta classe;

    public Carta() {
    }

    public Carta(String nome, String descricao, Integer ataque, Integer defesa, Integer mana, TipoCarta tipo, ClasseCarta classe) {
        this.nome = nome;
        this.descricao = descricao;
        this.ataque = ataque;
        this.defesa = defesa;
        this.mana = mana;
        this.tipo = tipo;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }

    public Integer getDefesa() {
        return defesa;
    }

    public void setDefesa(Integer defesa) {
        this.defesa = defesa;
    }

    public Integer getMana() {
        return mana;
    }

    public void setMana(Integer mana) {
        this.mana = mana;
    }

    public TipoCarta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCarta tipo) {
        this.tipo = tipo;
    }

    public ClasseCarta getClasse() {
        return classe;
    }

    public void setClasse(ClasseCarta classe) {
        this.classe = classe;
    }
}
