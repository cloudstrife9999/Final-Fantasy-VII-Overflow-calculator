package com.ff7damage.weapons;

import java.util.List;

import com.ff7damage.Utils;

public class PremiumHeart extends Weapon implements WeaponInterface {

	public PremiumHeart() {
		super(99, 0, false);
	}

	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		int limitLevel = Utils.sanitize(otherParameters[0], 1, 4, 1);
		int limitGauge = Utils.sanitize(otherParameters[1], 0, 255, 0);
		
		return ((techniquePower * limitLevel * (limitGauge >> 4)) >> 4) + 1;
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
		return "Premium Heart";
	}
}