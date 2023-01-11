package com.oserret.ocrulus.exception;

public class UserMessageException extends Exception
{
	private static final long serialVersionUID = -2497021921779294958L;

	public UserMessageException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

	public UserMessageException(String message)
	{
		super(message);
	}
}
