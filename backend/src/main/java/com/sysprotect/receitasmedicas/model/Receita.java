package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "codigo_receita", nullable = false, unique = true, length = 64)
    private String codigoReceita;

    @Column(name = "hash_conteudo", nullable = false, unique = true, length = 128)
    private String hashConteudo;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Column(name = "assinatura_digital", columnDefinition = "TEXT")
    private String assinaturaDigital;

    @Column(name = "assinatura_valida", nullable = false)
    private Boolean assinaturaValida = false;

    @Column(name = "validade_ok", nullable = false)
    private Boolean validadeOk = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceitaItem> itens = new ArrayList<>();

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fraude> fraudes = new ArrayList<>();

    public Receita() {
    }

    public Receita(UUID id, String codigoReceita, String hashConteudo, LocalDateTime dataEmissao,
                   LocalDate dataValidade, String assinaturaDigital, Boolean assinaturaValida,
                   Boolean validadeOk, Paciente paciente, Medico medico) {
        this.id = id;
        this.codigoReceita = codigoReceita;
        this.hashConteudo = hashConteudo;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
        this.assinaturaDigital = assinaturaDigital;
        this.assinaturaValida = assinaturaValida != null ? assinaturaValida : false;
        this.validadeOk = validadeOk != null ? validadeOk : true;
        this.paciente = paciente;
        this.medico = medico;
    }

    public void adicionarItem(ReceitaItem item) {
        itens.add(item);
        item.setReceita(this);
    }

    public void adicionarFraude(Fraude fraude) {
        fraudes.add(fraude);
        fraude.setReceita(this);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCodigoReceita() {
        return codigoReceita;
    }

    public void setCodigoReceita(String codigoReceita) {
        this.codigoReceita = codigoReceita;
    }

    public String getHashConteudo() {
        return hashConteudo;
    }

    public void setHashConteudo(String hashConteudo) {
        this.hashConteudo = hashConteudo;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getAssinaturaDigital() {
        return assinaturaDigital;
    }

    public void setAssinaturaDigital(String assinaturaDigital) {
        this.assinaturaDigital = assinaturaDigital;
    }

    public Boolean getAssinaturaValida() {
        return assinaturaValida;
    }

    public void setAssinaturaValida(Boolean assinaturaValida) {
        this.assinaturaValida = assinaturaValida;
    }

    public Boolean getValidadeOk() {
        return validadeOk;
    }

    public void setValidadeOk(Boolean validadeOk) {
        this.validadeOk = validadeOk;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<ReceitaItem> getItens() {
        return itens;
    }

    public void setItens(List<ReceitaItem> itens) {
        this.itens = itens;
    }

    public List<Fraude> getFraudes() {
        return fraudes;
    }

    public void setFraudes(List<Fraude> fraudes) {
        this.fraudes = fraudes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receita receita)) return false;
        return Objects.equals(id, receita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}