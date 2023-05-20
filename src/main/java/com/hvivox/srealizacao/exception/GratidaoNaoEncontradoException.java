package com.hvivox.srealizacao.exception;

public class GratidaoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public GratidaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GratidaoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de estado com código %d", estadoId));
	}
	
}
