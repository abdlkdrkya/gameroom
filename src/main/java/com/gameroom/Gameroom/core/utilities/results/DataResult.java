package com.gameroom.Gameroom.core.utilities.results;

public class DataResult<T> extends Result {

	private T data;
	
	public T getData() {
		return this.data;
	}
	
	public DataResult(boolean success) {
		super(success);
	}
	
	public DataResult(T data, boolean success, String message) {
		super(success,message);
		this.data = data;
	}
	public DataResult(T data, String message) {
		super(true, message);
		this.data = data;
	}
	public DataResult(T data, boolean success) {
		super(success);
		this.data = data;
	}
}
