package com.ff7damage.weapons;

import java.util.List;

import com.ff7damage.Utils;

public class LimitedMoon extends Weapon implements WeaponInterface {

	public LimitedMoon() {
		super(93, 0, false);
	}

	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		int currentMp = Utils.sanitize(otherParameters[0], 0, 9999, 0);
		int maxMp = Utils.sanitize(otherParameters[1], 0, 9999, 999); //yes, it is correct: max 9999, default 999
		
		return (int)Math.floor((3 * techniquePower * currentMp) / maxMp) + 1;
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
		return "Limited Moon";
	}
}