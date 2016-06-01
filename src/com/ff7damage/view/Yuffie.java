package com.ff7damage.view;

public class Yuffie extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Conformer", "4-point Shuriken", "Boomerang", "Pinweel", "Razor Ring", "Hawkeye", "Crystal Cross", "Wind Slash",
				"Twin Viper", "Spiral Shuriken", "Superball", "Magic Shuriken", "Rising Sun", "Oritsuru"};
	}
}