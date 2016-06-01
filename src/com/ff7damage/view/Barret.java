package com.ff7damage.view;

public class Barret extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Missing Score", "Gatling Gun", "Assault Gun", "Cannon Ball", "Atomic Scissors", "Heavy Vulcan", "Chainsaw",
				"Microlaser", "A∙M Cannon", "W Machine Gun", "Drill Arm", "Solid Bazooka", "Rocket Punch", "Enemy Launcher", "Pile Banger",
				"Max Ray"};
	}
}