package com.ff7damage.weapons;

import java.util.List;

public class MissingScore extends Weapon implements WeaponInterface {
	public MissingScore(int ap) {
		super(98, ap, true);
	}

	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		int ap = otherParameters[0];
		return (techniquePower * ((int)(Math.floor(ap/10000)) >> 4)) + 1;
	}
	
	@Override
	public int getAtkBonus() {
		return super.getAtkBonus();
	}
	
	@Override
	public int getAp() {
		return super.getAp();
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
		return "Missing Score";
	}
}