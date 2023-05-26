package com.hvivox.srealizacao.exception;

public class FolhaNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FolhaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FolhaNaoEncontradoException(Integer folhaId) {
		this(String.format("Não existe um cadastro de folha com código %d", folhaId));
	}
	
}
