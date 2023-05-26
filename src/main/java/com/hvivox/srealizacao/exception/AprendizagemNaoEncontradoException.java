package com.hvivox.srealizacao.exception;

public class AprendizagemNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public AprendizagemNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public AprendizagemNaoEncontradoException(Long AprendizagemId) {
		this(String.format("Não existe um cadastro de aprendizagem com código %d", AprendizagemId));
	}
	
}
