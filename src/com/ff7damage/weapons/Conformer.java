package com.ff7damage.weapons;

import java.util.List;

import com.ff7damage.Utils;

public class Conformer extends Weapon implements WeaponInterface {

	public Conformer() {
		super(96, 0, true);
	}

	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		int avgLevel = Utils.sanitize(otherParameters[0], 1, 99, 1);
		
		return avgLevel;
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
		return "Conformer";
	}
}