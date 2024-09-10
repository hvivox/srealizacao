package com.hvivox.srealizacao.exception;

public class LearningNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public LearningNotFoundException(String message) {
		super(message);
	}

	public LearningNotFoundException(Long LeaningId) {
		this(String.format("Não existe um cadastro de aprendizagem com código %d", LeaningId));
	}

}
