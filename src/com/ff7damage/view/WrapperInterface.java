package com.ff7damage.view;

public interface WrapperInterface {
	public abstract void accept(VisitorInterface visitor);
	public abstract String[] getWeapons();
}