package com.expert.ai.ocrulus.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be found in the request header
 * 
 */

public class JwtTokenMissingException extends AuthenticationException
{
	private static final long serialVersionUID = -2603580576197699080L;

	public JwtTokenMissingException(String msg)
	{
		super(msg);
	}
}