package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "receita_itens")
public class ReceitaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receita_id", nullable = false)
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Column(name = "dosagem_prescrita", nullable = false, precision = 10, scale = 2)
    private BigDecimal dosagemPrescrita;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "posologia", length = 255)
    private String posologia;

    public ReceitaItem() {
    }

    public ReceitaItem(UUID id, Receita receita, Medicamento medicamento,
                       BigDecimal dosagemPrescrita, Integer quantidade, String posologia) {
        this.id = id;
        this.receita = receita;
        this.medicamento = medicamento;
        this.dosagemPrescrita = dosagemPrescrita;
        this.quantidade = quantidade;
        this.posologia = posologia;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public BigDecimal getDosagemPrescrita() {
        return dosagemPrescrita;
    }

    public void setDosagemPrescrita(BigDecimal dosagemPrescrita) {
        this.dosagemPrescrita = dosagemPrescrita;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReceitaItem that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}