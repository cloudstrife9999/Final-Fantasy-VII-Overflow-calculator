package com.ff7damage.view;

public class Tifa extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Premium Heart", "Leather Glove", "Metal Knuckle", "Mythril Claw", "Grand Glove", "Tiger Fang", "Diamond Knuckle",
				"Dragon Claw", "Crystal Glove", "Motor Drive", "Platinum Fist", "Kaiser Knuckle", "Work Glove", "Powersoul", "Master Fist",
				"God's Hand"};
	}
}