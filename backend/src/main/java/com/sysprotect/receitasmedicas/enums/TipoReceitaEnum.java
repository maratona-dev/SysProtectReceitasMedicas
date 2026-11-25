package com.sysprotect.receitasmedicas.enums;

public enum TipoReceitaEnum {

    RECEITA_BRANCA(1, "Receita Branca"),
    RECEITA_AMARELA(2, "Receita Amarela"),
    RECEITA_AZUL(3, "Receita Azul");

    private final Integer codigo;
    private final String nome;

    TipoReceitaEnum(Integer codigo, String nome) {
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
        for (TipoReceitaEnum tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo.nome;
            }
        }

        return null;
    }
}