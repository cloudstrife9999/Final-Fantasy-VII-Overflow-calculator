package com.ff7damage;

public class Event {
	private Events code;
	private Object[] params;
	
	public Event(Events code, Object... params) {
		this.code = code;
		this.params = params;
	}
	
	public Events getCode() {
		return this.code;
	}
	
	public Object[] getParams() {
		return this.params;
	}
}
