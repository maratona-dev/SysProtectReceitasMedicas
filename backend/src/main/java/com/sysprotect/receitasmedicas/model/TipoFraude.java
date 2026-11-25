package com.sysprotect.receitasmedicas.model;

import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tipos_fraude")
public class TipoFraude {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private CategoriaFraude codigo;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "severidade", nullable = false)
    private Integer severidade = 1;

    @OneToMany(mappedBy = "tipoFraude", fetch = FetchType.LAZY)
    private List<Fraude> fraudes = new ArrayList<>();

    @OneToMany(mappedBy = "tipoFraude", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CondicaoTeste> condicoesTeste = new ArrayList<>();

    public TipoFraude() {
    }

    public TipoFraude(UUID id, CategoriaFraude codigo, String descricao, Integer severidade) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.severidade = severidade != null ? severidade : 1;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CategoriaFraude getCodigo() {
        return codigo;
    }

    public void setCodigo(CategoriaFraude codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getSeveridade() {
        return severidade;
    }

    public void setSeveridade(Integer severidade) {
        this.severidade = severidade;
    }

    public List<Fraude> getFraudes() {
        return fraudes;
    }

    public void setFraudes(List<Fraude> fraudes) {
        this.fraudes = fraudes;
    }

    public List<CondicaoTeste> getCondicoesTeste() {
        return condicoesTeste;
    }

    public void setCondicoesTeste(List<CondicaoTeste> condicoesTeste) {
        this.condicoesTeste = condicoesTeste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoFraude that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}