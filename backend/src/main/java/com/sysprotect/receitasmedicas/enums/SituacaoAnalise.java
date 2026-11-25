package com.sysprotect.receitasmedicas.enums;

public enum SituacaoAnalise {

    NORMAL(1, "Normal"),
    SUSPEITA(2, "Suspeita"),
    FRAUDE_A_CONFIRMAR(3, "Fraude a Confirmar"),
    FRAUDE_CONFIRMADA(4, "Fraude Confirmada por Operador"),
    FALSO_POSITIVO(5, "Falso Positivo");

    private final Integer codigo;
    private final String nome;

    SituacaoAnalise(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public static String obterNomePorCodigo(Integer codigo) {
        for (SituacaoAnalise situacao : values()) {
            if (situacao.codigo.equals(codigo)) {
                return situacao.nome;
            }
        }

        return null;
    }

    public static SituacaoAnalise obterPorCodigo(Integer codigo) {
        for (SituacaoAnalise situacao : values()) {
            if (situacao.codigo.equals(codigo)) {
                return situacao;
            }
        }

        throw new IllegalArgumentException(
            "Código de situação inválido: " + codigo
        );
    }
}