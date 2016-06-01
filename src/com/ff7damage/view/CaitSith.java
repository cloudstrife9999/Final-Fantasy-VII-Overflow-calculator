package com.ff7damage.view;

public class CaitSith extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"HP Shout", "Yellow M-phone", "Green M-phone", "Blue M-phone", "Red M-phone", "Crystal M-phone", "White M-phone",
				"Black M-phone", "Silver M-phone", "Trumpet Shell", "Gold M-phone", "Battle Trumpet", "Starlight Phone"};
	}
}