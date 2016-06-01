package com.ff7damage.view;

public class Vincent extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Death Penalty", "Quicksilver", "Shotgun", "Shortbarrel", "Lariat", "Winchester", "Peacemaker", "Buntline",
				"Long Barrel R", "Silver Rifle", "Sniper CR", "Supershot ST", "Outsider"};
	}
}