package com.oserret.ocrulus.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be construct
 * 
 */
public class JwtTokenConstructException extends AuthenticationException
{
	private static final long serialVersionUID = -5596555382824312933L;

	public JwtTokenConstructException(String msg)
	{
		super(msg);
	}

	public JwtTokenConstructException(String msg, Exception e)
	{
		super(msg, e);
	}
}