package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "condicoes_teste")
public class CondicaoTeste {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_fraude_id", nullable = false)
    private TipoFraude tipoFraude;

    @Column(name = "campo_alvo", nullable = false, length = 100)
    private String campoAlvo;

    @Column(name = "operador", nullable = false, length = 20)
    private String operador;

    @Column(name = "valor_esperado", nullable = false, length = 100)
    private String valorEsperado;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa = true;

    @OneToMany(mappedBy = "condicaoTeste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CenarioTeste> cenariosTeste = new ArrayList<>();

    public CondicaoTeste() {
    }

    public CondicaoTeste(UUID id, TipoFraude tipoFraude, String campoAlvo, String operador,
                         String valorEsperado, Boolean ativa) {
        this.id = id;
        this.tipoFraude = tipoFraude;
        this.campoAlvo = campoAlvo;
        this.operador = operador;
        this.valorEsperado = valorEsperado;
        this.ativa = ativa != null ? ativa : true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipoFraude getTipoFraude() {
        return tipoFraude;
    }

    public void setTipoFraude(TipoFraude tipoFraude) {
        this.tipoFraude = tipoFraude;
    }

    public String getCampoAlvo() {
        return campoAlvo;
    }

    public void setCampoAlvo(String campoAlvo) {
        this.campoAlvo = campoAlvo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getValorEsperado() {
        return valorEsperado;
    }

    public void setValorEsperado(String valorEsperado) {
        this.valorEsperado = valorEsperado;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public List<CenarioTeste> getCenariosTeste() {
        return cenariosTeste;
    }

    public void setCenariosTeste(List<CenarioTeste> cenariosTeste) {
        this.cenariosTeste = cenariosTeste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CondicaoTeste that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}