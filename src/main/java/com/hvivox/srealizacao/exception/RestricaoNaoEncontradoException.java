package com.hvivox.srealizacao.exception;

public class RestricaoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestricaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RestricaoNaoEncontradoException(Long restricaoId) {
		this(String.format("Não existe um cadastro de restrição com código %d", restricaoId));
	}
	
}
