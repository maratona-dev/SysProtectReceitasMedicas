package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "codigo_anvisa", unique = true, length = 30)
    private String codigoAnvisa;

    @Column(name = "principio_ativo", nullable = false, length = 150)
    private String principioAtivo;

    @Column(name = "dosagem_maxima_diaria", precision = 10, scale = 2)
    private BigDecimal dosagemMaximaDiaria;

    @Column(name = "unidade_medida", length = 20)
    private String unidadeMedida;

    @Column(name = "controlado", nullable = false)
    private Boolean controlado = false;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Medicamento() {
    }

    public Medicamento(UUID id, String nome, String codigoAnvisa, String principioAtivo,
                       BigDecimal dosagemMaximaDiaria, String unidadeMedida, Boolean controlado, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.codigoAnvisa = codigoAnvisa;
        this.principioAtivo = principioAtivo;
        this.dosagemMaximaDiaria = dosagemMaximaDiaria;
        this.unidadeMedida = unidadeMedida;
        this.controlado = controlado != null ? controlado : false;
        this.ativo = ativo != null ? ativo : true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoAnvisa() {
        return codigoAnvisa;
    }

    public void setCodigoAnvisa(String codigoAnvisa) {
        this.codigoAnvisa = codigoAnvisa;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public BigDecimal getDosagemMaximaDiaria() {
        return dosagemMaximaDiaria;
    }

    public void setDosagemMaximaDiaria(BigDecimal dosagemMaximaDiaria) {
        this.dosagemMaximaDiaria = dosagemMaximaDiaria;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Boolean getControlado() {
        return controlado;
    }

    public void setControlado(Boolean controlado) {
        this.controlado = controlado;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicamento that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}