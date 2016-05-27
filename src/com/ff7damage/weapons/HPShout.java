package com.ff7damage.weapons;

import java.util.List;

import com.ff7damage.Utils;

public class HPShout extends Weapon implements WeaponInterface {

	public HPShout() {
		super(95, 0, false);
	}

	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		int currentHp = Utils.sanitize(otherParameters[0], 1, 9999, 1);
		int maxHp = Utils.sanitize(otherParameters[1], 1, 9999, 9999);
		
		return (int)Math.floor((3 * techniquePower * currentHp) / maxHp) + 1;
	}
	
	@Override
	public int getAtkBonus() {
		return super.getAtkBonus();
	}
	
	@Override
	public int getAp() {
		return Utils.USELESS;
	}
	
	@Override
	public List<Double> getAdditionalMultipliers() {
		return super.getAdditionalMultipliers();
	}
	
	@Override
	public boolean isLongRange() {
		return super.isLongRange();
	}
	
	@Override
	public String toString() {
		return "HP Shout";
	}
}