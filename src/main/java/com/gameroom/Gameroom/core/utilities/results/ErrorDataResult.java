package com.gameroom.Gameroom.core.utilities.results;

public class ErrorDataResult<T> extends DataResult<T>{
	public ErrorDataResult(T data, boolean success) {
		super(data, false);
		// TODO Auto-generated constructor stub
	}
	
	public ErrorDataResult(T data, boolean success, String message) {
		super(data, false, message);
		// TODO Auto-generated constructor stub
	}
	public ErrorDataResult(boolean success, String message) {
		super(null,false,message);
		// TODO Auto-generated constructor stub
	}
}
