package com.hvivox.srealizacao.exception;

public class PrioridadeNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PrioridadeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PrioridadeNaoEncontradoException(Long prioridadeId) {
		this(String.format("Não existe um cadastro de prioridade com código %d", prioridadeId));
	}
	
}
