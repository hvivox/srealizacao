package com.hvivox.srealizacao.exception;

public class GratitudeNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public GratitudeNotFoundException(String message) {
		super(message);
	}

	public GratitudeNotFoundException(Long gratitudeId) {
		this(String.format("Não existe um cadastro de gratidão com código %d", gratitudeId));
	}

}
