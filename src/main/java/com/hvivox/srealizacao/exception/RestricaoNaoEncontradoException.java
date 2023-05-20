package com.hvivox.srealizacao.exception;

public class RestricaoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestricaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RestricaoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de estado com código %d", estadoId));
	}
	
}
