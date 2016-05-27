package com.ff7damage.view;

public interface VisitorInterface {
	public abstract void visit(Cloud character);
	public abstract void visit(Barret character);
	public abstract void visit(Tifa character);
	public abstract void visit(Aerith character);
	public abstract void visit(RedXIII character);
	public abstract void visit(Yuffie character);
	public abstract void visit(CaitSith character);
	public abstract void visit(Vincent character);
	public abstract void visit(Cid character);
}