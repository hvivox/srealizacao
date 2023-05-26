package com.hvivox.srealizacao.exception;

public class GratidaoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public GratidaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GratidaoNaoEncontradoException(Long gratidaoId) {
		this(String.format("Não existe um cadastro de gratidão com código %d", gratidaoId));
	}
	
}
