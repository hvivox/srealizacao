package com.hvivox.srealizacao.exception;

public class SheetNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public SheetNotFoundException(String message) {
		super(message);
	}

	public SheetNotFoundException(Integer sheetId) {
		this(String.format("Não existe um cadastro de folha com código %d", sheetId));
	}

}
