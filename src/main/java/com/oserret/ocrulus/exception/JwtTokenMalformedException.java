package com.oserret.ocrulus.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be parsed
 * 
 */
public class JwtTokenMalformedException extends AuthenticationException
{
	private static final long serialVersionUID = 3603521120644035312L;

	public JwtTokenMalformedException(String msg)
	{
		super(msg);
	}
}
