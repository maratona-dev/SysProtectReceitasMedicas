package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "crm", nullable = false, unique = true, length = 20)
    private String crm;

    @Column(name = "uf_crm", nullable = false, length = 2)
    private String ufCrm;

    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @Column(name = "crm_ativo", nullable = false)
    private Boolean crmAtivo = true;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private List<Receita> receitas = new ArrayList<>();

    public Medico() {
    }

    public Medico(UUID id, String nome, String crm, String ufCrm, String especialidade, Boolean crmAtivo) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.ufCrm = ufCrm;
        this.especialidade = especialidade;
        this.crmAtivo = crmAtivo != null ? crmAtivo : true;
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getUfCrm() {
        return ufCrm;
    }

    public void setUfCrm(String ufCrm) {
        this.ufCrm = ufCrm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Boolean getCrmAtivo() {
        return crmAtivo;
    }

    public void setCrmAtivo(Boolean crmAtivo) {
        this.crmAtivo = crmAtivo;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medico medico)) return false;
        return Objects.equals(id, medico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}