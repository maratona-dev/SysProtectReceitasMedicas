package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "fraudes")
public class Fraude {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receita_id", nullable = false)
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_fraude_id", nullable = false)
    private TipoFraude tipoFraude;

    @Column(name = "data_deteccao", nullable = false)
    private LocalDateTime dataDeteccao = LocalDateTime.now();

    @Column(name = "detalhes_evidencia", columnDefinition = "TEXT")
    private String detalhesEvidencia;

    @Column(name = "resolvida", nullable = false)
    private Boolean resolvida = false;

    public Fraude() {
    }

    public Fraude(UUID id, Receita receita, TipoFraude tipoFraude, LocalDateTime dataDeteccao,
                  String detalhesEvidencia, Boolean resolvida) {
        this.id = id;
        this.receita = receita;
        this.tipoFraude = tipoFraude;
        this.dataDeteccao = dataDeteccao != null ? dataDeteccao : LocalDateTime.now();
        this.detalhesEvidencia = detalhesEvidencia;
        this.resolvida = resolvida != null ? resolvida : false;
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

    public TipoFraude getTipoFraude() {
        return tipoFraude;
    }

    public void setTipoFraude(TipoFraude tipoFraude) {
        this.tipoFraude = tipoFraude;
    }

    public LocalDateTime getDataDeteccao() {
        return dataDeteccao;
    }

    public void setDataDeteccao(LocalDateTime dataDeteccao) {
        this.dataDeteccao = dataDeteccao;
    }

    public String getDetalhesEvidencia() {
        return detalhesEvidencia;
    }

    public void setDetalhesEvidencia(String detalhesEvidencia) {
        this.detalhesEvidencia = detalhesEvidencia;
    }

    public Boolean getResolvida() {
        return resolvida;
    }

    public void setResolvida(Boolean resolvida) {
        this.resolvida = resolvida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fraude fraude)) return false;
        return Objects.equals(id, fraude.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}