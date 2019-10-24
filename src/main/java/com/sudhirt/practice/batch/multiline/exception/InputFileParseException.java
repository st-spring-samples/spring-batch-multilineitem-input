package com.sudhirt.practice.batch.multiline.exception;

public class InputFileParseException extends RuntimeException {

	private static final long serialVersionUID = 8653292510366746107L;

	public InputFileParseException() {
		super("Error occurred while parsing input file");
	}

	public InputFileParseException(String message) {
		super(message);
	}

}