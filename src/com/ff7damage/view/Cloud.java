package com.ff7damage.view;

public class Cloud extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Ultima Weapon", "Buster Sword", "Mythril Saber", "Hardedge", "Butterfly Edge", "Enhance Sword", "Organics",
				"Crystal Sword", "Force Stealer", "Rune Blade", "Murasame", "Nail Bat", "Yoshiyuki", "Apocalypse", "Heaven's Cloud",
				"Ragnarok"};
	}
}