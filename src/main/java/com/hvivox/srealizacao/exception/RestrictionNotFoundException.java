package com.hvivox.srealizacao.exception;

public class RestrictionNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public RestrictionNotFoundException(String message) {
		super(message);
	}

	public RestrictionNotFoundException(Long restrictionId) {
		this(String.format("Não existe um cadastro de restrição com código %d", restrictionId));
	}

}
