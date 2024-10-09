package com.gameroom.Gameroom.core.utilities.results;

public class SuccessDataResult<T> extends DataResult<T>{
	public SuccessDataResult(T data) {
		super(data, true);
		// TODO Auto-generated constructor stub
	}
	public SuccessDataResult(T data, boolean success) {
		super(data, true);
		// TODO Auto-generated constructor stub
	}
	public SuccessDataResult(T data, String message) {
		super(data, message);
	}
	
	public SuccessDataResult(T data, boolean success, String message) {
		super(data, true, message);
		// TODO Auto-generated constructor stub
	}
}
