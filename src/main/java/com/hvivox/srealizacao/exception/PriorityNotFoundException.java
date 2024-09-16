package com.hvivox.srealizacao.exception;

public class PriorityNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PriorityNotFoundException(String message) {
		super(message);
	}

	public PriorityNotFoundException(Long priorityId) {
		this(String.format("Não existe um cadastro de prioridade com código %d", priorityId));
	}

}
