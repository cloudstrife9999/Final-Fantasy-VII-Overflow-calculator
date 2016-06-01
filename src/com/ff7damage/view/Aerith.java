package com.ff7damage.view;

public class Aerith extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Princess Guard", "Guard Stick", "Mythril Rod", "Full Metal Staff", "Striking Staff", "Prism Staff", "Aurora Rod",
				"Wizard Staff", "Wizer Staff", "Fairy Tale", "Umbrella"};
	}
}