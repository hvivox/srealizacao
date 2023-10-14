package com.hvivox.srealizacao.enumeracao;

public enum StatusEntrega {
    ENVIADO(0, "Enviado"),
    EM_TRANSITO(1, "Em Trânsito"),
    ENTREGUE(9, "Entregue");
    
    private final int codigo;
    private final String descricao;
    
    StatusEntrega(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public static StatusEntrega fromCodigo(int codigo) {
        for (StatusEntrega status : values()) {
            if (status.getCodigo() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }
}