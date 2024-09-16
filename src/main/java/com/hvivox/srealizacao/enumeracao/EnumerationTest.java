package com.hvivox.srealizacao.enumeracao;

public class EnumerationTest {
    public static void main(String[] args) {
        // Acesse os valores do enum
        DeliveryStatus status = DeliveryStatus.EM_TRANSITO;
        System.out.println("Status texto: " + status.getDescricao());
        System.out.println("Status numero: " + status.getCodigo());

        // Obtenha um enum a partir de um código
        int codigo = 1;
        DeliveryStatus statusFromCodigo = DeliveryStatus.fromCodigo(codigo);
        System.out.println("Status a partir do código " + codigo + ": " + statusFromCodigo.getDescricao());
    }
}