package com.sysprotect.receitasmedicas.enums;

public enum CategoriaFraude {

    MEDICO_INVALIDO(1, "Médico Inválido"),
    CRM_INEXISTENTE(2, "CRM Inexistente"),
    RECEITA_ADULTERADA(3, "Receita Adulterada"),
    RECEITA_DUPLICADA(4, "Receita Duplicada"),
    PACIENTE_INEXISTENTE(5, "Paciente Inexistente"),
    MEDICAMENTO_INVALIDO(6, "Medicamento Inválido"),
    DOSAGEM_INVALIDA(7, "Dosagem Inválida"),
    RECEITA_VENCIDA(8, "Receita Vencida"),
    ASSINATURA_INVALIDA(9, "Assinatura Inválida"),
    RECEITA_FALSA(10, "Receita Falsa"),
    TALAO_SUSPENSO(11,"Talão Suspenso");

    private final Integer codigo;
    private final String nome;

    CategoriaFraude(Integer codigo, String nome) {
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
        for (CategoriaFraude categoria : values()) {
            if (categoria.codigo.equals(codigo)) {
                return categoria.nome;
            }
        }

        return null;
    }

    public static CategoriaFraude obterPorCodigo(Integer codigo) {
        for (CategoriaFraude categoria : values()) {
            if (categoria.codigo.equals(codigo)) {
                return categoria;
            }
        }

        throw new IllegalArgumentException(
            "Código de categoria de fraude inválido: " + codigo
        );
    }
}