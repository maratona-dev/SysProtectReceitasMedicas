package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cenarios_teste")
public class CenarioTeste {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condicao_teste_id", nullable = false)
    private CondicaoTeste condicaoTeste;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "entrada_simulada", nullable = false, length = 255)
    private String entradaSimulada;

    @Column(name = "fraude_esperada", nullable = false)
    private Boolean fraudeEsperada;

    public CenarioTeste() {
    }

    public CenarioTeste(UUID id, CondicaoTeste condicaoTeste, String nome,
                        String entradaSimulada, Boolean fraudeEsperada) {
        this.id = id;
        this.condicaoTeste = condicaoTeste;
        this.nome = nome;
        this.entradaSimulada = entradaSimulada;
        this.fraudeEsperada = fraudeEsperada;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CondicaoTeste getCondicaoTeste() {
        return condicaoTeste;
    }

    public void setCondicaoTeste(CondicaoTeste condicaoTeste) {
        this.condicaoTeste = condicaoTeste;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEntradaSimulada() {
        return entradaSimulada;
    }

    public void setEntradaSimulada(String entradaSimulada) {
        this.entradaSimulada = entradaSimulada;
    }

    public Boolean getFraudeEsperada() {
        return fraudeEsperada;
    }

    public void setFraudeEsperada(Boolean fraudeEsperada) {
        this.fraudeEsperada = fraudeEsperada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CenarioTeste that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}