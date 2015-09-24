package com.returnsoft.callcenter.exception;

import java.io.Serializable;

public class CorbaUtilException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5211669227186039648L;
	
	public CorbaUtilException(String arg0, Throwable arg1){
		super(arg0,arg1);
	}
	
	public CorbaUtilException(String arg0){
		super(arg0);
	}
	
	public CorbaUtilException(Throwable arg0) {
		super(arg0);
	}
	


}
