package com.ff7damage.view;

public class Cid extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Venus Gospel", "Spear", "Slash Lance", "Trident", "Mast Ax", "Partisan", "Viper Halberd", "Javelin",
				"Grow Lance", "Mop", "Dragoon Lance", "Scimitar", "Flayer", "Spirit Lance"};
	}
}