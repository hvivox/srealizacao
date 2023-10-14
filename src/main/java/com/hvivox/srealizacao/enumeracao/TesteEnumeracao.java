package com.hvivox.srealizacao.enumeracao;

public class TesteEnumeracao {
    public static void main(String[] args) {
        // Acesse os valores do enum
        StatusEntrega status = StatusEntrega.EM_TRANSITO;
        System.out.println("Status texto: " + status.getDescricao());
        System.out.println("Status numero: " + status.getCodigo());
        
        // Obtenha um enum a partir de um código
        int codigo = 1;
        StatusEntrega statusFromCodigo = StatusEntrega.fromCodigo(codigo);
        System.out.println("Status a partir do código " + codigo + ": " + statusFromCodigo.getDescricao());
    }
}