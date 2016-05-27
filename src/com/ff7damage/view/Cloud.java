package com.ff7damage.view;

public class Cloud extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Ultima Weapon"};
	}
}